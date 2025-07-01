package com.example.ecommerceapi.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Document(collection = "embedded_orders")
public class EmbeddedOrderDocument {

    @Id
    private String id;
    private UserDocument user;
    private List<ProductDocument> products;
    private float totalPrice;
    private OffsetDateTime orderDate;

    public EmbeddedOrderDocument() {
        this.orderDate = OffsetDateTime.now();
    }

    public EmbeddedOrderDocument(String id, UserDocument user, List<ProductDocument> products, float totalPrice, OffsetDateTime orderDate) {
        this.id = id;
        this.user = user;
        this.products = products;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate != null ? orderDate : OffsetDateTime.now();
    }

    public String getId() {
        return id;
    }

    public UserDocument getUser() {
        return user;
    }

    public List<ProductDocument> getProducts() {
        return products;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public OffsetDateTime getOrderDate() {
        return orderDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(UserDocument user) {
        this.user = user;
    }

    public void setProducts(List<ProductDocument> products) {
        this.products = products;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderDate(OffsetDateTime orderDate) {
        this.orderDate = orderDate;
    }
}

