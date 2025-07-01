package com.example.ecommerceapi.controller.rest.mongo;

import com.example.ecommerceapi.model.mongo.UserDocument;
import com.example.ecommerceapi.service.mongo.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mongo/users")
public class MongoUserRestController {

    private final MongoUserService userService;

    @Autowired
    public MongoUserRestController(MongoUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDocument> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDocument> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ids")
    public List<String> getUserIds() {
        return userService.getAllUserDocumentIds();
    }

    @PostMapping
    public ResponseEntity<UserDocument> createUser(@RequestBody UserDocument body) {
        UserDocument created =
                userService.createUser(body.getEmail(), body.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDocument> updateUser(@PathVariable String id,
                                                   @RequestBody UserDocument body) {
        return userService.updateUser(id, body.getEmail(), body.getName())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
