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

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

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
            .body("message", containsString("accepted value is a pair of X and Y (X > 0 & Y > 0)"));
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
            .body("message", containsString("accepted value is a pair of X and Y (X > 0 & Y > 0)"));
        //@formatter:on
    }

    @Test
    public void testInvalidStartingPosition() {

    }

    @Test
    public void testInvalidOilPatchCoordinates() {

    }

    @Test
    public void testOutOfAreaOilPatchCoordinates() {

    }

    @Test
    public void testInvalidNavigationInstructions() {

    }

    @Test
    public void testBasicUseCase() {

    }

    @Test
    public void testRectangularArea() {

    }

    @Test
    public void testNoOilPatch() {

    }
}
