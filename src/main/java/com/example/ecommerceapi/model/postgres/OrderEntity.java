package com.example.ecommerceapi.model.postgres;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> products;

    @Column(name = "total_price")
    private float totalPrice;

    @Column(name = "order_date")
    private OffsetDateTime orderDate;

    @PrePersist
    protected void prePersist() {
        orderDate = OffsetDateTime.now();
    }

    public OrderEntity() {
    }


    public OrderEntity(Long id, UserEntity user, List<ProductEntity> products, float totalPrice, OffsetDateTime orderDate) {
        this.id = id;
        this.user = user;
        this.products = products;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public OffsetDateTime getOrderDate() {
        return orderDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderDate(OffsetDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
