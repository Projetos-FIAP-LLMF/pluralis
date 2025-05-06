# 🌱 Pluralis

**Pluralis** é uma aplicação web desenvolvida com foco em soluções ESG (Environmental, Social and Governance), promovendo responsabilidade social, diversidade e bem-estar no ambiente corporativo.

---

## 🧭 Visão Geral

A plataforma permite que empresas acompanhem e gerenciem indicadores importantes ligados à inclusão, sustentabilidade e governança de forma clara e intuitiva.

---

## 🧠 Arquitetura

Este projeto utiliza o padrão **MVC (Model-View-Controller)** com as seguintes camadas principais:

- **Controller:** Responsável por expor os endpoints REST e orquestrar as chamadas de serviço.
- **DTO (Data Transfer Object):** Camada de transporte de dados entre controller e model, garantindo segurança e clareza.
- **Model (Entity):** Representa as entidades persistidas no banco de dados.
- **Repository:** Interface com o banco de dados via Spring Data JPA.
- **Service:** Camada de negócio e regras de validação.

---

## 🛠️ Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Lombok
- Flyway (Migrações)
- Swagger (Documentação da API)
- JUnit / Mockito
- Docker

---

## 🚀 Como Rodar Localmente

```bash
# 1. Clone o repositório
git clone https://github.com/ORGANIZACAO/pluralis.git
cd pluralis

# 2. Suba os serviços com Docker
docker-compose up --build
```

> A aplicação estará disponível em: http://localhost:8080

---

## 🔎 Documentação da API

O Swagger está disponível em:

```
http://localhost:8080/swagger-ui.html
```

---

## ✅ Testes

Para rodar os testes automatizados:

```bash
./mvnw test
# ou
./gradlew test
```

---
## 💡 Feito com propósito

Construído para ajudar empresas a florescerem através da diversidade, ética e impacto social.

---
