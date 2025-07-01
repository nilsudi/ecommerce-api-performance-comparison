package com.example.ecommerceapi.controller.graphql.mongo;

import com.example.ecommerceapi.model.mongo.UserDocument;
import com.example.ecommerceapi.service.mongo.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MongoUserGraphQLController {

    private final MongoUserService userService;

    @Autowired
    public MongoUserGraphQLController(MongoUserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    public List<UserDocument> getAllUserDocuments() {
        return userService.getAllUsers();
    }

    @QueryMapping
    public UserDocument getUserDocumentById(@Argument String id) {
        return userService.getUserById(id).orElse(null);
    }

    @QueryMapping
    public List<String> getAllUserDocumentIds() {
        return userService.getAllUserDocumentIds();
    }

    @MutationMapping
    public UserDocument createUserDocument(@Argument String email,
                                           @Argument String name) {
        return userService.createUser(email, name);
    }

    @MutationMapping
    public UserDocument updateUserDocument(@Argument String id,
                                           @Argument String email,
                                           @Argument String name) {

        return userService.updateUser(id, email, name)
                .orElse(null);
    }

    @MutationMapping
    public boolean deleteUserDocument(@Argument String id) {
        return userService.deleteUser(id);
    }
}
