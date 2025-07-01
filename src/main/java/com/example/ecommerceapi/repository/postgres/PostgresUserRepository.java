package com.example.ecommerceapi.repository.postgres;

import com.example.ecommerceapi.model.postgres.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostgresUserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u.id from UserEntity u")
    List<Long> findAllIds();
}
