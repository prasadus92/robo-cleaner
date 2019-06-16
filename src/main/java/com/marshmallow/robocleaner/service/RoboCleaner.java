package com.marshmallow.robocleaner.service;

import com.marshmallow.robocleaner.model.Area;
import com.marshmallow.robocleaner.model.Position;
import com.marshmallow.robocleaner.model.Solution;

public class RoboCleaner {

    private Position previousPosition;
    private Area area;
    private Position position;
    private int oilPatchesCleaned;

    /**
     * Constructor
     *
     * @param area       Robot is aware of the whole area.
     * @param position  Initial position of the robot
     */
    public RoboCleaner(Area area, Position position) {
        this.area = area;
        this.position = position;
        previousPosition = position;
        oilPatchesCleaned = 0;
    }

    /**
     * Makes the robot move to a direction
     *
     * @param direction  Direction to move towards
     */
    private void move(char direction) {
        previousPosition.setLocation(position);

        switch (direction) {
            case 'N':
                position.incrementY();
                break;
            case 'S':
                position.decrementY();
                break;
            case 'E':
                position.incrementX();
                break;
            case 'W':
                position.decrementX();
                break;
            default:
                break;
        }

        // If a position is not in the area, the robot stays in the same place
        if (position.isValid()) {
            doClean(position);
        } else {
            position.setLocation(previousPosition);
        }
    }

    /**
     * Goes through the navigation instructions and checks for dirts to clean.
     *
     * @param  navigationInstructions  Our instructions
     * @return Solution             The solution of our current run
     */
    public Solution clean(String navigationInstructions) {
        // Check if initial position needs cleaning
        if (position.isValid()) {
            doClean(position);
        }

        for (int i = 0; i < navigationInstructions.length(); i++) {
            move(navigationInstructions.charAt(i));
        }

        return new Solution(position, oilPatchesCleaned);
    }

    /**
     * Cleans dirt on a specific position
     *
     * @param position  Position with potential dirt to be cleaned
     */
    private void doClean(Position position) {
        if (area.hasOilPatch(position)) {
            area.removeOilPatch(position);
            oilPatchesCleaned++;
        }
    }
}
