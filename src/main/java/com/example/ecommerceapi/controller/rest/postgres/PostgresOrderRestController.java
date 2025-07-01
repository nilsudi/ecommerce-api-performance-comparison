package com.example.ecommerceapi.controller.rest.postgres;

import com.example.ecommerceapi.dto.postgres.CreateOrderRequest;
import com.example.ecommerceapi.dto.postgres.UpdateOrderRequest;
import com.example.ecommerceapi.model.postgres.OrderEntity;
import com.example.ecommerceapi.model.postgres.UserEntity;
import com.example.ecommerceapi.service.postgres.PostgresOrderService;
import com.example.ecommerceapi.service.postgres.PostgresUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/postgres/orders")
public class PostgresOrderRestController {

    private final PostgresOrderService orderService;
    private final PostgresUserService userService;

    @Autowired
    public PostgresOrderRestController(PostgresOrderService orderService, PostgresUserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ids")
    public List<Long> getUserIds() {
        return orderService.getAllOrderIds();
    }

    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(@RequestBody CreateOrderRequest dto) {
        OrderEntity created = orderService.createOrder(dto.getUserId(), dto.getProductIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Long id, @RequestBody UpdateOrderRequest dto) {
        OrderEntity updated = orderService.updateOrder(id, dto.getUserId(), dto.getProductIds());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean deleted = orderService.deleteOrder(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/getPaginatedOrders")
    public ResponseEntity<Page<OrderEntity>> getPaginatedOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){

        Pageable pageable = PageRequest.of(
                page,
                size
        );

        Page<OrderEntity> orders = orderService.getPaginatedOrders(pageable);
        return ResponseEntity.ok(orders);
    }
}
