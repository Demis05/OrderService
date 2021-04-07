package com.exadel.fedorov.orders.exception;

import org.modelmapper.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public final ResponseEntity<Object> handleInvalidDataException(InvalidDataException cause) {
        return new ResponseEntity<>(cause.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchDataException.class)
    public final ResponseEntity<Object> handleNoDataException(NoSuchDataException cause) {
        return new ResponseEntity<>(cause.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MappingException.class})
    public final ResponseEntity<Object> handleInvalidInputDataException(MappingException cause) {
        InvalidDataException exception = new InvalidDataException("Invalid input data error.");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

}