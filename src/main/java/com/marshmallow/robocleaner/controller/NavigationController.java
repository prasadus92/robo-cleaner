package com.marshmallow.robocleaner.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.marshmallow.robocleaner.exception.GenericException;
import com.marshmallow.robocleaner.model.Instructions;
import com.marshmallow.robocleaner.model.Solution;
import com.marshmallow.robocleaner.service.NavigationService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping ("/api/v1/robocleaner")
@Api (value = "robocleaner", description = "Available operations for Robo Cleaner")
@RequiredArgsConstructor
@Slf4j
public class NavigationController {

    private final NavigationService navigationService;

    @PostMapping (path = "/navigation", produces = "application/json")
    @ApiOperation (value = "Navigate Robo Cleaner")
    @ApiResponses ({
        @ApiResponse (code = 200, message = "Success"),
        @ApiResponse (code = 400, message = "Bad request")
    })
    public Solution navigate(@RequestBody @Valid Instructions instructions) {
        log.info("New navigation request with instructions - {}", instructions);

        Optional<Solution> found = navigationService.navigate(instructions);
        if (found.isPresent()) {
            return found.get();
        }

        throw new GenericException("Error encountered during the Robo Cleaner navigation");
    }
}
