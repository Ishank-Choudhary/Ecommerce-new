package com.ecommerce.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Request DTOs are used to receive input from the client
public class CategoryRequestDTO implements Serializable {
    private String categoryName;
}
