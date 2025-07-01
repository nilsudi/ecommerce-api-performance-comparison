package com.example.ecommerceapi.controller.graphql.postgres;

import com.example.ecommerceapi.model.postgres.UserEntity;
import com.example.ecommerceapi.service.postgres.PostgresUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class PostgresUserGraphQLController {
    private final PostgresUserService userService;

    @Autowired
    public PostgresUserGraphQLController(PostgresUserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    public List<UserEntity> getAllUserEntities() {
        return userService.getAllUsers();
    }

    @QueryMapping
    public UserEntity getUserEntityById(@Argument Long id) {
        Optional<UserEntity> userOpt = userService.getUserById(id);
        return userOpt.orElse(null);
    }

    @QueryMapping
    public List<Long> getAllUserEntityIds() {
        return userService.getAllUserIds();
    }

    @MutationMapping
    public UserEntity createUserEntity(@Argument String name, @Argument String email) {
        return userService.createUser(name, email);
    }

    @MutationMapping
    public UserEntity updateUserEntity(@Argument Long id, @Argument String name, @Argument String email) {
        return userService.updateUser(id, name, email);
    }

    @MutationMapping
    public boolean deleteUserEntity(@Argument Long id) {
        return userService.deleteUser(id);
    }

}
