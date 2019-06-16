package com.marshmallow.robocleaner.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ErrorDto {
    private String message;
}
