Feature: Feedback, Trainings & Inclusion Report

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

# -------------------------------------------------------
  @happy @S
  Scenario: Enviar feedback anônimo válido
    When I POST "/anonymous-feedback" with json:
    """
    { "message": "Ambiente saudável e colaborativo, gosto de trabalhar aqui!" }
    """
    Then the response status should be 200
    And the response json should match schema "schemas/feedback-created.json"

# -------------------------------------------------------
  @negativo @S
  Scenario: Impedir feedback com mensagem curta
    When I POST "/anonymous-feedback" with json:
    """
    { "message": "Oi" }
    """
    Then the response status should be 400
    And the response json should match schema "schemas/error-validation.json"

# -------------------------------------------------------
  @happy @S
  Scenario: Criar treinamento e listar
    When I POST "/trainings" with json:
    """
    { "name": "Treinamento de Diversidade e Inclusão", "date": "2025-10-22", "mandatory": true }
    """
    Then the response status should be 200
    And the response json should match schema "schemas/training-created.json"
    When I GET "/trainings"
    Then the response status should be 200
    And the response json should match schema "schemas/trainings-list.json"

# -------------------------------------------------------
  @happy @S
  Scenario: Registrar participação em treinamento
    When I POST "/employees" with json:
    """
    {
      "name": "João",
      "email": "joao@empresa.com",
      "gender": "Masculino",
      "ethnicity": "Pardo",
      "neurodivergent": false,
      "lgbtqia": false
    }
    """
    Then the response status should be 200
    And remember "$.id" as "empId"

    When I POST "/trainings" with json:
    """
    { "name": "Treinamento de Acessibilidade", "date": "2025-10-22", "mandatory": true }
    """
    Then the response status should be 200
    And remember "$.id" as "trId"

    When I POST "/training-participation" with json:
    """
    { "employeeId": "${empId}", "trainingId": "${trId}", "completed": true }
    """
    Then the response status should be 200
    And the response json should match schema "schemas/participation-created.json"

# -------------------------------------------------------
  @happy @G
  Scenario: Gerar Inclusion Report
    When I GET "/inclusion-report"
    Then the response status should be 200
    And the response json should match schema "schemas/inclusion-report.json"
