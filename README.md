# [Job Management in Technology] - Project, with Spring boot, from the RocketSeat Java training course

## DEPLOY WITH RENDER
To deploy this application, I used the Render platform (cloud hosting). You can use a free version for studies. 

How to do that?
  1. Log in to Render using your GitHub account.
  2. Do the first simple configuration like name, database name...
  3. Create a Dockerfile on your project with the configuration of "dockerfile config" below.
  4. Create a new Web Service on Render and deploy with your last commit on Git.



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
 
## Creating a database and webserver on AWS
  1. Create an account on Amazon AWS  
  2. Search for "RDS" and go to Create a database  
  3. Choose standard creation > PostgreSQL > Models: Free tier > Create database  
  4. Search for EC2  
  5. AMI - Kernel6.1 - free > Instance: t3.MICRO - free  
  6. Create pair of keys: RSA > .pem  
  7. Execute instance and connect

## Creating a DockerHub

With Docker Hub, you can save a ready-to-use image of your application, just like you can save your code on GitHub.

You need a .yml file in the root of your project for this, mine looked like this

```dockerfile config
name: Gestão de Vagas Application

on: 
    push:
        branches: [main]

jobs:
    build:
        runs-on: ubuntu-latest
        steps:
            - name: Checkout code
              uses: actions/checkout@v3
            - name: Set up Java
              uses: actions/setup-java@v3
              with:
                distribution: temurin
                java-version: 17
            - name: Build project
              run: mvn clean install
            - name: login docker
              run: docker login -u ${{secrets.DOCKER_USERNAME}} -p ${{secrets.DOCKER_PASSWORD}}
            - name: Build docker image
              run: docker build -t felipevalboenodocker/gestao_vagas .
            - name: Publish image
              run: docker push felipevalboenodocker/gestao_vagas
                
            
```


