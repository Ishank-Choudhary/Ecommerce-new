package com.ecommerce.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Request DTOs are used to receive input from the client
public class CategoryDTO {
    private Long categoryId;
    private String categoryName;
}
