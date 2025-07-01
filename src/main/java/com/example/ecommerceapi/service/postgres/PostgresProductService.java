package com.example.ecommerceapi.service.postgres;

import com.example.ecommerceapi.model.postgres.ProductEntity;
import com.example.ecommerceapi.repository.postgres.PostgresProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostgresProductService {

    private final PostgresProductRepository productRepository;

    @Autowired
    public PostgresProductService(PostgresProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public ProductEntity createProduct(String name, Float price) {
        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setPrice(price);
        return productRepository.save(product);
    }

    public ProductEntity updateProduct(Long id, String name, Float price) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));

        product.setName(name);
        product.setPrice(price);

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public long count() {
        return productRepository.count();
    }

    public List<Long> getAllProductIds() {
        return productRepository
                .findAll()
                .stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());
    }
}
