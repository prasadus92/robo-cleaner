package com.marshmallow.robocleaner.controller;

import com.marshmallow.robocleaner.api.NavigationRequest;
import com.marshmallow.robocleaner.api.NavigationResponse;
import com.marshmallow.robocleaner.service.NavigationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for robotic cleaner navigation operations.
 */
@RestController
@RequestMapping("/api/v1/robocleaner")
@Tag(name = "Navigation", description = "Robotic cleaner navigation operations")
public class NavigationController {

    private static final Logger log = LoggerFactory.getLogger(NavigationController.class);

    private final NavigationService navigationService;

    public NavigationController(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    @PostMapping(path = "/navigation", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Navigate the robotic cleaner",
            description = "Execute a navigation sequence for the robotic cleaner through the sea area, cleaning oil patches along the way"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Navigation completed successfully",
                    content = @Content(schema = @Schema(implementation = NavigationResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input parameters",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    public NavigationResponse navigate(@RequestBody @Valid NavigationRequest request) {
        log.info("Received navigation request");
        return navigationService.navigate(request);
    }
}
