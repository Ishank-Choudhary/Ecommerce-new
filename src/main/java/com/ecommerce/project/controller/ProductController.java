package com.ecommerce.project.controller;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductRequestDTO;
import com.ecommerce.project.payload.ProductResponseDTO;
import com.ecommerce.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/categories/{categoryId}/products")
    public ResponseEntity<ProductRequestDTO> addProduct(
            @RequestBody ProductRequestDTO productRequestDTO,
            @PathVariable Long categoryId) {

        ProductRequestDTO savedProduct =
                productService.addProduct(productRequestDTO, categoryId);

        return new ResponseEntity<>(
                savedProduct,
                HttpStatus.CREATED);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductRequestDTO> getProductById(
            @PathVariable Long productId) {

        ProductRequestDTO product =
                productService.getProductById(productId);

        return ResponseEntity.ok(product);
    }

    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<ProductResponseDTO> getAllProductsByCategoryId(
            @PathVariable Long categoryId) {

        ProductResponseDTO products =
                productService.getAllProductByCategoryId(categoryId);

        return ResponseEntity.ok(products);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductRequestDTO> updateProductById(
            @PathVariable Long productId,
            @RequestBody ProductRequestDTO productDTO) {

        ProductRequestDTO updatedProduct =
                productService.updateProduct(
                        productId,
                        productDTO);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProductById(
            @PathVariable Long productId) {

        String message =
                productService.deleteProduct(productId);

        return ResponseEntity.ok(message);
    }
}