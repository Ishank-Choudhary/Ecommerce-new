package com.ecommerce.project.service;

import com.ecommerce.project.Exceptions.ResourceNotFoundException;
import com.ecommerce.project.Repository.CategoryRepository;
import com.ecommerce.project.Repository.ProductRepository;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductRequestDTO;
import com.ecommerce.project.payload.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductRequestDTO addProduct(
            ProductRequestDTO productDTO,
            Long categoryId) {

        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category",
                                "categoryId",
                                categoryId));

        Product product = mapToEntity(productDTO);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);

        return mapToDTO(savedProduct);
    }

    @Override
    public ProductRequestDTO getProductById(Long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product",
                                "productId",
                                productId));

        return mapToDTO(product);
    }

    @Override
    public ProductResponseDTO getAllProductByCategoryId(Long categoryId,
                                                        Integer pageNumber,
                                                        Integer pageSize,
                                                        String sortBy,
                                                        String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> page = productRepository.findByCategoryCategoryId(categoryId,
                pageable);

        List<ProductRequestDTO> productDTOs =
                page.getContent()
                        .stream()
                        .map(this::mapToDTO)
                        .toList();
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setContent(productDTOs);
        responseDTO.setPageNumber(page.getNumber());
        responseDTO.setPageSize(page.getSize());
        responseDTO.setTotalPages(page.getTotalPages());
        responseDTO.setTotalElements(page.getTotalElements());
        responseDTO.setLastPage(page.isLast());

        return responseDTO;
    }

    @Override
    public ProductRequestDTO updateProduct(Long productId, ProductRequestDTO dto) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product",
                                "productId",
                                productId));

        product.setProductName(dto.getProductName());
        product.setImage(dto.getImage());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());
        product.setSpecialPrice(dto.getSpecialPrice());
        product.setDiscount(dto.getDiscount());

        Product updated =
                productRepository.save(product);

        return mapToDTO(updated);
    }

    @Override
    public String deleteProduct(Long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product",
                                "productId",
                                productId));

        productRepository.delete(product);

        return "Product deleted successfully";
    }
    private ProductRequestDTO mapToDTO(Product product) {

        ProductRequestDTO dto =
                new ProductRequestDTO();

        dto.setProductId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setImage(product.getImage());
        dto.setQuantity(product.getQuantity());
        dto.setPrice(product.getPrice());
        dto.setSpecialPrice(product.getSpecialPrice());
        dto.setDiscount(product.getDiscount());

        return dto;
    }
    private Product mapToEntity(ProductRequestDTO dto) {
        Product product = new Product();

        product.setProductName(dto.getProductName());
        product.setImage(dto.getImage());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());
        product.setSpecialPrice(dto.getSpecialPrice());
        product.setDiscount(dto.getDiscount());

        return product;
    }
}
