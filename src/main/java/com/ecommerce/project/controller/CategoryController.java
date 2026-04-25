package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    private CategoryService categoryService; // to store dependency
    private Category category;

    public CategoryController(CategoryService categoryService) { // to inject dependency
        this.categoryService = categoryService;
    }

    @GetMapping("/api/public/getCategories")
    public List<Category> getAllCategory(){
        return categoryService.getAllCategories();
    }

    @PostMapping("/api/public/postCategories")
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        Category created = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/api/public/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        try{
            Category deleted =  categoryService.deleteCategory(id);
            return ResponseEntity.status(HttpStatus.OK).body(deleted);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
    }

    @PutMapping("/api/public/updateCategory/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory){
        try{
            Category updated = categoryService.updateCategory(id,updatedCategory);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Category updated successfully");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
    }

}
