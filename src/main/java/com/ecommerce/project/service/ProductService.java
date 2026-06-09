package com.ecommerce.project.service;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductRequestDTO;
import com.ecommerce.project.payload.ProductResponseDTO;

public interface ProductService {

    ProductRequestDTO addProduct(ProductRequestDTO productRequestDTO, Long categoryId);
    ProductRequestDTO getProductById(Long productId);
    ProductResponseDTO getAllProductByCategoryId(Long categoryId);
    ProductRequestDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO);
    String deleteProduct(Long productId);
}
