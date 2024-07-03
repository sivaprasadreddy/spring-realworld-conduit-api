# Spring Boot RealWorld Conduit API

![Spring Boot RealWorld Conduit API](logo.png)

**Spring Boot RealWorld Conduit API** implements the [RealWorld API Endpoints](https://realworld-docs.netlify.app/docs/specs/backend-specs/endpoints).

## Prerequisites
* JDK 21
* Docker and Docker Compose

Install JDK using [SDKMAN](https://sdkman.io/)

```shell
$ curl -s "https://get.sdkman.io" | bash
$ source "$HOME/.sdkman/bin/sdkman-init.sh"
$ sdk install java 21.0.1-tem
$ sdk install maven
```

## How to?

```shell
# Run tests
./mvnw test

# Format code
./mvnw spotless:apply

# Run application using Docker Compose
./mvnw spring-boot:run

# Run application using Testcontainers
./mvnw spring-boot:test-run
```
