package com.ecommerce.project.service;

import com.ecommerce.project.Exceptions.ResourceNotFoundException;
import com.ecommerce.project.Repository.CategoryRepository;
import com.ecommerce.project.Repository.ProductRepository;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductRequestDTO;
import com.ecommerce.project.payload.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
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
    public ProductResponseDTO getAllProductByCategoryId(Long categoryId) {
        List<Product> products =
                productRepository
                        .findByCategoryCategoryId(categoryId);

        List<ProductRequestDTO> productDTOs =
                products.stream()
                        .map(this::mapToDTO)
                        .toList();

        return new ProductResponseDTO(productDTOs);
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

        return dto;
    }
    private Product mapToEntity(ProductRequestDTO dto) {
        Product product = new Product();

        product.setProductName(dto.getProductName());
        product.setImage(dto.getImage());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());
        product.setSpecialPrice(dto.getSpecialPrice());

        return product;
    }
}
