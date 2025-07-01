package com.example.ecommerceapi.model.postgres;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float price;
    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<OrderEntity> orders;

    public ProductEntity() {
    }

    public ProductEntity(Long id, float price, String name, List<OrderEntity> orders) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }
}
