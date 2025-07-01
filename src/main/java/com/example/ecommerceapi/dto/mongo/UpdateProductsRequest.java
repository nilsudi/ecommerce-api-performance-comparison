package com.example.ecommerceapi.dto.mongo;

import java.util.List;

public class UpdateProductsRequest {
    private List<String> productIds;

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
}
