package org.example.restcontroller_controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(value = IndexException.class)
    ResponseEntity<String> handleIndexException(IndexException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }
}
