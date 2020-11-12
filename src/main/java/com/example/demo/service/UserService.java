package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();
    Optional<User> getById(Long id);
    void deleteAll();
    void saveAll(Iterable<User> users);
}
