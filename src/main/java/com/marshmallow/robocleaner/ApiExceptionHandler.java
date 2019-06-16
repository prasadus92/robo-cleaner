package com.marshmallow.robocleaner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.marshmallow.robocleaner.exception.GenericException;
import com.marshmallow.robocleaner.exception.OilPatchOutOfAreaException;
import com.marshmallow.robocleaner.model.ErrorDto;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.error("Request isn't valid", ex);
        return new ResponseEntity(ErrorDto.builder().message(ex.getMessage()).build(), headers, status);
    }

    @ExceptionHandler (OilPatchOutOfAreaException.class)
    public final ResponseEntity<Object> handleOilPatchOutOfGridError(OilPatchOutOfAreaException ex, WebRequest request) {
        log.warn("Handling OilPatchOutOfAreaException: request - {}, error - {}", request.getDescription(false), ex.getMessage());
        return new ResponseEntity<>(ErrorDto.builder().message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (GenericException.class)
    public final ResponseEntity<Object> handleGenericError(GenericException ex, WebRequest request) {
        log.warn("Handling GenericException: request - {}, error - {}", request.getDescription(false), ex.getMessage());
        return new ResponseEntity<>(ErrorDto.builder().message(ex.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
