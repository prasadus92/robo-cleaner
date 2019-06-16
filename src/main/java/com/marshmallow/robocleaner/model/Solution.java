package com.marshmallow.robocleaner.model;

import lombok.Value;

import java.util.Arrays;
import java.util.List;

/**
 * Class that represent the final solution
 */
@Value
public class Solution {

    private List<Integer> finalPosition;
    private int oilPatchesCleaned;

    /**
     * Solution constructor.
     *
     * @param position      Final position of the Robo Cleaner
     * @param oilPatchesCleaned  Oil Patches that have been cleaned
     */
    public Solution(Position position, int oilPatchesCleaned) {
        this.finalPosition = Arrays.asList(position.getX(), position.getY());
        this.oilPatchesCleaned = oilPatchesCleaned;
    }
}
