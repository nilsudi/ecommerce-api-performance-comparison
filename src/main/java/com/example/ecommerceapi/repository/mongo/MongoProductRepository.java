package com.example.ecommerceapi.repository.mongo;

import com.example.ecommerceapi.model.mongo.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoProductRepository extends MongoRepository<ProductDocument, String> {
}
