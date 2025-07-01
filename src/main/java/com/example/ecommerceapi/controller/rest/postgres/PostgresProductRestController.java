package com.example.ecommerceapi.controller.rest.postgres;

import com.example.ecommerceapi.model.postgres.ProductEntity;
import com.example.ecommerceapi.service.postgres.PostgresProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postgres/products")
public class PostgresProductRestController {

    private final PostgresProductService productService;

    @Autowired
    public PostgresProductRestController(PostgresProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductEntity> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) {
        ProductEntity created = productService.createProduct(product.getName(), product.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id, @RequestBody ProductEntity updatedProduct) {
        ProductEntity updated = productService.updateProduct(id, updatedProduct.getName(), updatedProduct.getPrice());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

