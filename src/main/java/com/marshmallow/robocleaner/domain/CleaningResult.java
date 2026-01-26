package com.marshmallow.robocleaner.domain;

import com.marshmallow.robocleaner.model.Coordinate;

/**
 * Immutable result of a cleaning navigation operation.
 *
 * @param finalPosition     the final position of the cleaner
 * @param oilPatchesCleaned the number of oil patches cleaned
 */
public record CleaningResult(Coordinate finalPosition, int oilPatchesCleaned) {
}
