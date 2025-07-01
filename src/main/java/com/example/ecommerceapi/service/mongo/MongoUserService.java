package com.example.ecommerceapi.service.mongo;

import com.example.ecommerceapi.model.mongo.UserDocument;
import com.example.ecommerceapi.repository.mongo.IdOnly;
import com.example.ecommerceapi.repository.mongo.MongoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MongoUserService {

    private final MongoUserRepository userRepository;

    @Autowired
    public MongoUserService(MongoUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDocument> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserDocument> getUserById(String id) {
        return userRepository.findById(id);
    }

    public UserDocument createUser(String email, String name) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-posta boş olamaz.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("İsim boş olamaz.");
        }

        userRepository.findByEmail(email.trim().toLowerCase())
                .ifPresent(u -> { throw new IllegalStateException("Bu e-posta zaten kayıtlı."); });

        UserDocument user = new UserDocument();
        user.setEmail(email.trim().toLowerCase());
        user.setName(name.trim());

        return userRepository.save(user);
    }

    public Optional<UserDocument> updateUser(String id, String email, String name) {

        if (email == null || email.isBlank())
            throw new IllegalArgumentException("E-posta boş olamaz.");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("İsim boş olamaz.");

        String normalizedEmail = email.trim().toLowerCase();

        return userRepository.findById(id).map(existing -> {

            userRepository.findByEmail(normalizedEmail)
                    .filter(u -> !u.getId().equals(id))
                    .ifPresent(u -> { throw new IllegalStateException("Bu e-posta başka bir kullanıcıda kayıtlı."); });

            existing.setEmail(normalizedEmail);
            existing.setName(name.trim());

            return userRepository.save(existing);
        });
    }

    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<String> getAllUserDocumentIds() {
        return userRepository.findAllIds()
                .stream()
                .map(IdOnly::getId)
                .toList();
    }
}
