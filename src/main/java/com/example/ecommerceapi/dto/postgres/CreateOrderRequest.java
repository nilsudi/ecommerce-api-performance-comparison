package com.example.ecommerceapi.dto.postgres;

import java.util.List;

public class CreateOrderRequest {
    private Long userId;
    private List<Long> productIds;

    public CreateOrderRequest() {}

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public List<Long> getProductIds() {
        return productIds;
    }
    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}

