package com.example.ecommerceapi.service.postgres;

import com.example.ecommerceapi.model.postgres.UserEntity;
import com.example.ecommerceapi.repository.postgres.PostgresUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostgresUserService {

    private final PostgresUserRepository userRepository;

    @Autowired
    public PostgresUserService(PostgresUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserEntity createUser(String name, String email) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setEmail(email);
        return userRepository.save(user);
    }

    public UserEntity updateUser(Long id, String name, String email) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));

        user.setName(name);
        user.setEmail(email);

        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public long count() {
        return userRepository.count();
    }

    public List<Long> getAllUserIds() {
        return userRepository.findAllIds();
    }
}
