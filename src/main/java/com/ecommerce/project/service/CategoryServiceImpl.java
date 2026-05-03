package com.ecommerce.project.service;

import com.ecommerce.project.Exceptions.ResourceAlreadyExistsException;
import com.ecommerce.project.Exceptions.ResourceNotFoundException;
import com.ecommerce.project.Repository.CategoryRepository;
import com.ecommerce.project.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No categories found");
        }
        return categories;
    }

    @Override
    public Category createCategory(Category category) {

        Optional<Category> existing = categoryRepository
                .findByCategoryName(category.getCategoryName());
        if(existing.isPresent()){
            throw new ResourceAlreadyExistsException("Category already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category deleteCategory(Long id) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        categoryRepository.delete(c);
        return c;
    }

    @Override
    public Category updateCategory(Long id, Category updatedCategory) {

        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (updatedCategory.getCategoryName() != null) {
            c.setCategoryName(updatedCategory.getCategoryName());
        }

        return categoryRepository.save(c); // we have updated the existing category
    }
}