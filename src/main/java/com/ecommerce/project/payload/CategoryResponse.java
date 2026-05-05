package com.ecommerce.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
// Response DTO are used to send structured data back to the client
public class CategoryResponse {
    private List<CategoryDTO> categoryList;
}
