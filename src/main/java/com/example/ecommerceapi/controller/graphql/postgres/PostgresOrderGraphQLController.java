package com.example.ecommerceapi.controller.graphql.postgres;

import com.example.ecommerceapi.model.postgres.OrderEntity;
import com.example.ecommerceapi.dto.postgres.OrderEntityPage;
import com.example.ecommerceapi.model.postgres.ProductEntity;
import com.example.ecommerceapi.model.postgres.UserEntity;
import com.example.ecommerceapi.service.postgres.PostgresOrderService;
import com.example.ecommerceapi.service.postgres.PostgresProductService;
import com.example.ecommerceapi.service.postgres.PostgresUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PostgresOrderGraphQLController {

    private final PostgresOrderService orderService;
    private final PostgresUserService userService;
    private final PostgresProductService productService;

    @Autowired
    public PostgresOrderGraphQLController(PostgresOrderService orderService,
                                          PostgresUserService userService,
                                          PostgresProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }

    @QueryMapping
    public List<OrderEntity> getAllOrderEntities() {
        return orderService.getAllOrders();
    }

    @QueryMapping
    public OrderEntity getOrderEntityById(@Argument Long id) {
        return orderService.getOrderById(id).orElse(null);
    }

    @QueryMapping
    public List<Long> getAllOrderEntityIds() {
        return orderService.getAllOrderIds();
    }
    @QueryMapping
    public OrderEntityPage getPaginatedOrderEntities(
            @Argument int page,
            @Argument int size) {

        Pageable pageable = PageRequest.of(page, size);
        var pageResult = orderService.getPaginatedOrders(pageable);
        return OrderEntityPage.from(pageResult);
    }

    @MutationMapping
    public OrderEntity createOrderEntity(@Argument Long userId, @Argument List<Long> productIds) {
        return orderService.createOrder(userId, productIds);
    }

    @MutationMapping
    public OrderEntity updateOrderEntity(@Argument Long id, @Argument Long userId, @Argument List<Long> productIds) {
        return orderService.updateOrder(id, userId, productIds);
    }

    @MutationMapping
    public Boolean deleteOrderEntity(@Argument Long id) {
        return orderService.deleteOrder(id);
    }
}
