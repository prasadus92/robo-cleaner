package com.marshmallow.robocleaner.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Coordinate Unit Tests")
class CoordinateTest {

    @Nested
    @DisplayName("Factory Method Tests")
    class FactoryMethodTests {

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
        @DisplayName("fromList - wrong size list should throw exception")
        void fromList_wrongSizeList_shouldThrowException() {
            assertThrows(IllegalArgumentException.class, () ->
                Coordinate.fromList(Arrays.asList(1)));
        }
    }

    @Nested
    @DisplayName("Movement Tests")
    class MovementTests {

        @Test
        @DisplayName("move North - should increase y")
        void moveNorth_shouldIncreaseY() {
            Coordinate coord = new Coordinate(2, 3);
            Coordinate moved = coord.move(Direction.N);

            assertEquals(new Coordinate(2, 4), moved);
        }

        @Test
        @DisplayName("move South - should decrease y")
        void moveSouth_shouldDecreaseY() {
            Coordinate coord = new Coordinate(2, 3);
            Coordinate moved = coord.move(Direction.S);

            assertEquals(new Coordinate(2, 2), moved);
        }

        @Test
        @DisplayName("move East - should increase x")
        void moveEast_shouldIncreaseX() {
            Coordinate coord = new Coordinate(2, 3);
            Coordinate moved = coord.move(Direction.E);

            assertEquals(new Coordinate(3, 3), moved);
        }

        @Test
        @DisplayName("move West - should decrease x")
        void moveWest_shouldDecreaseX() {
            Coordinate coord = new Coordinate(2, 3);
            Coordinate moved = coord.move(Direction.W);

            assertEquals(new Coordinate(1, 3), moved);
        }
    }

    @Nested
    @DisplayName("Boundary Check Tests")
    class BoundaryCheckTests {

        @Test
        @DisplayName("isWithinBounds - inside bounds should return true")
        void isWithinBounds_insideBounds_shouldReturnTrue() {
            Coordinate coord = new Coordinate(2, 3);
            assertTrue(coord.isWithinBounds(5, 5));
        }

        @Test
        @DisplayName("isWithinBounds - at origin should return true")
        void isWithinBounds_atOrigin_shouldReturnTrue() {
            Coordinate coord = new Coordinate(0, 0);
            assertTrue(coord.isWithinBounds(5, 5));
        }

        @Test
        @DisplayName("isWithinBounds - at boundary should return false")
        void isWithinBounds_atBoundary_shouldReturnFalse() {
            Coordinate coord = new Coordinate(5, 5);
            assertFalse(coord.isWithinBounds(5, 5));
        }

        @Test
        @DisplayName("isWithinBounds - negative x should return false")
        void isWithinBounds_negativeX_shouldReturnFalse() {
            Coordinate coord = new Coordinate(-1, 3);
            assertFalse(coord.isWithinBounds(5, 5));
        }
    }

    @Nested
    @DisplayName("Serialization Tests")
    class SerializationTests {

        @Test
        @DisplayName("toList - should return correct list")
        void toList_shouldReturnCorrectList() {
            Coordinate coord = new Coordinate(3, 5);
            List<Integer> list = coord.toList();

            assertEquals(Arrays.asList(3, 5), list);
        }
    }
}
