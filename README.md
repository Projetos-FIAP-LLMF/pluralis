# 🌱 Pluralis — Inclusão, Diversidade & ESG com Impacto

**Projeto desenvolvido como parte da disciplina de Análise e Desenvolvimento de Sistemas na FIAP.**

Pluralis é um sistema desenvolvido com alma e propósito: promover ambientes corporativos mais inclusivos, diversos e conscientes. Uma API RESTful pensada para monitorar treinamentos de inclusão, registrar feedbacks anônimos e gerar relatórios ESG que realmente transformam o cenário empresarial.

> A inclusão não é um recurso opcional. É a base do futuro.

---

## 📌 Tema ESG | ESG Topic
**🇧🇷 Inclusão e diversidade corporativa**  
**🇺🇸 Corporate inclusion and diversity**

- Relatórios sobre diversidade e presença feminina
- Controle de treinamentos inclusivos obrigatórios
- Canal de feedback anônimo para ouvir quem importa

---

### 🚀 Endpoints principais | Main Endpoints

#### Autenticação | Authentication
- `POST /auth/register` → Registrar novo usuário | Register new user
- `POST /auth/login` → Autenticar e obter token JWT | Authenticate and receive JWT

#### Colaboradores | Employees
- `GET /employees` → Listar todos os colaboradores
- `POST /employees` → Criar novo colaborador

#### Treinamentos | Trainings
- `GET /trainings` → Listar treinamentos ativos
- `POST /trainings` → Criar novo treinamento

#### Participações | Participation Tracking
- `GET /participations` → Listar todas as participações
- `POST /participations` → Registrar participação

#### Feedback Anônimo | Anonymous Feedback
- `GET /anonymous-feedback` → Ver feedbacks recebidos
- `POST /anonymous-feedback` → Enviar novo feedback

#### Relatório ESG | ESG Report
- `GET /inclusion-report` → Consultar relatórios ESG de inclusão
- `POST /inclusion-report` → Criar novo relatório

---

### 🛡️ Segurança | Security
- JWT Token baseado em login com Spring Security
- Headers com `Authorization: Bearer {token}`
- Endpoints protegidos e seguros

---

## 🐳 Execução com Docker | Running with Docker

```bash
docker-compose up --build
```

- Acesse: `http://localhost:8080`
- Banco de dados Oracle: `localhost:1521`  
  Usuário: `pluralis` | Senha: `oracle`

---

## 🔄 Pipeline CI/CD

### Ferramenta Utilizada

**GitHub Actions** - Plataforma de CI/CD integrada ao GitHub para automação de workflows.

### Etapas do Pipeline

#### 1. **Build e Push (Job: build_and_push)**

- **Checkout do código:** Clona o repositório
- **Setup Java 17:** Configura ambiente Java com Temurin
- **Permissões:** Configura executável do Gradle wrapper
- **Testes:** Executa testes automatizados com `./gradlew clean test`
- **Build:** Gera o arquivo JAR com `./gradlew bootJar -x test`
- **Docker Build:** Constrói a imagem Docker da aplicação
- **Docker Push:** Envia imagem para Docker Hub com tags por ambiente

#### 2. **Deploy Staging (Job: deploy_staging)**

- Executa apenas quando há push na branch `develop`
- Deploy automático no Azure Web App de staging
- Utiliza imagem com tag `staging`

#### 3. **Deploy Produção (Job: deploy_prod)**

- Executa apenas quando há push na branch `master`
- Deploy automático no Azure Web App de produção
- Utiliza imagem com tag `prod`

### Lógica do Pipeline

**Gatilhos:**

- Push nas branches `develop` (staging) ou `master` (produção)

**Estratégia de Tagging:**

- Branch `develop` → tag `staging`
- Branch `master` → tag `prod`
- Todas as builds também recebem tag com SHA do commit (7 caracteres)

---

## 📦 Containerização | Containerization

```bash
# Dockerfile.azure
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
```

### Estratégias Adotadas

#### 1. Imagem Base Otimizada
openjdk:21-jdk-slim: Utilizamos a versão slim do OpenJDK 21, reduzindo significativamente o tamanho da imagem final e eliminando dependências desnecessárias

#### 2. Estrutura de Diretórios
WORKDIR /app: Define um diretório de trabalho organizado para a aplicação, isolando os arquivos do sistema

