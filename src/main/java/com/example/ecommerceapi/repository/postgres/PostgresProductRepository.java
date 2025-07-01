package com.example.ecommerceapi.repository.postgres;

import com.example.ecommerceapi.model.postgres.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresProductRepository extends JpaRepository<ProductEntity, Long> {
}
