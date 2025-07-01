package com.example.ecommerceapi.controller.graphql.mongo;

import com.example.ecommerceapi.model.mongo.ProductDocument;
import com.example.ecommerceapi.service.mongo.MongoProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MongoProductGraphQLController {

    private final MongoProductService productService;

    @Autowired
    public MongoProductGraphQLController(MongoProductService productService) {
        this.productService = productService;
    }

    @QueryMapping
    public List<ProductDocument> getAllProductDocuments() {
        return productService.getAllProducts();
    }

    @QueryMapping
    public ProductDocument getProductDocumentById(@Argument String id) {
        return productService.getProductById(id).orElse(null);
    }

    @QueryMapping
    public List<String> getAllProductDocumentIds() {
        return productService.getAllProductIds();
    }

    @MutationMapping
    public ProductDocument createProductDocument(@Argument String name,
                                                 @Argument float price) {
        return productService.createProduct(name, price);
    }

    @MutationMapping
    public ProductDocument updateProductDocument(@Argument String id,
                                                 @Argument String name,
                                                 @Argument float price) {

        return productService.updateProduct(id, name, price)
                .orElse(null);
    }

    @MutationMapping
    public boolean deleteProductDocument(@Argument String id) {
        return productService.deleteProduct(id);
    }
}
