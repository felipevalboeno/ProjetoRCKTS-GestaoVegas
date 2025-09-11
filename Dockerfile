#FROM ubuntu:latest AS build

#RUN apt-get update
##RUN apt-get install openjdk-17-jdk -y
#COPY . . 

#RUN apt-get install maven -y
#RUN mvn clean install

#FROM openjdk:17-jdk-slim
#EXPOSE 8080

#COPY --from=build /target/gestao_vagas-0.0.1.jar app.jar

#ENTRYPOINT [ "java", "-jar", "app.jar" ]

# Fase de build
FROM ubuntu:latest AS build

# Instala dependências
RUN apt-get update && apt-get install -y openjdk-17-jdk maven git

# Define diretório de trabalho
WORKDIR /app

# Copia arquivos do projeto
COPY . .

# Executa build do Maven (com Lombok processando)
RUN mvn clean install -DskipTests

# Fase de runtime
FROM openjdk:17-jdk-slim

WORKDIR /app
EXPOSE 8080

# Copia o jar gerado (ajuste o caminho se necessário)
COPY --from=build /app/target/gestao_vagas-0.0.1.jar app.jar

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
