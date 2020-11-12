package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    boolean existsByUsernameAndPassword(String username, String password);
    boolean existsByUsernameOrEmail(String username, String email);
}
