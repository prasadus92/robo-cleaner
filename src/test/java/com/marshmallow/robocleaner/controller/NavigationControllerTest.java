package com.marshmallow.robocleaner.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith (SpringJUnit4ClassRunner.class)
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NavigationControllerTest {

    @LocalServerPort
    protected int port;

    @Before
    public void beforeTest() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void testInvalidAreaSize() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"areaSize\" : [5],\n" +
                              "  \"startingPosition\" : [1, 2],\n" +
                              "  \"oilPatches\" : [\n" +
                              "    [1, 0],\n" +
                              "    [2, 2],\n" +
                              "    [2, 3]\n" +
                              "  ],\n" +
                              "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", containsString("accepted value for areaSize is a pair of X and Y (X > 0 & Y > 0)"));
        //@formatter:on

        //language=JSON
        instructions = "{\n" +
                       "  \"areaSize\" : [0, 5],\n" +
                       "  \"startingPosition\" : [1, 2],\n" +
                       "  \"oilPatches\" : [\n" +
                       "    [1, 0],\n" +
                       "    [2, 2],\n" +
                       "    [2, 3]\n" +
                       "  ],\n" +
                       "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                       "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", containsString("accepted value for areaSize is a pair of X and Y (X > 0 & Y > 0)"));
        //@formatter:on
    }

    @Test
    public void testInvalidStartingPosition() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"areaSize\" : [5, 5],\n" +
                              "  \"startingPosition\" : [1],\n" +
                              "  \"oilPatches\" : [\n" +
                              "    [1, 0],\n" +
                              "    [2, 2],\n" +
                              "    [2, 3]\n" +
                              "  ],\n" +
                              "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", containsString("accepted value is a pair of X and Y"));
        //@formatter:on
    }

    @Test
    public void testStaringPositionOutOfArea() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"areaSize\" : [5, 5],\n" +
                              "  \"startingPosition\" : [1, 6],\n" +
                              "  \"oilPatches\" : [\n" +
                              "    [1, 0],\n" +
                              "    [2, 2],\n" +
                              "    [2, 3]\n" +
                              "  ],\n" +
                              "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", containsString("Starting position coordinates are outside of the area"));
        //@formatter:on
    }

    @Test
    public void testInvalidOilPatchCoordinates() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"areaSize\" : [5, 5],\n" +
                              "  \"startingPosition\" : [1, 2],\n" +
                              "  \"oilPatches\" : [\n" +
                              "    [1],\n" +
                              "    [2, 2],\n" +
                              "    [2, 3]\n" +
                              "  ],\n" +
                              "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", containsString("oilPatches must be a list of X and Y pairs"));
        //@formatter:on
    }

    @Test
    public void testOilPatchCoordinatesOutOfArea() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"areaSize\" : [5, 5],\n" +
                              "  \"startingPosition\" : [1, 2],\n" +
                              "  \"oilPatches\" : [\n" +
                              "    [1, 6],\n" +
                              "    [2, 2],\n" +
                              "    [2, 3]\n" +
                              "  ],\n" +
                              "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", containsString("Oil patch coordinates are outside of the area"));
        //@formatter:on
    }

    @Test
    public void testInvalidNavigationInstructions() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"areaSize\" : [5, 5],\n" +
                              "  \"startingPosition\" : [1, 2],\n" +
                              "  \"oilPatches\" : [\n" +
                              "    [1, 0],\n" +
                              "    [2, 2],\n" +
                              "    [2, 3]\n" +
                              "  ],\n" +
                              "  \"navigationInstructions\" : \"NNESEESWNWWA\"\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", containsString("the accepted sequence of characters for navigationInstructions are N, S, E and W (indicative of directions)"));
        //@formatter:on
    }

    @Test
    public void testBasicUseCase() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"areaSize\" : [5, 5],\n" +
                              "  \"startingPosition\" : [1, 2],\n" +
                              "  \"oilPatches\" : [\n" +
                              "    [1, 0],\n" +
                              "    [2, 2],\n" +
                              "    [2, 3]\n" +
                              "  ],\n" +
                              "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body("oilPatchesCleaned", equalTo(1))
            .body("finalPosition", equalTo(Arrays.asList(1, 3)));
        //@formatter:on
    }

    @Test
    public void testRectangularArea() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"areaSize\" : [5, 7],\n" +
                              "  \"startingPosition\" : [1, 5],\n" +
                              "  \"oilPatches\" : [\n" +
                              "    [1, 6],\n" +
                              "    [2, 6],\n" +
                              "    [4, 4]\n" +
                              "  ],\n" +
                              "  \"navigationInstructions\" : \"NESEESWNWS\"\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body("oilPatchesCleaned", equalTo(3))
            .body("finalPosition", equalTo(Arrays.asList(2, 4)));
        //@formatter:on
    }

    @Test
    public void testNoAreaSize() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"startingPosition\" : [1, 2],\n" +
                              "  \"oilPatches\" : [\n" +
                              "    [1, 0],\n" +
                              "    [2, 2],\n" +
                              "    [2, 3]\n" +
                              "  ],\n" +
                              "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", containsString("accepted value for areaSize is a pair of X and Y (X > 0 & Y > 0)"));
        //@formatter:on
    }

    @Test
    public void testNoStartingPosition() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"areaSize\" : [5, 5],\n" +
                              "  \"oilPatches\" : [\n" +
                              "    [1, 0],\n" +
                              "    [2, 2],\n" +
                              "    [2, 3]\n" +
                              "  ],\n" +
                              "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", containsString("accepted value is a pair of X and Y"));
        //@formatter:on
    }

    @Test
    public void testNoOilPatch() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"areaSize\" : [5, 5],\n" +
                              "  \"startingPosition\" : [1, 2],\n" +
                              "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", containsString("oilPatches must be a list of X and Y pairs"));
        //@formatter:on
    }

    @Test
    public void testNoNavigationInstructions() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"areaSize\" : [5, 5],\n" +
                              "  \"startingPosition\" : [1, 2],\n" +
                              "  \"oilPatches\" : [\n" +
                              "    [1, 0],\n" +
                              "    [2, 2],\n" +
                              "    [2, 3]\n" +
                              "  ]\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", containsString("the accepted sequence of characters for navigationInstructions are N, S, E and W (indicative of directions)"));
        //@formatter:on
    }

    @Test
    public void testEmptyNavigationInstructions() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"areaSize\" : [5, 5],\n" +
                              "  \"startingPosition\" : [1, 2],\n" +
                              "  \"oilPatches\" : [\n" +
                              "    [1, 0],\n" +
                              "    [2, 2],\n" +
                              "    [2, 3]\n" +
                              "  ],\n" +
                              "  \"navigationInstructions\" : \"\"\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", containsString("the accepted sequence of characters for navigationInstructions are N, S, E and W (indicative of directions)"));
        //@formatter:on
    }

    @Test
    public void testNegativeCoordinatesForOilPatches() {
        //language=JSON
        String instructions = "{\n" +
                              "  \"areaSize\" : [5, 5],\n" +
                              "  \"startingPosition\" : [1, 2],\n" +
                              "  \"oilPatches\" : [\n" +
                              "    [1, 0],\n" +
                              "    [2, -2],\n" +
                              "    [-2, 3]\n" +
                              "  ],\n" +
                              "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                              "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(instructions)
            .when()
            .post("api/v1/robocleaner/navigation")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", containsString("oilPatches must be a list of X and Y pairs (X >= 0 and Y >= 0)"));
        //@formatter:on
    }
}
