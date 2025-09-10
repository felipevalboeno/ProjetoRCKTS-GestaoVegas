# [Job Management in Technology] - Project, with Spring boot, from the RocketSeat Java training course

## DEPLOY
- To deply this application, I used The Render platform(cloud hosting). You can use a free version for studies.
- How to do that?
    -  1º Log in to render using your github account.
    -  2º Do the first simple configuration like name, database name...
    -  3º Create a DockerFile on your project with the configuration bellow:

```dockerfile
FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . . 

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:17-jdk-slim
EXPOSE 8080

COPY --from=build /target/gestao_vagas-0.0.1.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
```



