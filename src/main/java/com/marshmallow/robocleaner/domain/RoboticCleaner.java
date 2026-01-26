package com.marshmallow.robocleaner.domain;

import com.marshmallow.robocleaner.model.Coordinate;
import com.marshmallow.robocleaner.model.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Domain entity representing the robotic cleaner.
 * Handles movement and cleaning logic.
 */
public class RoboticCleaner {

    private static final Logger log = LoggerFactory.getLogger(RoboticCleaner.class);

    private final CleaningArea area;
    private Coordinate position;
    private int oilPatchesCleaned;

    /**
     * Create a new robotic cleaner in the given area at the starting position.
     *
     * @param area             the cleaning area
     * @param startingPosition the initial position of the cleaner
     * @throws IllegalArgumentException if the starting position is outside the area
     */
    public RoboticCleaner(CleaningArea area, Coordinate startingPosition) {
        if (!area.isWithinBounds(startingPosition)) {
            throw new IllegalArgumentException(
                    "Starting position is outside the area bounds: " + startingPosition);
        }
        this.area = area;
        this.position = startingPosition;
        this.oilPatchesCleaned = 0;
    }

    /**
     * Execute the cleaning navigation following the given instructions.
     *
     * @param instructions string of direction characters (N, S, E, W)
     * @return the result of the cleaning operation
     */
    public CleaningResult executeNavigation(String instructions) {
        log.debug("Starting navigation from position {} with instructions: {}", position, instructions);

        // Clean initial position if needed
        cleanCurrentPosition();

        // Process each instruction
        for (char instruction : instructions.toCharArray()) {
            move(Direction.fromChar(instruction));
        }

        log.debug("Navigation complete. Final position: {}, patches cleaned: {}", position, oilPatchesCleaned);
        return new CleaningResult(position, oilPatchesCleaned);
    }

    /**
     * Move the cleaner in the specified direction.
     * If the new position would be outside the area, the cleaner stays in place.
     *
     * @param direction the direction to move
     */
    private void move(Direction direction) {
        Coordinate newPosition = position.move(direction);

        if (area.isWithinBounds(newPosition)) {
            position = newPosition;
            cleanCurrentPosition();
            log.trace("Moved {} to position {}", direction, position);
        } else {
            log.trace("Cannot move {} from {} - would be out of bounds", direction, position);
        }
    }

    /**
     * Clean the current position if there is an oil patch.
     */
    private void cleanCurrentPosition() {
        if (area.hasOilPatch(position)) {
            area.cleanOilPatch(position);
            oilPatchesCleaned++;
            log.trace("Cleaned oil patch at position {}", position);
        }
    }

    public Coordinate getPosition() {
        return position;
    }

    public int getOilPatchesCleaned() {
        return oilPatchesCleaned;
    }
}
