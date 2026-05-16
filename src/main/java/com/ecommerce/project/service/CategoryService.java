package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryRequestDTO;
import com.ecommerce.project.payload.CategoryResponseDTO;

public interface CategoryService {

    CategoryResponseDTO getAllCategories(Integer pageNumber, Integer pageSize,String sortBy, String sortOrder);
    CategoryRequestDTO getCategoryById(Long id);
    CategoryRequestDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    Category deleteCategory(Long id);
    CategoryRequestDTO updateCategory(Long id,CategoryRequestDTO updatedCategory);

}
