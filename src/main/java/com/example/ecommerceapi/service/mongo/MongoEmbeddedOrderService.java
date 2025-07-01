package com.example.ecommerceapi.service.mongo;

import com.example.ecommerceapi.model.mongo.EmbeddedOrderDocument;
import com.example.ecommerceapi.model.mongo.ProductDocument;
import com.example.ecommerceapi.model.mongo.UserDocument;
import com.example.ecommerceapi.repository.mongo.MongoEmbeddedOrderRepository;
import com.example.ecommerceapi.repository.mongo.MongoProductRepository;
import com.example.ecommerceapi.repository.mongo.MongoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.ecommerceapi.repository.mongo.IdOnly;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MongoEmbeddedOrderService {

    private final MongoEmbeddedOrderRepository embeddedOrderRepository;
    private final MongoUserRepository userRepository;
    private final MongoProductRepository productRepository;

    @Autowired
    public MongoEmbeddedOrderService(
            MongoEmbeddedOrderRepository embeddedOrderRepository,
            MongoUserRepository userRepository,
            MongoProductRepository productRepository
    ) {
        this.embeddedOrderRepository   = embeddedOrderRepository;
        this.userRepository    = userRepository;
        this.productRepository = productRepository;
    }

    public List<EmbeddedOrderDocument> getAllEmbeddedOrders() {
        return embeddedOrderRepository.findAll();
    }

    public Optional<EmbeddedOrderDocument> getEmbeddedOrderById(String id) {
        return embeddedOrderRepository.findById(id);
    }

    public EmbeddedOrderDocument createEmbeddedOrder(String userId, List<String> productIds) {
        UserDocument user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User bulunamadı: " + userId));

        List<ProductDocument> products = productRepository.findAllById(productIds);
        if (products.isEmpty()) {
            throw new RuntimeException("Hiç ürün bulunamadı: " + productIds);
        }

        float totalPrice = (float) products.stream()
                .mapToDouble(ProductDocument::getPrice)
                .sum();

        OffsetDateTime now = OffsetDateTime.now();

        EmbeddedOrderDocument order = new EmbeddedOrderDocument();
        order.setUser(user);
        order.setProducts(products);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(now);

        return embeddedOrderRepository.save(order);
    }

    public EmbeddedOrderDocument updateEmbeddedOrderDocument(String id, List<String> productIds) {
        EmbeddedOrderDocument existing = embeddedOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        List<ProductDocument> products = productRepository.findAllById(productIds);
        existing.setProducts(products);

        float totalPrice = (float) products.stream()
                .mapToDouble(ProductDocument::getPrice)
                .sum();
        existing.setTotalPrice(totalPrice);

        existing.setOrderDate(OffsetDateTime.now());

        return embeddedOrderRepository.save(existing);
    }

    public boolean deleteEmbeddedOrderDocument(String id) {
        if (embeddedOrderRepository.existsById(id)) {
            embeddedOrderRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public long count() {
        return embeddedOrderRepository.count();
    }

    public List<EmbeddedOrderDocument> saveAllEmbeddedOrders(List<EmbeddedOrderDocument> orders) {
        return embeddedOrderRepository.saveAll(orders);
    }

    public List<String> getAllEmbeddedOrderDocumentIds() {
        return embeddedOrderRepository.findAllIds()
                .stream()
                .map(IdOnly::getId)
                .toList();
    }

    public Page<EmbeddedOrderDocument> getPaginatedEmbeddedOrders(Pageable pageable){
        return embeddedOrderRepository.findAll(pageable);
    }

}
