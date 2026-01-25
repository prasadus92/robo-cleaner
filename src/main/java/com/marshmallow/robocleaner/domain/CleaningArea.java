package com.marshmallow.robocleaner.domain;

import com.marshmallow.robocleaner.model.Coordinate;
import com.marshmallow.robocleaner.model.Dimensions;
import com.marshmallow.robocleaner.model.Grid;

import java.util.List;

import static com.marshmallow.robocleaner.model.Grid.CLEAN;
import static com.marshmallow.robocleaner.model.Grid.OIL_PATCH;

/**
 * Represents the sea area where the robotic cleaner operates.
 * Manages the grid state and oil patch locations.
 */
public class CleaningArea {

    private final Dimensions dimensions;
    private final Grid[][] grid;

    /**
     * Create a new cleaning area with the given dimensions.
     * All cells are initialized to CLEAN.
     *
     * @param dimensions the dimensions of the area
     */
    public CleaningArea(Dimensions dimensions) {
        this.dimensions = dimensions;
        this.grid = new Grid[dimensions.width()][dimensions.height()];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int x = 0; x < dimensions.width(); x++) {
            for (int y = 0; y < dimensions.height(); y++) {
                grid[x][y] = CLEAN;
            }
        }
    }

    /**
     * Apply oil patches to the grid.
     *
     * @param oilPatches list of coordinate lists representing oil patch positions
     * @throws IllegalArgumentException if any oil patch is outside the area bounds
     */
    public void applyOilPatches(List<List<Integer>> oilPatches) {
        for (int i = 0; i < oilPatches.size(); i++) {
            Coordinate coord = Coordinate.fromList(oilPatches.get(i));
            applyOilPatch(coord, i);
        }
    }

    /**
     * Apply a single oil patch at the given coordinate.
     *
     * @param coordinate     the coordinate of the oil patch
     * @param oilPatchIndex  the index of the oil patch (for error reporting)
     * @throws IllegalArgumentException if the coordinate is outside the area bounds
     */
    public void applyOilPatch(Coordinate coordinate, int oilPatchIndex) {
        if (!dimensions.contains(coordinate)) {
            throw new IllegalArgumentException(
                    String.format("Oil patch at index %d is outside the area bounds: %s", oilPatchIndex, coordinate));
        }
        grid[coordinate.x()][coordinate.y()] = OIL_PATCH;
    }

    /**
     * Check if the given coordinate has an oil patch.
     *
     * @param coordinate the coordinate to check
     * @return true if there is an oil patch at this coordinate
     */
    public boolean hasOilPatch(Coordinate coordinate) {
        return grid[coordinate.x()][coordinate.y()] == OIL_PATCH;
    }

    /**
     * Remove the oil patch at the given coordinate (clean it).
     *
     * @param coordinate the coordinate to clean
     */
    public void cleanOilPatch(Coordinate coordinate) {
        grid[coordinate.x()][coordinate.y()] = CLEAN;
    }

    /**
     * Check if a coordinate is within the area bounds.
     *
     * @param coordinate the coordinate to check
     * @return true if the coordinate is within bounds
     */
    public boolean isWithinBounds(Coordinate coordinate) {
        return dimensions.contains(coordinate);
    }

    public Dimensions getDimensions() {
        return dimensions;
    }
}
