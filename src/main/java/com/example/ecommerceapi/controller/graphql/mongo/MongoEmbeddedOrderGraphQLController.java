package com.example.ecommerceapi.controller.graphql.mongo;

import com.example.ecommerceapi.model.mongo.EmbeddedOrderDocument;
import com.example.ecommerceapi.dto.mongo.EmbeddedOrderDocumentPageDto;
import com.example.ecommerceapi.service.mongo.MongoEmbeddedOrderService;
import com.example.ecommerceapi.service.mongo.MongoProductService;
import com.example.ecommerceapi.service.mongo.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class MongoEmbeddedOrderGraphQLController {

    private final MongoEmbeddedOrderService embeddedOrderService;
    private final MongoUserService userService;
    private final MongoProductService productService;

    @Autowired
    public MongoEmbeddedOrderGraphQLController(MongoEmbeddedOrderService embeddedOrderService, MongoUserService userService, MongoProductService productServiceService) {
        this.embeddedOrderService = embeddedOrderService;
        this.userService = userService;
        this.productService = productServiceService;
    }

    @QueryMapping
    public List<EmbeddedOrderDocument> getAllEmbeddedOrderDocuments() {
        return embeddedOrderService.getAllEmbeddedOrders();
    }

    @QueryMapping
    public Optional<EmbeddedOrderDocument> getEmbeddedOrderDocumentById(@Argument String id) throws ChangeSetPersister.NotFoundException {
        return Optional.ofNullable(embeddedOrderService.getEmbeddedOrderById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @QueryMapping
    public List<String> getAllEmbeddedOrderDocumentIds() {
        return embeddedOrderService.getAllEmbeddedOrderDocumentIds();
    }

    @QueryMapping
    public EmbeddedOrderDocumentPageDto getPaginatedEmbeddedOrderDocuments(
            @Argument int page,
            @Argument int size) {

        Pageable pageable = PageRequest.of(page, size);
        var pageResult = embeddedOrderService.getPaginatedEmbeddedOrders(pageable);
        return EmbeddedOrderDocumentPageDto.from(pageResult);
    }

    @MutationMapping
    public EmbeddedOrderDocument createEmbeddedOrderDocument(
            @Argument String userId,
            @Argument List<String> productIds
    ) {
        return embeddedOrderService.createEmbeddedOrder(userId, productIds);
    }

    @MutationMapping
    public EmbeddedOrderDocument updateEmbeddedOrderDocument(
            @Argument(name = "id") String id,
            @Argument(name = "productIds") List<String> productIds
    ) {
        return embeddedOrderService.updateEmbeddedOrderDocument(id, productIds);
    }

    @MutationMapping
    public Boolean deleteEmbeddedOrderDocument(@Argument String id) {
        return embeddedOrderService.deleteEmbeddedOrderDocument(id);
    }
}
