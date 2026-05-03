package com.ecommerce.project.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseStructure {
    private int status;
    private String message;
    private Long timestamp;
}
