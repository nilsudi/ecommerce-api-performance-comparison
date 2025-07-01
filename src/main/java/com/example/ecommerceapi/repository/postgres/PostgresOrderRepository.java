package com.example.ecommerceapi.repository.postgres;

import com.example.ecommerceapi.model.mongo.EmbeddedOrderDocument;
import com.example.ecommerceapi.model.postgres.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostgresOrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("select o.id from OrderEntity o")
    List<Long> findAllIds();

    Page<OrderEntity> findAll(Pageable pageable);
}
