# [Job Management in Technology] - Project, with Spring boot, from the RocketSeat Java training course

## 📈 Monitoring and alerting tool

I used Prometheus for monitoring this application, because it's simple and very useful.  
You can test Prometheus in this application using the endpoints below:

- [Health](http://3.139.102.156:8080/actuator/health)
- [Metrics](http://3.139.102.156:8080/actuator/metrics)
- [Prometheus](http://3.139.102.156:8080/actuator/prometheus)


## Documentation
To document and test the application, I used Swagger.
You can access the documentation using the url bellow:

- [Swagger](http://3.139.102.156:8080/swagger-ui/index.html#/)

# BACK END: 
  - Java;
  - SpringBoot
### Estutura do projeto BackEnd
```
GESTAO_VAGAS
├── .mvn
├── .vscode
└── src
└── main
└── java
└── br
└── com
└── felipevalboeno
└── gestao_vagas
│ ├── config/
│ │ └── SwaggerConfig.java
│ ├── exceptions/
│ │ ├── CompanyNotFoundException.java
│ │ ├── ErrorMessageDTO.java
│ │ ├── ExceptionHandlerController.java
│ │ └── JobNotFoundException.java
│ │ └── UserFoundException.java
│ │ └── UserNotFoundException.java
│ └── modules/
| ├──  candidate/
| ├──  controllers/
│ │ └── AuthCandidateController.java
│ │ └── CandidateController.java
| ├──  dto/
│ │ └── AuthCandidateRequestDTO.java
│ │ └── AuthCandidateResponseDTO.java
│ │ └── ProfileCandidateResponseDTO.java
| ├──  entity/
│ │ └── ApplyJobEntity.java
│ │ └── CandidateEntity.java
| ├──  repository/
│ │ └── ApplyJobRepository.java
│ │ └── CandidateRepository.java
| ├──  UseCases/
│ │ └── ApplyJobCandidateUseCase.java
│ │ └── AuthCandidateUseCase.java
│ │ └── CreateCandidateUseCase.java
│ │ └── ListAllJobsByFilterUseCase.java
│ │ └── ProfileCandidateUseCase.java
| ├──  company/
| ├──  controllers/
│ │ └── AuthCompanyController.java
│ │ └── CompanyController.java
│ │ └── JobController.java
| ├──  dto/
│ │ └── AuthCompanyDTO.java
│ │ └── AuthCompanyResponseDTO.java
│ │ └── CreateJobDTO.java
| ├──  entity/
│ │ └── CompanyEntity.java
│ │ └── JobEntity.java
| ├──  repository/
│ │ └── CompanyRepository.java
│ │ └── JobRepository.java
| ├──  UseCases/
│ │ └── AuthCompanyUseCase.java
│ │ └── CreateCompanyUseCase.java
│ │ └── CreateJobUseCase.java
│ │ └── DeleteJobUseCase.java
│ │ └── ListAllJobsByCompanyUseCase.java
| ├──  providers/
│ │ └── JWTCandidateProvider.java
│ │ └── JWTProvider.java
| ├──  security/
│ │ └── SecurityCandidateFilter.java
│ │ └── SecurityCompanyFilter.java
│ │ └── SecurityConfig.java
├── GestaoVagasApplication.java
└── application.properties

```

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

### OBS: 
  - I used Putty to configure the Docker on EC2 AWS.
  - I used a Database Client extension to conect a new postgrSQL to EC2 endpoint, and I change the DTABASE_URL on github to this new database that I create.


## How to start the application after all this configurations
  1. Open your Putty app
  2. Connect on the saved session 
  3. execute: cd ~/actions-runner
  4. Check on your github if the runner ar online, if not, execute: ./run.sh
  5. execute: docker start gestao-vagas 
  6. execute: docker ps 
  7. execute: docker logs gestao_vagas , check if the application started as well
  8. Open Swagger: http://<public_ec2_ip>:8080/swagger-ui/index.html an do a test.


# FRONT END: 
  - Tailwind;
  - RestTemplate to connect with http request backend
### Estutura do projeto FrontEnd
```
FRONT_GESTAO_VAGAS
├── .mvn
├── .vscode
└── src
└── main
└── java
└── br
└── com
└── felipevalboeno
└── front_gestao_vagas
└── modules
├── candidate
│ ├── controller/
│ │ └── CandidateController.java
│ ├── dto/
│ │ ├── CreateCandidateDTO.java
│ │ ├── JobDTO.java
│ │ ├── ProfileUserDTO.java
│ │ └── Token.java
│ └── service/
│ ├── ApplyJobService.java
│ ├── CandidateService.java
│ ├── CreateCandidateService.java
│ ├── FindJobsService.java
│ └── ProfileCandidateService.java
└── company
├── controller/
│ └── CompanyController.java
├── dto/
│   ├── CreateCompanyDTO.java
│   └── CreateJobsDTO.java
├── service/
│   ├── CreateCompanyService.java
│   ├── CreateJobService.java
│   ├── ListAllJobsCompanyService.java
│   └── LoginCompanyService.java
├── security/
│   └── SecurityConfig.java
├── utils/
│   └── FrontGestaoVagasApplication.java
├── resources
├── static
├── templates
│   └── candidate
│       └── create.html
│       └── jobs.html
│       └── login.html
│       └── profile.html
│   └── company
│       └── create.html
│       └── jobs.html
│       └── list.html
│       └── login.html
└── application.properties

```
