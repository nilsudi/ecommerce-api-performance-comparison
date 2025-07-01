package com.example.ecommerceapi.controller.rest.mongo;

import com.example.ecommerceapi.model.mongo.ProductDocument;
import com.example.ecommerceapi.service.mongo.MongoProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mongo/products")
public class MongoProductRestController {

    private final MongoProductService productService;

    @Autowired
    public MongoProductRestController(MongoProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDocument> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDocument> getProductById(@PathVariable String id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductDocument> createProduct(
            @RequestBody ProductDocument body) {

        ProductDocument created =
                productService.createProduct(body.getName(), body.getPrice());

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDocument> updateProduct(
            @PathVariable String id,
            @RequestBody ProductDocument body) {

        return productService
                .updateProduct(id, body.getName(), body.getPrice())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
