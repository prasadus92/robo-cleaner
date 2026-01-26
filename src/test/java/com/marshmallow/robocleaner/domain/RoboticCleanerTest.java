package com.marshmallow.robocleaner.domain;

import com.marshmallow.robocleaner.model.Coordinate;
import com.marshmallow.robocleaner.model.Dimensions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Robotic Cleaner Unit Tests")
class RoboticCleanerTest {

    @Test
    @DisplayName("Basic navigation - should clean patch and return correct position")
    void basicNavigation_shouldReturnCorrectResult() {
        CleaningArea area = new CleaningArea(new Dimensions(5, 5));
        area.applyOilPatch(new Coordinate(1, 0), 0);
        area.applyOilPatch(new Coordinate(2, 2), 1);
        area.applyOilPatch(new Coordinate(2, 3), 2);

        RoboticCleaner cleaner = new RoboticCleaner(area, new Coordinate(1, 2));
        CleaningResult result = cleaner.executeNavigation("NNESEESWNWW");

        assertNotNull(result);
        assertEquals(1, result.oilPatchesCleaned());
        assertEquals(new Coordinate(1, 3), result.finalPosition());
    }

    @Test
    @DisplayName("Navigation at boundary - should stay within bounds")
    void navigationAtBoundary_shouldStayWithinBounds() {
        CleaningArea area = new CleaningArea(new Dimensions(3, 3));
        RoboticCleaner cleaner = new RoboticCleaner(area, new Coordinate(0, 0));

        CleaningResult result = cleaner.executeNavigation("SSSWWWNNNEEEE");

        assertEquals(new Coordinate(2, 2), result.finalPosition());
    }

    @Test
    @DisplayName("Starting position out of bounds - should throw exception")
    void startingPositionOutOfBounds_shouldThrowException() {
        CleaningArea area = new CleaningArea(new Dimensions(5, 5));

        assertThrows(IllegalArgumentException.class, () ->
            new RoboticCleaner(area, new Coordinate(5, 5)));
    }
}
