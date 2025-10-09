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

---

## 📱 Prints do Funcionamento | Operation Prints

---

## 🧠 Tecnologias | Tech Stack
- 💛 Java 17 + Spring Boot 3
- 🐬 Oracle Database
- 🐳 Docker + Docker Compose
- 💾 Flyway para migrações de banco
- 🔐 Spring Security + JWT

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
