# Spring Boot RealWorld Conduit API

![Spring Boot RealWorld Conduit API](logo.png)

**Spring Boot RealWorld Conduit API** implements the [API Endpoints](https://realworld-docs.netlify.app/docs/specs/backend-specs/endpoints) of [Conduit](https://github.com/gothinkster/realworld),
which is a Medium.com clone.

[![CI Build](https://github.com/sivaprasadreddy/spring-realworld-conduit-api/actions/workflows/maven.yml/badge.svg)](https://github.com/sivaprasadreddy/spring-realworld-conduit-api/actions/workflows/maven.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sivaprasadreddy_spring-realworld-conduit-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=sivaprasadreddy_spring-realworld-conduit-api)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=sivaprasadreddy_spring-realworld-conduit-api&metric=coverage)](https://sonarcloud.io/summary/new_code?id=sivaprasadreddy_spring-realworld-conduit-api)

## Tech Stack
* [Java 21](https://dev.java/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Security](https://spring.io/projects/spring-security)
* [Spring Modulith](https://spring.io/projects/spring-modulith)
* [jOOQ](https://www.jooq.org/)
* [PostgreSQL](https://www.postgresql.org/)
* [FlywayDB](https://flywaydb.org/)
* [JUnit 5](https://junit.org/junit5/)
* [Testcontainers](https://testcontainers.com/)
* [Docker Compose](https://docs.docker.com/compose/)

## Prerequisites
* JDK 21
* Docker and Docker Compose
* Your favourite IDE (Recommended: [IntelliJ IDEA](https://www.jetbrains.com/idea/))

Install JDK using [SDKMAN](https://sdkman.io/)

```shell
$ curl -s "https://get.sdkman.io" | bash
$ source "$HOME/.sdkman/bin/sdkman-init.sh"
$ sdk install java 21.0.1-tem
$ sdk install maven
```

Verify the prerequisites

```shell
$ java -version
openjdk version "21.0.1" 2023-10-17 LTS
OpenJDK Runtime Environment Temurin-21.0.1+12 (build 21.0.1+12-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.1+12 (build 21.0.1+12-LTS, mixed mode)

$ docker info
Client:
 Version:    27.0.3
 Context:    desktop-linux
 ...
 ...
Server:
 Server Version: 27.0.3
 ...
 ...

$ docker compose version
Docker Compose version v2.28.1-desktop.1
```

## How to?

```shell
# Clone the repository
$ git clone https://github.com/sivaprasadreddy/spring-realworld-conduit-api.git
$ cd spring-realworld-conduit-api

# Run tests
$ ./mvnw test

# Automatically format code using spotless-maven-plugin
$ ./mvnw spotless:apply

# Run/Debug application from IDE
Run `src/main/java/conduit/ConduitApplication.java` from IDE.

# Run application using Maven
./mvnw spring-boot:run
```

The application is configured to use Docker Compose to automatically start the application dependencies
such as PostgreSQL.

* PostgreSQL container connection properties:
  ```shell
  host: localhost
  port: 65432
  username: postgres
  password: postgres
  database: postgres
  ```
* Application run on port http://localhost:8080
* Swagger UI: http://localhost:8080/swagger-ui/index.html

## Using [Taskfile](https://taskfile.dev/) utility
Task is a task runner that we can use to run any arbitrary commands in easier way.

### Installation

```shell
$ brew install go-task
(or)
$ go install github.com/go-task/task/v3/cmd/task@latest

#verify task version
$ task --version
Task version: 3.35.1
```

### Using `task` to perform various tasks:

```shell

# Run tests
$ task test

# Automatically format code using spotless-maven-plugin
$ task format

# Build docker image
$ task build_image

# Run application in docker container
$ task start
$ task stop
$ task restart
```
