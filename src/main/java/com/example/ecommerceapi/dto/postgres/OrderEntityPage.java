package com.example.ecommerceapi.dto.postgres;

import com.example.ecommerceapi.model.postgres.OrderEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public record OrderEntityPage(
        List<OrderEntity> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last) {

    public static OrderEntityPage from(Page<OrderEntity> p) {
        return new OrderEntityPage(
                p.getContent(),
                p.getNumber(),
                p.getSize(),
                p.getTotalElements(),
                p.getTotalPages(),
                p.isLast()
        );
    }
}

