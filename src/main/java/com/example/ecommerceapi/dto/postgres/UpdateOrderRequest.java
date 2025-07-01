package com.example.ecommerceapi.dto.postgres;

import java.util.List;

public class UpdateOrderRequest {
    private Long userId;

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private List<Long> productIds;

}
