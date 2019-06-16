package com.marshmallow.robocleaner.service;

import org.junit.Test;
import com.marshmallow.robocleaner.model.Area;
import com.marshmallow.robocleaner.model.AreaSize;
import com.marshmallow.robocleaner.model.Position;
import com.marshmallow.robocleaner.model.Solution;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RoboCleanerTest {

    @Test
    public void testBasicUseCase() {
        // Given
        AreaSize areaSize = new AreaSize(Arrays.asList(5, 5));
        Area area = new Area(areaSize);

        Position position = new Position(Arrays.asList(1, 2), areaSize);
        area.applyOilPatch(1, 0, 0);
        area.applyOilPatch(2, 2, 1);
        area.applyOilPatch(2, 3, 2);
        String navigationInstructions = "NNESEESWNWW";
        RoboCleaner roboCleaner = new RoboCleaner(area, position);

        // When
        Solution solution = roboCleaner.clean(navigationInstructions);

        // Then
        assertNotNull(solution);
        assertEquals(1, solution.getOilPatchesCleaned());
        assertEquals(Arrays.asList(1, 3), solution.getFinalPosition());
    }

    @Test
    public void testOilPatchOnInitialPosition() {
        // Given
        AreaSize areaSize = new AreaSize(Arrays.asList(7, 7));
        Area area = new Area(areaSize);

        Position position = new Position(Arrays.asList(1, 5), areaSize);
        area.applyOilPatch(0, 5, 0);
        area.applyOilPatch(0, 1, 1);
        area.applyOilPatch(2, 2, 2);
        area.applyOilPatch(2, 3, 3);
        area.applyOilPatch(3, 3, 4);
        area.applyOilPatch(4, 6, 5);
        area.applyOilPatch(5, 4, 6);
        area.applyOilPatch(6, 1, 7);
        // Initial position (1, 5) has an oil patch
        area.applyOilPatch(1, 5, 8);
        String navigationInstructions = "WSWSSSEENENEENEESSS";
        RoboCleaner roboCleaner = new RoboCleaner(area, position);

        // When
        Solution solution = roboCleaner.clean(navigationInstructions);

        // Then
        assertNotNull(solution);
        assertEquals(8, solution.getOilPatchesCleaned());
        assertEquals(Arrays.asList(6, 1), solution.getFinalPosition());
    }

    @Test
    public void testNoOilPatch() {
        // Given
        AreaSize areaSize = new AreaSize(Arrays.asList(7, 10));
        Area area = new Area(areaSize);

        Position position = new Position(Arrays.asList(1, 5), areaSize);
        String navigationInstructions = "WSWSSSEENENEENEESSS";
        RoboCleaner roboCleaner = new RoboCleaner(area, position);

        // When
        Solution solution = roboCleaner.clean(navigationInstructions);

        // Then
        assertNotNull(solution);
        assertEquals(0, solution.getOilPatchesCleaned());
        assertEquals(Arrays.asList(6, 1), solution.getFinalPosition());
    }
}