#### 3. Cópia Eficiente do Artefato
COPY build/libs/*.jar app.jar: Copia o JAR gerado pelo build do Gradle com um nome padronizado, facilitando o comando de execução

#### 4. Exposição de Porta
EXPOSE 8080: Documenta que a aplicação estará ouvindo na porta 8080, padrão do Spring Boot

#### 5. Entrypoint Otimizado
ENTRYPOINT ["java","-jar","app.jar"]: Configura o comando padrão para execução da aplicação, garantindo inicialização consistente

#### 6. Multi-stage para Azure
O arquivo é nomeado como Dockerfile.azure, indicando uma configuração específica para deploy na Azure, permitindo diferentes configurações por ambiente

---

## 🧪 Testes BDD e Validação de APIs

### 📘 Linguagem Gherkin

Foram escritos **cenários de teste BDD** utilizando a linguagem **Gherkin**, garantindo que o comportamento das principais funcionalidades da aplicação seja validado em cenários **positivos** (happy path) e **negativos** (falhas).

Os testes foram implementados com **Cucumber + JUnit**, simulando requisições HTTP reais e validando status code, corpo JSON e contratos via **JSON Schema**.

---

### ✅ **Cenários Implementados**

#### 1️⃣ Autenticação
```gherkin
@happy
Scenario: Registrar novo usuário e autenticar com sucesso
  Given a API base url is "http://localhost:8080"
  When I POST "/auth/register" with json:
    """
    { "username": "user_teste", "password": "123456" }
    """
  Then the response status should be 200
  When I POST "/auth/login" with json:
    """
    { "username": "user_teste", "password": "123456" }
    """
  Then the response status should be 200
  And the response body at "$.token" should contain "."
```

#### 2️⃣ Cadastro de Colaboradores
```gherkin
@happy
Scenario: Cadastrar colaborador com sucesso
  Given a valid JWT token
  When I POST "/employees" with json:
    """
    {
      "name": "Maria",
      "email": "maria@empresa.com",
      "gender": "Feminino",
      "ethnicity": "Parda",
      "neurodivergent": false,
      "lgbtqia": true
    }
    """
  Then the response status should be 200
  And the response json should match schema "schemas/employee-created.json"
```

#### 3️⃣ Feedback Anônimo
```gherkin
@negativo
Scenario: Impedir envio de feedback muito curto
  Given a valid JWT token
  When I POST "/anonymous-feedback" with json:
    """
    { "message": "Oi" }
    """
  Then the response status should be 400
  And the response json should match schema "schemas/error-validation.json"
```

---

### ⚙️ Execução dos Testes

Os testes podem ser executados **localmente ou em pipeline CI/CD**.

#### Localmente:
```bash
# Subir containers
docker-compose up -d

# Executar testes
./gradle -q -Dtest=bdd.RunCucumberIT test
```

#### CI/CD (GitHub Actions)
Durante o pipeline, os testes são executados automaticamente na etapa:
```yaml
- name: Run tests
  run: ./gradlew clean test --no-daemon
```

---

### 🧩 Validações Incluídas

- **Status Code:** 200, 400, 401, 403  
- **Corpo JSON:** validação com JsonPath  
- **Contrato:** validação com JSON Schema  
- **BDD:** escrita natural Gherkin com Cucumber  
- **Cobertura:** `/auth`, `/employees`, `/trainings`, `/feedback`, `/inclusion-report`

---

💬 *Esses testes garantem que as principais rotas da aplicação respondem de forma consistente, segura e validada de ponta a ponta, tanto em execuções locais quanto no pipeline CI/CD.*

---

## 💫 Desenvolvedoras | The Team

✨ **Francine Maciel de Sá**  
GitHub: [@francinemaaciel](https://github.com/francinemaaciel)  

✨ **Myrella Uchoa**  
GitHub: [@immyrella](https://github.com/immyrella)  

✨ **Lauren Vasconcelos**  
GitHub: [@l44ver](https://github.com/l44ver)  

✨ **Luciana Ferdioly**  
GitHub: [@LuFerdioly](https://github.com/LuFerdioly)  

---

> Feito com 💚 por mulheres que acreditam no poder do código e da mudança.
