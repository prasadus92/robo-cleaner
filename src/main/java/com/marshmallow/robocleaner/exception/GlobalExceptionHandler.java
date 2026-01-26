package com.marshmallow.robocleaner.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

/**
 * Global exception handler implementing RFC 7807 Problem Details for HTTP APIs.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String VALIDATION_ERROR_TYPE = "https://api.robocleaner.com/errors/validation";
    private static final String NAVIGATION_ERROR_TYPE = "https://api.robocleaner.com/errors/navigation";
    private static final String INTERNAL_ERROR_TYPE = "https://api.robocleaner.com/errors/internal";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("Validation failed: {}", ex.getMessage());

        String validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                validationErrors
        );
        problem.setType(URI.create(VALIDATION_ERROR_TYPE));
        problem.setTitle("Validation Failed");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("errorCount", ex.getBindingResult().getErrorCount());

        return problem;
    }

    @ExceptionHandler(StartingPositionOutOfBoundsException.class)
    public ProblemDetail handleStartingPositionOutOfBounds(StartingPositionOutOfBoundsException ex) {
        log.warn("Starting position out of bounds: {}", ex.getMessage());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        problem.setType(URI.create(NAVIGATION_ERROR_TYPE));
        problem.setTitle("Starting Position Out of Bounds");
        problem.setProperty("timestamp", Instant.now());

        return problem;
    }

    @ExceptionHandler(OilPatchOutOfBoundsException.class)
    public ProblemDetail handleOilPatchOutOfBounds(OilPatchOutOfBoundsException ex) {
        log.warn("Oil patch out of bounds: {}", ex.getMessage());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        problem.setType(URI.create(NAVIGATION_ERROR_TYPE));
        problem.setTitle("Oil Patch Out of Bounds");
        problem.setProperty("timestamp", Instant.now());

        return problem;
    }

    @ExceptionHandler(NavigationException.class)
    public ProblemDetail handleNavigationException(NavigationException ex) {
        log.error("Navigation error: {}", ex.getMessage(), ex);

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        problem.setType(URI.create(NAVIGATION_ERROR_TYPE));
        problem.setTitle("Navigation Error");
        problem.setProperty("timestamp", Instant.now());

        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later."
        );
        problem.setType(URI.create(INTERNAL_ERROR_TYPE));
        problem.setTitle("Internal Server Error");
        problem.setProperty("timestamp", Instant.now());

        return problem;
    }

    private String formatFieldError(FieldError error) {
        return String.format("%s: %s", error.getField(), error.getDefaultMessage());
    }
}
