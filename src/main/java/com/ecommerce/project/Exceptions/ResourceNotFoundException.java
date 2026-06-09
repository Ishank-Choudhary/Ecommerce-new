package com.ecommerce.project.Exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String category, String categoryId, Long categoryId1) {
    }
}
