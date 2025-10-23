# -----------------------------
# Etapa 1: Build da aplicação
# -----------------------------
FROM gradle:8.10.2-jdk21-alpine AS build
WORKDIR /workspace
COPY . .
RUN gradle clean bootJar -x test

# -----------------------------
# Etapa 2: Runtime
# -----------------------------
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Instala netcat (necessário pro wait-for-it.sh)
RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*

# Copia o JAR gerado da etapa de build
COPY --from=build /workspace/build/libs/*.jar app.jar

# Copia o script wait-for-it
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8080

# Usa o script pra aguardar o Oracle estar pronto
ENTRYPOINT ["/wait-for-it.sh", "oracle-db", "1521", "--", "java", "-jar", "app.jar"]
