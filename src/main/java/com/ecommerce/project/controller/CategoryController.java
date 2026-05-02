package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/public")
public class CategoryController {

    private CategoryService categoryService; // to store dependency
    private Category category;

    public CategoryController(CategoryService categoryService) { // to inject dependency
        this.categoryService = categoryService;
    }

    //@GetMapping("/api/public/getCategories")
    @RequestMapping(value = "/getCategories", method = RequestMethod.GET)
    public List<Category> getAllCategory(){
        return categoryService.getAllCategories();
    }

    //@PostMapping("/api/public/postCategories")
    @RequestMapping(value = "/postCategories", method = RequestMethod.POST)
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category){
        Category created = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

   //@DeleteMapping("/api/public/deleteCategory/{id}")
    @RequestMapping(value = "/deleteCategory/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        try{
            Category deleted =  categoryService.deleteCategory(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleted);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Category not found");
        }
    }

    //@PutMapping("/api/public/updateCategory/{id}")
    @RequestMapping(value = "/updateCategory/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory){
        try{
            Category updated = categoryService.updateCategory(id,updatedCategory);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Category updated successfully");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
    }

}
