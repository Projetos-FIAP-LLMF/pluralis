Feature: Employees

  Background:
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
    And remember "$.token" as "jwt"
    And set default header "Authorization" to "Bearer ${jwt}"

  @happy @S
  Scenario: Cadastrar colaborador com sucesso
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
    And the response body at "$.email" should be "maria@empresa.com"

  @negativo @S
  Scenario: Impedir cadastro com campo obrigatório ausente
    When I POST "/employees" with json:
    """
    { "name": "" }
    """
    Then the response status should be 400
    And the response json should match schema "schemas/error-validation.json"

  @happy @S
  Scenario: Listar colaboradores
    When I GET "/employees"
    Then the response status should be 200
    And the response json should match schema "schemas/employees-list.json"
