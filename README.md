# Interview task (house hosting logic)

## Prerequisites
- Java 17 (installed and activated by `JAVA_HOME` or full path to `java.exe` should be used)
- Maven

## Architecture description
This sample microservice consists of REST controller and service, that makes
analysis on on-memory data (loaded via controller).

## Business rule description (in form of task)
This repository contains implementation for the task, described in the folder "task".

## How to start
### - manually (from terminal)
1 - Build module from the root folder using command:
```shell
$ mvn clean install
```
2 - Start Calculator service using command:
```shell
java -jar target/shost-0.0.1-SNAPSHOT.jar
```

### - (faster alternative!) with Docker (from terminal):

1 - Build the Docker images of all services:

```shell
$ mvn clean package -Pbuild-image
```

## Links
- http://localhost:8080/swagger-ui/index.html - OpenAPI(v3)(Swagger) REST endpoint documentation
- http://localhost:8080/actuator/health -> check status service;