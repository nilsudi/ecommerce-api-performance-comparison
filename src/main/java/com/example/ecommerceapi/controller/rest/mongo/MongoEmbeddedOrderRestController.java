package com.example.ecommerceapi.controller.rest.mongo;

import com.example.ecommerceapi.dto.mongo.CreateEmbeddedOrderRequest;
import com.example.ecommerceapi.model.mongo.EmbeddedOrderDocument;
import com.example.ecommerceapi.dto.mongo.UpdateProductsRequest;
import com.example.ecommerceapi.service.mongo.MongoEmbeddedOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mongo/embedded-orders")
public class MongoEmbeddedOrderRestController {

    private final MongoEmbeddedOrderService orderService;

    @Autowired
    public MongoEmbeddedOrderRestController(MongoEmbeddedOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<EmbeddedOrderDocument> getAllEmbeddedOrders() {
        return orderService.getAllEmbeddedOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmbeddedOrderDocument> getEmbeddedOrderById(@PathVariable String id) {
        return orderService.getEmbeddedOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ids")
    public List<String> getAllEmbeddedOrderDocumentIds() {
        return orderService.getAllEmbeddedOrderDocumentIds();
    }

    @PostMapping
    public ResponseEntity<EmbeddedOrderDocument> createEmbeddedOrder(
            @RequestBody CreateEmbeddedOrderRequest request) {

        EmbeddedOrderDocument savedOrder =
                orderService.createEmbeddedOrder(request.getUserId(), request.getProductIds());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmbeddedOrderDocument> updateEmbeddedOrderDocument(
            @PathVariable String id,
            @RequestBody UpdateProductsRequest request
    ) {
        try {
            EmbeddedOrderDocument updated = orderService.updateEmbeddedOrderDocument(
                    id,
                    request.getProductIds()
            );
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmbeddedOrderDocument(@PathVariable String id) {
        boolean deleted = orderService.deleteEmbeddedOrderDocument(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/getPaginatedEmbeddedOrders")
    public ResponseEntity<Page<EmbeddedOrderDocument>> getPaginatedEmbeddedOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){

        Pageable pageable = PageRequest.of(
                page,
                size
        );

        Page<EmbeddedOrderDocument> orders = orderService.getPaginatedEmbeddedOrders(pageable);
        return ResponseEntity.ok(orders);
    }


}
