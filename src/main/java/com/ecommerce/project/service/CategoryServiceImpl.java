package com.ecommerce.project.service;

import com.ecommerce.project.Exceptions.ResourceAlreadyExistsException;
import com.ecommerce.project.Exceptions.ResourceNotFoundException;
import com.ecommerce.project.Repository.CategoryRepository;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryRequestDTO;
import com.ecommerce.project.payload.CategoryResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Cacheable(value = "categories")
    public CategoryResponseDTO getAllCategories(Integer pageNumber, Integer pageSize,String sortBy, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent(); // return the list of category

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No categories found");
        }
        List<CategoryRequestDTO> categoryRequestDTOS = categories.stream() // mapping each category object to categoryDTO
                .map(category -> modelMapper
                .map(category, CategoryRequestDTO.class))
                .collect(Collectors.toList());
        // the below info we need for pagination
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setCategoryList(categoryRequestDTOS); // changing the dto to category response
        categoryResponseDTO.setPageNumber(categoryPage.getNumber());
        categoryResponseDTO.setPageSize(categoryPage.getSize());
        categoryResponseDTO.setTotalPages(categoryPage.getTotalPages());
        categoryResponseDTO.setTotalElements(categoryPage.getTotalElements());
        categoryResponseDTO.setLastPage(categoryResponseDTO.isLastPage());
        return categoryResponseDTO;
    }

    @Override
    @Cacheable(value = "categoryById", key = "#id")
    public CategoryRequestDTO getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return modelMapper.map(category, CategoryRequestDTO.class);
    }

    @Override
    public CategoryRequestDTO createCategory(CategoryRequestDTO categoryRequestDTO) {

        Optional<Category> existing = categoryRepository
                .findByCategoryName(categoryRequestDTO.getCategoryName());
        if(existing.isPresent()){
            throw new ResourceAlreadyExistsException("Category already exists");
        }
        // DTO to Entity
        Category entity = new Category();
        entity.setCategoryName(categoryRequestDTO.getCategoryName());
        Category saved = categoryRepository.save(entity);

        // Entity to DTO
        CategoryRequestDTO response = new CategoryRequestDTO();
        response.setCategoryName(saved.getCategoryName());

        return  response;
    }

    @Override
    @CacheEvict(value = {"categories", "categoryById"}, key = "#id", allEntries = true)
    public Category deleteCategory(Long id) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        categoryRepository.delete(c);
        return c;
    }

    @Override
    @CacheEvict(value = {"categories", "categoryById"}, key = "#id", allEntries = true)
    public CategoryRequestDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {

        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (requestDTO.getCategoryName() != null) {
            existing.setCategoryName(requestDTO.getCategoryName());
        }
        Category saved = categoryRepository.save(existing);

        //Enttity to DTO
        CategoryRequestDTO response = new CategoryRequestDTO();
        response.setCategoryName(saved.getCategoryName());

        return response;
    }
}