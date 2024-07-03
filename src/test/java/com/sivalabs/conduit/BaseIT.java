package com.sivalabs.conduit;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import(ContainersConfig.class)
@ActiveProfiles("test")
public abstract class BaseIT {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUpBase() {
        RestAssured.port = port;
    }
}
