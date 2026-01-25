package com.marshmallow.robocleaner.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
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

    @Nested
    @DisplayName("Successful Navigation Tests")
    class SuccessfulNavigationTests {

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
        @DisplayName("Rectangular area - should navigate correctly and clean all patches")
        void rectangularArea_shouldNavigateCorrectly() {
            String request = """
                {
                  "areaSize": [5, 7],
                  "startingPosition": [1, 5],
                  "oilPatches": [[1, 6], [2, 6], [4, 4]],
                  "navigationInstructions": "NESEESWNWS"
                }
                """;

            given()
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post(NAVIGATION_ENDPOINT)
            .then()
                .statusCode(200)
                .body("oilPatchesCleaned", equalTo(3))
                .body("finalPosition", equalTo(Arrays.asList(2, 4)));
        }

        @Test
        @DisplayName("Clean patch at starting position - should count initial position cleaning")
        void oilPatchAtStartingPosition_shouldBeCleanedImmediately() {
            String request = """
                {
                  "areaSize": [3, 3],
                  "startingPosition": [1, 1],
                  "oilPatches": [[1, 1]],
                  "navigationInstructions": "N"
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
                .body("finalPosition", equalTo(Arrays.asList(1, 2)));
        }

        @Test
        @DisplayName("Navigation at boundary - should stay within bounds")
        void navigationAtBoundary_shouldStayWithinBounds() {
            String request = """
                {
                  "areaSize": [3, 3],
                  "startingPosition": [0, 0],
                  "oilPatches": [[1, 1]],
                  "navigationInstructions": "SSSWWWNNNEEEE"
                }
                """;

            given()
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post(NAVIGATION_ENDPOINT)
            .then()
                .statusCode(200)
                .body("finalPosition", equalTo(Arrays.asList(2, 2)));
        }
    }

    @Nested
    @DisplayName("Validation Error Tests")
    class ValidationErrorTests {

        @Test
        @DisplayName("Invalid area size - single value should fail")
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
                .statusCode(400)
                .body("detail", containsString("areaSize"));
        }

        @Test
        @DisplayName("Invalid area size - zero value should fail")
        void invalidAreaSize_zeroValue_shouldReturnBadRequest() {
            String request = """
                {
                  "areaSize": [0, 5],
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
                .statusCode(400)
                .body("detail", containsString("areaSize"));
        }

        @Test
        @DisplayName("Invalid starting position - single value should fail")
        void invalidStartingPosition_singleValue_shouldReturnBadRequest() {
            String request = """
                {
                  "areaSize": [5, 5],
                  "startingPosition": [1],
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
                .statusCode(400)
                .body("detail", containsString("startingPosition"));
        }

        @Test
        @DisplayName("Starting position out of bounds - should fail")
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
                .statusCode(400)
                .body("detail", containsString("Starting position"));
        }

        @Test
        @DisplayName("Invalid oil patch coordinates - single value should fail")
        void invalidOilPatchCoordinates_singleValue_shouldReturnBadRequest() {
            String request = """
                {
                  "areaSize": [5, 5],
                  "startingPosition": [1, 2],
                  "oilPatches": [[1]],
                  "navigationInstructions": "N"
                }
                """;

            given()
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post(NAVIGATION_ENDPOINT)
            .then()
                .statusCode(400)
                .body("detail", containsString("oilPatches"));
        }

        @Test
        @DisplayName("Oil patch out of bounds - should fail")
        void oilPatchOutOfBounds_shouldReturnBadRequest() {
            String request = """
                {
                  "areaSize": [5, 5],
                  "startingPosition": [1, 2],
                  "oilPatches": [[1, 6]],
                  "navigationInstructions": "N"
                }
                """;

            given()
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post(NAVIGATION_ENDPOINT)
            .then()
                .statusCode(400)
                .body("detail", containsString("Oil patch"));
        }

        @Test
        @DisplayName("Negative oil patch coordinates - should fail")
        void negativeOilPatchCoordinates_shouldReturnBadRequest() {
            String request = """
                {
                  "areaSize": [5, 5],
                  "startingPosition": [1, 2],
                  "oilPatches": [[1, 0], [-2, 3]],
                  "navigationInstructions": "N"
                }
                """;

            given()
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post(NAVIGATION_ENDPOINT)
            .then()
                .statusCode(400)
                .body("detail", containsString("oilPatches"));
        }

        @Test
        @DisplayName("Invalid navigation instructions - should fail")
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
                .statusCode(400)
                .body("detail", containsString("navigationInstructions"));
        }

        @Test
        @DisplayName("Empty navigation instructions - should fail")
        void emptyNavigationInstructions_shouldReturnBadRequest() {
            String request = """
                {
                  "areaSize": [5, 5],
                  "startingPosition": [1, 2],
                  "oilPatches": [[1, 0]],
                  "navigationInstructions": ""
                }
                """;

            given()
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post(NAVIGATION_ENDPOINT)
            .then()
                .statusCode(400)
                .body("detail", containsString("navigationInstructions"));
        }

        @Test
        @DisplayName("Missing area size - should fail")
        void missingAreaSize_shouldReturnBadRequest() {
            String request = """
                {
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
        @DisplayName("Missing starting position - should fail")
        void missingStartingPosition_shouldReturnBadRequest() {
            String request = """
                {
                  "areaSize": [5, 5],
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
        @DisplayName("Missing oil patches - should fail")
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

        @Test
        @DisplayName("Missing navigation instructions - should fail")
        void missingNavigationInstructions_shouldReturnBadRequest() {
            String request = """
                {
                  "areaSize": [5, 5],
                  "startingPosition": [1, 2],
                  "oilPatches": [[1, 0]]
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
}
