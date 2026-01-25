package com.marshmallow.robocleaner.api;

import com.marshmallow.robocleaner.validation.NavigationInstructions;
import com.marshmallow.robocleaner.validation.OilPatches;
import com.marshmallow.robocleaner.validation.PositiveDimensions;
import com.marshmallow.robocleaner.validation.ValidCoordinate;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Request DTO for the navigation endpoint.
 * Contains all input parameters required for the robotic cleaner simulation.
 */
@Schema(description = "Navigation request containing area dimensions, starting position, oil patches, and instructions")
public record NavigationRequest(

        @Schema(description = "Area dimensions as [width, height], both must be positive integers",
                example = "[5, 5]",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @PositiveDimensions
        List<Integer> areaSize,

        @Schema(description = "Starting position as [x, y] coordinates, must be within area bounds",
                example = "[1, 2]",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @ValidCoordinate
        List<Integer> startingPosition,

        @Schema(description = "List of oil patch positions, each as [x, y] coordinates",
                example = "[[1, 0], [2, 2], [2, 3]]",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @OilPatches
        List<List<Integer>> oilPatches,

        @Schema(description = "Navigation instructions as a string of directions (N=North, S=South, E=East, W=West)",
                example = "NNESEESWNWW",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NavigationInstructions
        String navigationInstructions
) {
}
