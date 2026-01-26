package com.marshmallow.robocleaner.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Coordinate Unit Tests")
class CoordinateTest {

    @Test
    @DisplayName("fromList - valid list should create coordinate")
    void fromList_validList_shouldCreateCoordinate() {
        Coordinate coord = Coordinate.fromList(Arrays.asList(3, 5));

        assertEquals(3, coord.x());
        assertEquals(5, coord.y());
    }

    @Test
    @DisplayName("fromList - null list should throw exception")
    void fromList_nullList_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
            Coordinate.fromList(null));
    }

    @Test
    @DisplayName("move North - should increase y")
    void moveNorth_shouldIncreaseY() {
        Coordinate coord = new Coordinate(2, 3);
        Coordinate moved = coord.move(Direction.N);

        assertEquals(new Coordinate(2, 4), moved);
    }

    @Test
    @DisplayName("isWithinBounds - inside bounds should return true")
    void isWithinBounds_insideBounds_shouldReturnTrue() {
        Coordinate coord = new Coordinate(2, 3);
        assertTrue(coord.isWithinBounds(5, 5));
    }

    @Test
    @DisplayName("isWithinBounds - at boundary should return false")
    void isWithinBounds_atBoundary_shouldReturnFalse() {
        Coordinate coord = new Coordinate(5, 5);
        assertFalse(coord.isWithinBounds(5, 5));
    }

    @Test
    @DisplayName("toList - should return correct list")
    void toList_shouldReturnCorrectList() {
        Coordinate coord = new Coordinate(3, 5);
        assertEquals(Arrays.asList(3, 5), coord.toList());
    }
}
