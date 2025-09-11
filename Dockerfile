# Fase de build
FROM maven:3.9.0-openjdk-17-slim AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Fase de runtime
FROM openjdk:17-jdk-slim

WORKDIR /app
EXPOSE 8080

COPY --from=build /app/target/gestao_vagas-0.0.1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

