package com.example.ecommerceapi.service.mongo;

import com.example.ecommerceapi.model.mongo.ProductDocument;
import com.example.ecommerceapi.repository.mongo.MongoProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MongoProductService {

    private final MongoProductRepository productRepository;

    @Autowired
    public MongoProductService(MongoProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDocument> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductDocument> getProductById(String id) {
        return productRepository.findById(id);
    }

    public List<ProductDocument> getProductsByIds(List<String> ids) {
        return productRepository.findAllById(ids);
    }

    public List<String> getAllProductIds() {
        return productRepository
                .findAll()
                .stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());
    }

    public ProductDocument createProduct(String name, float price) {

        if (price <= 0) {
            throw new IllegalArgumentException("Ürün fiyatı 0'dan büyük olmalıdır.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Ürün adı boş olamaz.");
        }

        ProductDocument product = new ProductDocument();
        product.setName(name.trim());
        product.setPrice(price);

        return productRepository.save(product);

    }

    public Optional<ProductDocument> updateProduct(String id,
                                                   String name,
                                                   float price) {

        if (price <= 0) {
            throw new IllegalArgumentException("Ürün fiyatı 0'dan büyük olmalıdır.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Ürün adı boş olamaz.");
        }

        return productRepository.findById(id)
                .map(existing -> {
                    existing.setName(name.trim());
                    existing.setPrice(price);
                    return productRepository.save(existing);
                });
    }

    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

