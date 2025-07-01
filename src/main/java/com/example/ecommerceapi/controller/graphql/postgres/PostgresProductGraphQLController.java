package com.example.ecommerceapi.controller.graphql.postgres;

import com.example.ecommerceapi.model.postgres.ProductEntity;
import com.example.ecommerceapi.service.postgres.PostgresProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PostgresProductGraphQLController {

    private final PostgresProductService productService;

    @Autowired
    public PostgresProductGraphQLController(PostgresProductService productService) {
        this.productService = productService;
    }

    @QueryMapping
    public List<ProductEntity> getAllProductEntities() {
        return productService.getAllProducts();
    }

    @QueryMapping
    public ProductEntity getProductEntityById(@Argument Long id) {
        return productService.getProductById(id).orElse(null);
    }

    @QueryMapping
    public List<Long> getAllProductEntityIds() {
        return productService.getAllProductIds();
    }

    @MutationMapping
    public ProductEntity createProductEntity(@Argument String name, @Argument Float price) {
        return productService.createProduct(name, price);
    }

    @MutationMapping
    public ProductEntity updateProductEntity(@Argument Long id, @Argument String name, @Argument Float price) {
        return productService.updateProduct(id, name, price);
    }

    @MutationMapping
    public Boolean deleteProductEntity(@Argument Long id) {
        productService.deleteProduct(id);
        return true;
    }
}
