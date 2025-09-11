#FROM ubuntu:latest AS build


#RUN apt-get update
#RUN apt-get install openjdk-17-jdk -y
#COPY . . 

#RUN apt-get install maven -y
#RUN mvn clean install

#FROM openjdk:17-jdk-slim
#EXPOSE 8080

#COPY --from=build /target/gestao_vagas-0.0.1.jar app.jar

#ENTRYPOINT [ "java", "-jar", "app.jar" ]

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


