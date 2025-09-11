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


FROM maven:3.9.0-eclipse-temurin-17 AS build
#WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
#WORKDIR /app
COPY --from=build /target/gestao_vagas-0.0.1.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]


