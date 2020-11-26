package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();
    void appointUser(long userId, Role moderator);
    void dismiss(long userId);
}
