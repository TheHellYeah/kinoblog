package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAll();
    boolean register(User user);
    boolean isUserExists(User user);
}
