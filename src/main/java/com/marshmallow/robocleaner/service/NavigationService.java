package com.marshmallow.robocleaner.service;

import com.marshmallow.robocleaner.api.NavigationRequest;
import com.marshmallow.robocleaner.api.NavigationResponse;
import com.marshmallow.robocleaner.domain.CleaningArea;
import com.marshmallow.robocleaner.domain.CleaningResult;
import com.marshmallow.robocleaner.domain.RoboticCleaner;
import com.marshmallow.robocleaner.exception.OilPatchOutOfBoundsException;
import com.marshmallow.robocleaner.exception.StartingPositionOutOfBoundsException;
import com.marshmallow.robocleaner.model.Coordinate;
import com.marshmallow.robocleaner.model.Dimensions;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service layer for handling navigation operations.
 * Orchestrates the cleaning simulation process.
 */
@Service
public class NavigationService {

    private static final Logger log = LoggerFactory.getLogger(NavigationService.class);

    /**
     * Execute a navigation operation based on the given request.
     *
     * @param request the navigation request containing all input parameters
     * @return the navigation response with the result
     * @throws StartingPositionOutOfBoundsException if the starting position is outside the area
     * @throws OilPatchOutOfBoundsException        if any oil patch is outside the area
     */
    @Timed(value = "navigation.execute", description = "Time taken to execute navigation")
    public NavigationResponse navigate(NavigationRequest request) {
        log.info("Processing navigation request: areaSize={}, startingPosition={}, oilPatches={}, instructionsLength={}",
                request.areaSize(),
                request.startingPosition(),
                request.oilPatches().size(),
                request.navigationInstructions().length());

        // Create the cleaning area
        Dimensions dimensions = Dimensions.fromList(request.areaSize());
        CleaningArea area = new CleaningArea(dimensions);

        // Parse starting position
        Coordinate startingPosition = Coordinate.fromList(request.startingPosition());

        // Validate starting position is within bounds
        if (!area.isWithinBounds(startingPosition)) {
            throw new StartingPositionOutOfBoundsException(startingPosition, dimensions);
        }

        // Apply oil patches
        try {
            area.applyOilPatches(request.oilPatches());
        } catch (IllegalArgumentException e) {
            throw new OilPatchOutOfBoundsException(e.getMessage());
        }

        // Execute the navigation
        RoboticCleaner cleaner = new RoboticCleaner(area, startingPosition);
        CleaningResult result = cleaner.executeNavigation(request.navigationInstructions());

        log.info("Navigation completed: finalPosition={}, oilPatchesCleaned={}",
                result.finalPosition(), result.oilPatchesCleaned());

        return new NavigationResponse(
                result.finalPosition().toList(),
                result.oilPatchesCleaned()
        );
    }
}
