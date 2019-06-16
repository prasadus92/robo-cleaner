package com.marshmallow.robocleaner.model;

import lombok.Value;
import com.marshmallow.robocleaner.exception.OilPatchOutOfAreaException;

import java.util.List;

import static com.marshmallow.robocleaner.model.Grid.CLEAN;
import static com.marshmallow.robocleaner.model.Grid.OIL_PATCH;

/**
 * Represents the Area of our Robo Cleaner.
 *
 */
@Value
public class Area {

    private Grid[][] grids;
    private AreaSize areaSize;

    public Area(AreaSize areaSize) {
        this.areaSize = areaSize;
        Integer m = areaSize.getM();
        Integer n = areaSize.getN();
        grids = new Grid[m][n];

        // Initialise grids
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                grids[x][y] = CLEAN;
            }
        }
    }

    /**
     * Adds the oil patch to a certain grid
     *
     * @param  coordinates       Coordinates of the oil patch as a List
     * @param  oilPatchIndex  The index or the number of the oil patch
     * @throws OilPatchOutOfAreaException
     */
    public void applyOilPatch(List<Integer> coordinates, int oilPatchIndex) {
        applyOilPatch(coordinates.get(0), coordinates.get(1), oilPatchIndex);
    }

    /**
     * Adds the oil patch to a certain coordinates
     *
     * @param x  The x coordinate
     * @param y  The y coordinate
     */
    public void applyOilPatch(int x, int y, int oilPatchIndex) {
        if (x >= areaSize.getM() ||
            y >= areaSize.getN()) {
            throw new OilPatchOutOfAreaException(oilPatchIndex);
        }
        grids[x][y] = OIL_PATCH;
    }

    /**
     * Checks if a certain grid has oil patch
     *
     * @param  position  Position to check
     * @return boolean   true if it has, false otherwise
     */
    public boolean hasOilPatch(Position position) {
        return grids[position.getX()][position.getY()] == OIL_PATCH;
    }

    /**
     * Removes the oil patch from a certain grid
     *
     * @param  position  Position to remove oil patch from
     */
    public void removeOilPatch(Position position) {
        grids[position.getX()][position.getY()] = CLEAN;
    }
}
