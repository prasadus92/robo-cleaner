package com.marshmallow.robocleaner.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Response DTO for the navigation endpoint.
 * Contains the result of the robotic cleaner simulation.
 */
@Schema(description = "Navigation response containing final position and number of cleaned patches")
public record NavigationResponse(

        @Schema(description = "Final position of the cleaner as [x, y] coordinates",
                example = "[1, 3]")
        List<Integer> finalPosition,

        @Schema(description = "Number of oil patches cleaned during navigation",
                example = "1")
        int oilPatchesCleaned
) {
}
