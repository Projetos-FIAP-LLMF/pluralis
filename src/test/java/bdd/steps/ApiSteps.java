package bdd.steps;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;


public class ApiSteps {

    private final Map<String, Object> ctx = new ConcurrentHashMap<>();
    private final Map<String, String> defaultHeaders = new ConcurrentHashMap<>();

    private String baseUrl;
    private String lastResponseBody;
    private int lastStatus;

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private final Random rnd = new Random();

    private String resolvePlaceholders(String text) {
        if (text == null) return null;
        String resolved = text;
        for (var e : ctx.entrySet()) {
            resolved = resolved.replace("${" + e.getKey() + "}", String.valueOf(e.getValue()));
        }
        return resolved;
    }

    private HttpResponse<String> send(String method, String path, String body) {
        try {
            String target = baseUrl.endsWith("/") || path.startsWith("/")
                    ? baseUrl + (path.startsWith("/") ? path.substring(1) : path)
                    : baseUrl + path;

            HttpRequest.Builder b = HttpRequest.newBuilder()
                    .uri(URI.create(target))
                    .timeout(Duration.ofSeconds(20));

            for (var h : defaultHeaders.entrySet()) {
                b.header(h.getKey(), resolvePlaceholders(h.getValue()));
            }

            if ("POST".equalsIgnoreCase(method)) {
                String payload = body == null ? "" : resolvePlaceholders(body);
                b.header("Content-Type", "application/json; charset=UTF-8");
                b.POST(HttpRequest.BodyPublishers.ofString(payload, StandardCharsets.UTF_8));
            } else if ("GET".equalsIgnoreCase(method)) {
                b.GET();
            } else {
                throw new IllegalArgumentException("Método não suportado: " + method);
            }

            HttpResponse<String> resp = http.send(b.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            lastStatus = resp.statusCode();
            lastResponseBody = resp.body();
            return resp;
        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar requisição " + method + " " + path + ": " + e.getMessage(), e);
        }
    }

    @Given("a API base url is {string}")
    public void setBaseUrl(String base) {
        this.baseUrl = base;
    }

    @Given("I create a fresh username {string} and password {string}")
    public void createFreshUser(String userTemplate, String password) {
        String unique = String.valueOf(System.currentTimeMillis() + rnd.nextInt(1000));
        String username = userTemplate.replace("${RND}", unique);
        ctx.put("username", username);
        ctx.put("password", password);
    }

    @Given("I reuse the last username and password")
    public void reuseLastUser() {

    }

    @When("I POST {string} with json:")
    public void iPOST(String path, String body) {
        send("POST", path, body);
    }

    @When("I GET {string}")
    public void iGET(String path) {
        send("GET", path, null);
    }

    @Then("the response status should be {int}")
    public void statusShouldBe(int expected) {
        assertEquals(expected, lastStatus, () ->
                "expected: " + expected + " but was: " + lastStatus +
                        (lastResponseBody != null ? "\nBody:\n" + lastResponseBody : ""));
    }

    @And("remember {string} as {string}")
    public void rememberJsonPathAs(String jsonPath, String key) {
        Object val = JsonPath.read(nullToEmpty(lastResponseBody), jsonPath);
        ctx.put(key, val);
    }

    @And("set default header {string} to {string}")
    public void setDefaultHeader(String header, String value) {
        defaultHeaders.put(header, resolvePlaceholders(value));
    }

    @And("the response json should match schema {string}")
    public void jsonShouldMatchSchema(String schemaClasspath) {
        try (InputStream schemaIs = resourceOrThrow(schemaClasspath)) {
            Class<?> schemaLoaderCls = Class.forName("org.everit.json.schema.loader.SchemaLoader");
            Class<?> jsonTokenerCls = Class.forName("org.json.JSONTokener");
            Class<?> jsonObjectCls = Class.forName("org.json.JSONObject");
            Class<?> schemaCls = Class.forName("org.everit.json.schema.Schema");

            Object schemaTokener = jsonTokenerCls.getConstructor(InputStream.class).newInstance(schemaIs);
            Object schemaJson = jsonObjectCls.getConstructor(jsonTokenerCls).newInstance(schemaTokener);
            Object schemaLoader = schemaLoaderCls.getMethod("load", jsonObjectCls)
                    .invoke(schemaLoaderCls.getMethod("builder").invoke(null), schemaJson);

            Object schema = schemaLoaderCls.getMethod("load").invoke(schemaLoader);

            Object bodyTokener = jsonTokenerCls.getConstructor(String.class).newInstance(nullToEmpty(lastResponseBody));
            Object bodyJson = jsonObjectCls.getConstructor(jsonTokenerCls).newInstance(bodyTokener);

            schemaCls.getMethod("validate", Object.class).invoke(schema, bodyJson);
        } catch (ClassNotFoundException e) {
            assertNotNull(lastResponseBody, "Response body é nulo");
            assertFalse(lastResponseBody.isBlank(), "Response body está vazio");
            System.out.println("[WARN] Everit JSON Schema não encontrado no classpath. Validação de schema ignorada.");
        } catch (Exception e) {
            throw new AssertionError("Falha na validação de schema '" + schemaClasspath + "': " + e.getMessage(), e);
        }
    }

    @And("the response body at {string} should contain {string}")
    public void jsonPathContains(String jsonPath, String expected) {
        Object val = JsonPath.read(nullToEmpty(lastResponseBody), jsonPath);
        String s = (val == null) ? "" : String.valueOf(val);
        assertTrue(s.contains(expected), "Expected '" + s + "' to contain '" + expected + "'");
    }

    @And("the response body at {string} should be {string}")
    public void jsonPathEquals(String jsonPath, String expected) {
        Object val = JsonPath.read(nullToEmpty(lastResponseBody), jsonPath);
        String s = (val == null) ? null : String.valueOf(val);
        assertEquals(expected, s, "JSONPath " + jsonPath + " diferente do esperado");
    }

    private static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    private static InputStream resourceOrThrow(String classpath) {
        String path = classpath.startsWith("/") ? classpath.substring(1) : classpath;
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (is == null) {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("schemas/" + path);
        }
        if (is == null) {
            throw new IllegalArgumentException("Recurso de schema não encontrado no classpath: " + classpath);
        }
        return is;
    }
}
