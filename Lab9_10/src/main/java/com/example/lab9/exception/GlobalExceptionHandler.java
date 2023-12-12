package com.example.lab9.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;



@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
      
        BindingResult bindingResult = e.getBindingResult();

        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
    
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            errors
        ));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> handleInvalidFormatException(InvalidFormatException e) {
        String fieldName = e.getPath().get(0).getFieldName();
        String expectedType = e.getTargetType().getSimpleName();
        String actualValue = e.getValue().toString();

        String errorMessage = "Field " + fieldName + " have invalid format. Valid format must be " + expectedType + ". But the actual value is: " + actualValue;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            errorMessage
        ));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleUnsuppotedMethod(HttpRequestMethodNotSupportedException e) {
        String method = e.getMethod();
        String supportedMethods = String.join(", ", e.getSupportedMethods());
        String errorMessage = "Method " + method + " is unsupported in this resources. Supported methods is: " + supportedMethods;

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ErrorMessage(
            HttpStatus.METHOD_NOT_ALLOWED.value(),
            errorMessage
        ));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleInvalidEndPoint(NoHandlerFoundException e) {
       
        String httpMethod = e.getHttpMethod();
        String requestURI = e.getRequestURL();
        String errorMessage = "Not found valid endpoint with method " + httpMethod + " " + requestURI;

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            errorMessage
        ));
    }

}
