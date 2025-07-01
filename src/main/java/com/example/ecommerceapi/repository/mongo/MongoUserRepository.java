package com.example.ecommerceapi.repository.mongo;

import com.example.ecommerceapi.model.mongo.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MongoUserRepository extends MongoRepository<UserDocument, String> {
    @Query(value = "{}", fields = "{ _id : 1 }")
    List<IdOnly> findAllIds();

    Optional<UserDocument> findByEmail(String email);
}



