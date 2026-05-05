package com.ecommerce.project.service;

import com.ecommerce.project.Exceptions.ResourceAlreadyExistsException;
import com.ecommerce.project.Exceptions.ResourceNotFoundException;
import com.ecommerce.project.Repository.CategoryRepository;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll(); // fetching categories from DB

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No categories found");
        }
        List<CategoryDTO> categoryDTOS = categories.stream() // mapping each category object to categoryDTO
                .map(category -> modelMapper
                .map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryList(categoryDTOS); // changing the dto to category response
        return categoryResponse;
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