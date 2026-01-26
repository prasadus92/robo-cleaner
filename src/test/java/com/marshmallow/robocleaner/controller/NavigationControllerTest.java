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
    @DisplayName("Basic navigation - should clean one patch and return correct position")
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
            .body("oilPatchesCleaned", equalTo(1))
            .body("finalPosition", equalTo(Arrays.asList(1, 3)));
    }

    @Test
    @DisplayName("Invalid area size - single value should fail with 400")
    void invalidAreaSize_singleValue_shouldReturnBadRequest() {
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

    @Test
    @DisplayName("Invalid navigation instructions - should fail with 400")
    void invalidNavigationInstructions_shouldReturnBadRequest() {
        String request = """
            {
              "areaSize": [5, 5],
              "startingPosition": [1, 2],
              "oilPatches": [[1, 0]],
              "navigationInstructions": "NNESXA"
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

    @Test
    @DisplayName("Starting position out of bounds - should fail with 400")
    void startingPositionOutOfBounds_shouldReturnBadRequest() {
        String request = """
            {
              "areaSize": [5, 5],
              "startingPosition": [1, 6],
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

    @Test
    @DisplayName("Missing oil patches - should fail with 400")
    void missingOilPatches_shouldReturnBadRequest() {
        String request = """
            {
              "areaSize": [5, 5],
              "startingPosition": [1, 2],
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
