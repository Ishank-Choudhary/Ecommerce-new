package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.CategoryRequestDTO;
import com.ecommerce.project.payload.CategoryResponseDTO;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class CategoryController {

    private CategoryService categoryService; // to store dependency

    public CategoryController(CategoryService categoryService) { // to inject dependency
        this.categoryService = categoryService;
    }


    //@GetMapping("/api/public/getCategories")
    @RequestMapping(value = "/getCategories", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponseDTO> getAllCategory(
            @RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name="sortBy",defaultValue = AppConstants.SORT_CATEGORIES_BY) String sortBy,
            @RequestParam(name="sortOrder",defaultValue = AppConstants.SORT_CATEGORIES_ORDER) String sortOrder){
        CategoryResponseDTO response = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryRequestDTO> getCategoryById(@PathVariable Long id) {

        CategoryRequestDTO category = categoryService.getCategoryById(id);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    //@PostMapping("/api/public/postCategories")
    @RequestMapping(value = "/postCategories", method = RequestMethod.POST)
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
        CategoryRequestDTO created = categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully");
    }

   //@DeleteMapping("/api/public/deleteCategory/{id}")
    @RequestMapping(value = "/deleteCategory/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    //@PutMapping("/api/public/updateCategory/{id}")
    @RequestMapping(value = "/updateCategory/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO updatedCategory){

        categoryService.updateCategory(id,updatedCategory);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Category updated successfully");

    }

}
