Feature: Autenticação

  @happy @G
  Scenario: Registrar usuário novo
    Given a API base url is "http://localhost:8080/"
    And I create a fresh username "user_${RND}" and password "123456"
    When I POST "/auth/register" with json:
    """
    { "username": "${username}", "password": "${password}" }
    """
    Then the response status should be 200

  @negativo @G
  Scenario: Impedir registrar usuário duplicado
    Given a API base url is "http://localhost:8080/"
    And I create a fresh username "user_${RND}" and password "123456"
    When I POST "/auth/register" with json:
    """
    { "username": "${username}", "password": "${password}" }
    """
    Then the response status should be 200
    And I reuse the last username and password
    When I POST "/auth/register" with json:
    """
    { "username": "${username}", "password": "${password}" }
    """
    Then the response status should be 400

  @negativo @G
  Scenario: Impedir login com senha incorreta
    Given a API base url is "http://localhost:8080/"
    And I create a fresh username "user_${RND}" and password "123456"
    When I POST "/auth/register" with json:
    """
    { "username": "${username}", "password": "${password}" }
    """
    Then the response status should be 200
    When I POST "/auth/login" with json:
    """
    { "username": "${username}", "password": "errada" }
    """
    Then the response status should be 403

  @happy @G
  Scenario: Login com sucesso retorna token
    Given a API base url is "http://localhost:8080/"
    And I create a fresh username "user_${RND}" and password "123456"
    When I POST "/auth/register" with json:
    """
    { "username": "${username}", "password": "${password}" }
    """
    Then the response status should be 200
    When I POST "/auth/login" with json:
    """
    { "username": "${username}", "password": "${password}" }
    """
    Then the response status should be 200
    And the response json should match schema "schemas/auth-response.json"
    And remember "$.token" as "jwt"
    And set default header "Authorization" to "Bearer ${jwt}"
    And the response body at "$.token" should contain "."
