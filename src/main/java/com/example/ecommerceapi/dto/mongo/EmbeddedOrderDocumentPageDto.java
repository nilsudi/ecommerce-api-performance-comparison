package com.example.ecommerceapi.dto.mongo;

import com.example.ecommerceapi.model.mongo.EmbeddedOrderDocument;
import java.util.List;

public record EmbeddedOrderDocumentPageDto(
        List<EmbeddedOrderDocument> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last) {
    public static EmbeddedOrderDocumentPageDto from(org.springframework.data.domain.Page<EmbeddedOrderDocument> p) {
        return new EmbeddedOrderDocumentPageDto(
                p.getContent(),
                p.getNumber(),
                p.getSize(),
                p.getTotalElements(),
                p.getTotalPages(),
                p.isLast());
    }
}

