package com.ecommerce.project.service;

import com.ecommerce.project.Repository.CategoryRepository;
import com.ecommerce.project.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category); // ID auto-generated
    }

    @Override
    public Category deleteCategory(Long id) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(c);
        return c;
    }

    @Override
    public Category updateCategory(Long id, Category updatedCategory) {

        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (updatedCategory.getCategoryName() != null) {
            c.setCategoryName(updatedCategory.getCategoryName());
        }

        return categoryRepository.save(c); // we have updated the existing category
    }
}