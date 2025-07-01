package com.example.ecommerceapi.model.mongo;

import com.example.ecommerceapi.model.postgres.OrderEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "products")
public class ProductDocument {
    @Id
    private String id;
    private float price;
    private String name;

    public ProductDocument() {
    }

    public ProductDocument(String id, float price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
}
