package com.example.ecommerceapi.service.postgres;

import com.example.ecommerceapi.model.mongo.EmbeddedOrderDocument;
import com.example.ecommerceapi.model.postgres.OrderEntity;
import com.example.ecommerceapi.model.postgres.ProductEntity;
import com.example.ecommerceapi.model.postgres.UserEntity;
import com.example.ecommerceapi.repository.postgres.PostgresOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostgresOrderService {

    private final PostgresOrderRepository orderRepository;
    private final PostgresProductService productService;
    private final PostgresUserService userService;

    @Autowired
    public PostgresOrderService(PostgresOrderRepository orderRepository, PostgresProductService productService, PostgresUserService userService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<OrderEntity> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public OrderEntity createOrder(Long userId, List<Long> productIds) {
        UserEntity user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        List<ProductEntity> products = fetchValidProducts(productIds);
        float totalPrice = calculateTotalPrice(products);

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setProducts(products);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(OffsetDateTime.now());

        return orderRepository.save(order);
    }

    public OrderEntity updateOrder(Long orderId, Long userId, List<Long> productIds) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + orderId));

        UserEntity user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        List<ProductEntity> products = fetchValidProducts(productIds);
        float totalPrice = calculateTotalPrice(products);

        order.setUser(user);
        return orderRepository.save(order);
    }

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public long count() {
        return orderRepository.count();
    }

    public List<Long> getAllOrderIds() {
        return orderRepository.findAllIds();
    }

    public Page<OrderEntity> getPaginatedOrders(Pageable pageable){
        return orderRepository.findAll(pageable);
    }

    private List<ProductEntity> fetchValidProducts(List<Long> productIds) {
        return productIds.stream()
                .map(pid -> productService.getProductById(pid)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Product not found with id " + pid)))
                .collect(Collectors.toList());
    }

    private float calculateTotalPrice(List<ProductEntity> products) {
        return (float) products.stream()
                .mapToDouble(ProductEntity::getPrice)
                .sum();
    }

}
