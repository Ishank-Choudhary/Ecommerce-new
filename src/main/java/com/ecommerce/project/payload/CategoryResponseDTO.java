package com.ecommerce.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
// Response DTO are used to send structured data back to the client
public class CategoryResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<CategoryRequestDTO> categoryList;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
    private boolean lastPage;
}
