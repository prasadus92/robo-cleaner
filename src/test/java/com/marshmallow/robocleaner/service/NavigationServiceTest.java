package com.marshmallow.robocleaner.service;

import com.marshmallow.robocleaner.api.NavigationRequest;
import com.marshmallow.robocleaner.api.NavigationResponse;
import com.marshmallow.robocleaner.exception.OilPatchOutOfBoundsException;
import com.marshmallow.robocleaner.exception.StartingPositionOutOfBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Navigation Service Unit Tests")
class NavigationServiceTest {

    private NavigationService navigationService;

    @BeforeEach
    void setUp() {
        navigationService = new NavigationService();
    }

    @Test
    @DisplayName("Basic use case - should return correct result")
    void basicUseCase_shouldReturnCorrectResult() {
        NavigationRequest request = new NavigationRequest(
                Arrays.asList(5, 5),
                Arrays.asList(1, 2),
                List.of(Arrays.asList(1, 0), Arrays.asList(2, 2), Arrays.asList(2, 3)),
                "NNESEESWNWW"
        );

        NavigationResponse response = navigationService.navigate(request);

        assertNotNull(response);
        assertEquals(1, response.oilPatchesCleaned());
        assertEquals(Arrays.asList(1, 3), response.finalPosition());
    }

    @Test
    @DisplayName("Oil patch outside area - should throw exception")
    void oilPatchOutsideArea_shouldThrowException() {
        NavigationRequest request = new NavigationRequest(
                Arrays.asList(5, 5),
                Arrays.asList(1, 2),
                List.of(Arrays.asList(1, 5)),
                "NNESEESWNWW"
        );

        assertThrows(OilPatchOutOfBoundsException.class, () ->
            navigationService.navigate(request));
    }

    @Test
    @DisplayName("Starting position outside area - should throw exception")
    void startingPositionOutsideArea_shouldThrowException() {
        NavigationRequest request = new NavigationRequest(
                Arrays.asList(5, 5),
                Arrays.asList(5, 5),
                List.of(Arrays.asList(1, 0)),
                "N"
        );

        assertThrows(StartingPositionOutOfBoundsException.class, () ->
            navigationService.navigate(request));
    }
}
