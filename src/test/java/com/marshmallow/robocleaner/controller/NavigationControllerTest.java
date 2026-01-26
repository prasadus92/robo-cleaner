package com.marshmallow.robocleaner.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Navigation Controller Integration Tests")
class NavigationControllerTest {

    private static final String NAVIGATION_ENDPOINT = "/api/v1/robocleaner/navigation";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Basic navigation - should return 200 with correct result")
    void basicNavigation_shouldReturnCorrectResult() throws Exception {
        String request = """
            {
              "areaSize": [5, 5],
              "startingPosition": [1, 2],
              "oilPatches": [[1, 0], [2, 2], [2, 3]],
              "navigationInstructions": "NNESEESWNWW"
            }
            """;

        mockMvc.perform(post(NAVIGATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.oilPatchesCleaned").value(1))
            .andExpect(jsonPath("$.finalPosition[0]").value(1))
            .andExpect(jsonPath("$.finalPosition[1]").value(3));
    }

    @Test
    @DisplayName("Invalid area size - should return 400")
    void invalidAreaSize_shouldReturnBadRequest() throws Exception {
        String request = """
            {
              "areaSize": [5],
              "startingPosition": [1, 2],
              "oilPatches": [[1, 0]],
              "navigationInstructions": "N"
            }
            """;

        mockMvc.perform(post(NAVIGATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Invalid navigation instructions - should return 400")
    void invalidNavigationInstructions_shouldReturnBadRequest() throws Exception {
        String request = """
            {
              "areaSize": [5, 5],
              "startingPosition": [1, 2],
              "oilPatches": [[1, 0]],
              "navigationInstructions": "NNESXA"
            }
            """;

        mockMvc.perform(post(NAVIGATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Starting position out of bounds - should return 400")
    void startingPositionOutOfBounds_shouldReturnBadRequest() throws Exception {
        String request = """
            {
              "areaSize": [5, 5],
              "startingPosition": [1, 6],
              "oilPatches": [[1, 0]],
              "navigationInstructions": "N"
            }
            """;

        mockMvc.perform(post(NAVIGATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Missing required field - should return 400")
    void missingOilPatches_shouldReturnBadRequest() throws Exception {
        String request = """
            {
              "areaSize": [5, 5],
              "startingPosition": [1, 2],
              "navigationInstructions": "N"
            }
            """;

        mockMvc.perform(post(NAVIGATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isBadRequest());
    }
}
