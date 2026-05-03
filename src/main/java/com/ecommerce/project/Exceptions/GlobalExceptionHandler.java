package com.ecommerce.project.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,String> response = new HashMap<>();
       for(FieldError err: e.getBindingResult().getFieldErrors()){
           response.put(err.getField(),err.getDefaultMessage());
       }
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseStructure> resourceNotFound(ResourceNotFoundException e){
        ErrorResponseStructure errorResponseStructure = new ErrorResponseStructure(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseStructure);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseStructure> handleAlreadyExists(ResourceAlreadyExistsException e){
        ErrorResponseStructure errorResponseStructure = new ErrorResponseStructure(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseStructure);
    }


}
