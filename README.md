# [Job Management in Technology] - Project, with Spring boot, from the RocketSeat Java training course

## DEPLOY WITH RENDER
- To deply this application, I used The Render platform(cloud hosting). You can use a free version for studies.
- How to do that?
    -  1º Log in to render using your github account.
    -  2º Do the first simple configuration like name, database name...
    -  3º Create a DockerFile on your project with the configuration of "dockerfile config" bellow.
    -  4º Create a new Web server on Render and deply with your last commit on git.

```dockerfile config
# =========================
# Stage 1: Build
# =========================
FROM maven:3.9.3-eclipse-temurin-17 AS build

# Define diretório de trabalho
WORKDIR /app

# Copia apenas o pom.xml para cache do Maven
COPY pom.xml .

# Baixa dependências sem reconstruir código
RUN mvn dependency:go-offline -B

# Copia o código-fonte
COPY src ./src

# Build do projeto, gera o .jar
RUN mvn clean package -DskipTests

# =========================
# Stage 2: Runtime
# =========================
FROM openjdk:17-jdk-slim

# Define diretório de trabalho
WORKDIR /app

# Copia o .jar do stage de build
COPY --from=build /app/target/gestao_vagas-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]


ENTRYPOINT [ "java", "-jar", "app.jar" ]
```
 




