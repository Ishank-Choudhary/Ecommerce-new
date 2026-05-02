package com.ecommerce.project.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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

}
