package com.example.demo.web;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public boolean login(@RequestBody User user) {
        return userService.isUserExists(user);
    }

    @PostMapping("/registration")
    public boolean register(@RequestBody User user) {
        return userService.register(user);
    }
}
