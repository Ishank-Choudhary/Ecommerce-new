package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    Category createCategory(Category category);
    Category deleteCategory(Long id);
    Category updateCategory(Long id,Category updatedCategory);

}
