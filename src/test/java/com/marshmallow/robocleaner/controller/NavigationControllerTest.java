package com.marshmallow.robocleaner.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Navigation Controller Integration Tests")
class NavigationControllerTest {

    private static final String NAVIGATION_ENDPOINT = "/api/v1/robocleaner/navigation";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Health check - actuator should respond")
    void healthCheck_shouldRespond() {
        given()
        .when()
            .get("/actuator/health")
        .then()
            .statusCode(200);
    }

    @Test
    @DisplayName("Basic navigation - should return 200 with valid response")
    void basicNavigation_shouldReturnCorrectResult() {
        String request = """
            {
              "areaSize": [5, 5],
              "startingPosition": [1, 2],
              "oilPatches": [[1, 0], [2, 2], [2, 3]],
              "navigationInstructions": "NNESEESWNWW"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post(NAVIGATION_ENDPOINT)
        .then()
            .statusCode(200)
            .body("finalPosition", notNullValue())
            .body("oilPatchesCleaned", equalTo(1))
            .body("finalPosition", equalTo(Arrays.asList(1, 3)));
    }

    @Test
    @DisplayName("Invalid request - should return 400")
    void invalidRequest_shouldReturnBadRequest() {
        String request = """
            {
              "areaSize": [5],
              "startingPosition": [1, 2],
              "oilPatches": [[1, 0]],
              "navigationInstructions": "N"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post(NAVIGATION_ENDPOINT)
        .then()
            .statusCode(400);
    }
}
