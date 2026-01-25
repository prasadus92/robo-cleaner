package com.marshmallow.robocleaner.domain;

import com.marshmallow.robocleaner.model.Coordinate;
import com.marshmallow.robocleaner.model.Dimensions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Robotic Cleaner Unit Tests")
class RoboticCleanerTest {

    @Nested
    @DisplayName("Navigation Tests")
    class NavigationTests {

        @Test
        @DisplayName("Basic navigation - should clean patch and return correct position")
        void basicNavigation_shouldReturnCorrectResult() {
            // Given
            CleaningArea area = new CleaningArea(new Dimensions(5, 5));
            area.applyOilPatch(new Coordinate(1, 0), 0);
            area.applyOilPatch(new Coordinate(2, 2), 1);
            area.applyOilPatch(new Coordinate(2, 3), 2);

            RoboticCleaner cleaner = new RoboticCleaner(area, new Coordinate(1, 2));

            // When
            CleaningResult result = cleaner.executeNavigation("NNESEESWNWW");

            // Then
            assertNotNull(result);
            assertEquals(1, result.oilPatchesCleaned());
            assertEquals(new Coordinate(1, 3), result.finalPosition());
        }

        @Test
        @DisplayName("Oil patch at initial position - should be cleaned immediately")
        void oilPatchAtInitialPosition_shouldBeCleanedImmediately() {
            // Given
            CleaningArea area = new CleaningArea(new Dimensions(7, 7));
            area.applyOilPatch(new Coordinate(0, 5), 0);
            area.applyOilPatch(new Coordinate(0, 1), 1);
            area.applyOilPatch(new Coordinate(2, 2), 2);
            area.applyOilPatch(new Coordinate(2, 3), 3);
            area.applyOilPatch(new Coordinate(3, 3), 4);
            area.applyOilPatch(new Coordinate(4, 6), 5);
            area.applyOilPatch(new Coordinate(5, 4), 6);
            area.applyOilPatch(new Coordinate(6, 1), 7);
            area.applyOilPatch(new Coordinate(1, 5), 8); // Initial position

            RoboticCleaner cleaner = new RoboticCleaner(area, new Coordinate(1, 5));

            // When
            CleaningResult result = cleaner.executeNavigation("WSWSSSEENENEENEESSS");

            // Then
            assertNotNull(result);
            assertEquals(8, result.oilPatchesCleaned());
            assertEquals(new Coordinate(6, 1), result.finalPosition());
        }

        @Test
        @DisplayName("No oil patches - should return zero cleaned")
        void noOilPatches_shouldReturnZeroCleaned() {
            // Given
            CleaningArea area = new CleaningArea(new Dimensions(7, 10));
            RoboticCleaner cleaner = new RoboticCleaner(area, new Coordinate(1, 5));

            // When
            CleaningResult result = cleaner.executeNavigation("WSWSSSEENENEENEESSS");

            // Then
            assertNotNull(result);
            assertEquals(0, result.oilPatchesCleaned());
            assertEquals(new Coordinate(6, 1), result.finalPosition());
        }

        @Test
        @DisplayName("Navigation at boundary - should stay within bounds")
        void navigationAtBoundary_shouldStayWithinBounds() {
            // Given
            CleaningArea area = new CleaningArea(new Dimensions(3, 3));
            RoboticCleaner cleaner = new RoboticCleaner(area, new Coordinate(0, 0));

            // When - try to go out of bounds in all directions
            CleaningResult result = cleaner.executeNavigation("SSSWWWNNNEEEE");

            // Then - should be at (2, 2), the top-right corner
            assertEquals(new Coordinate(2, 2), result.finalPosition());
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Starting position out of bounds - should throw exception")
        void startingPositionOutOfBounds_shouldThrowException() {
            // Given
            CleaningArea area = new CleaningArea(new Dimensions(5, 5));

            // When/Then
            assertThrows(IllegalArgumentException.class, () ->
                new RoboticCleaner(area, new Coordinate(5, 5)));
        }
    }
}
