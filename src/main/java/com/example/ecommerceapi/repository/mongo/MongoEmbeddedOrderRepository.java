package com.example.ecommerceapi.repository.mongo;

import com.example.ecommerceapi.model.mongo.EmbeddedOrderDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface MongoEmbeddedOrderRepository extends MongoRepository<EmbeddedOrderDocument, String> {

    @Query(value = "{}", fields = "{ _id : 1 }")
    List<IdOnly> findAllIds();

    Page<EmbeddedOrderDocument> findAll(Pageable pageable);

}
