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

### Etapas do Pipeline

### 1. **Build e Push (Job: build_and_push)**

- **Checkout do código:** Clona o repositório
- **Setup Java 17:** Configura ambiente Java com Temurin
- **Permissões:** Configura executável do Gradle wrapper
- **Testes:** Executa testes automatizados com `./gradlew clean test`
- **Build:** Gera o arquivo JAR com `./gradlew bootJar -x test`
- **Docker Build:** Constrói a imagem Docker da aplicação
- **Docker Push:** Envia imagem para Docker Hub com tags por ambiente

### 2. **Deploy Staging (Job: deploy_staging)**

- Executa apenas quando há push na branch `develop`
- Deploy automático no Azure Web App de staging
- Utiliza imagem com tag `staging`

### 3. **Deploy Produção (Job: deploy_prod)**

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

## 📱 Prints do Funcionamento | Operation Prints

### Build e Testes
![image](https://github.com/user-attachments/assets/e6c2f708-3a12-463d-8781-a00deb5d094e)
![BB2CCAC3-FF37-4FA7-BC14-9E555C2F47CD](https://github.com/user-attachments/assets/c66f90b0-0832-4a4c-a337-b3cc641631ab)

### Docker Build & Push
![image (1)](https://github.com/user-attachments/assets/df599107-888c-4ac7-880f-92c6ad1b330e)
![9E75848B-432D-4165-90FD-27CCC760A9FE](https://github.com/user-attachments/assets/6ced6116-b6a7-42be-9da0-cbec16b107d8)

### Deploy nos Ambientes
![image (2)](https://github.com/user-attachments/assets/8b6a08a0-b2a9-435d-b886-0ace24a422f4)

## Ambientes
### Ambiente de Staging (Develop)

Inserir print do app rodando em staging

### Ambiente de Produção (Master)
![image (3)](https://github.com/user-attachments/assets/edc474b3-62fe-40dd-8529-04326ec16186)
![2BD5BC3B-85D4-4E43-B1DE-C4B65D580A5A](https://github.com/user-attachments/assets/5885dff4-fd1e-4d81-83a4-fe4ab01d4154)

---

## 🧠 Tecnologias | Tech Stack
- Java 17 + Spring Boot 3
- Oracle Database
- Docker + Docker Compose
- Flyway para migrações de banco
- Spring Security + JWT

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
