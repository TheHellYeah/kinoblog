package com.example.demo.service;

import com.example.demo.security.AuthenticationResponse;
import com.example.demo.model.User;

public interface AuthenticationService {

    AuthenticationResponse login(User user);
    AuthenticationResponse register(User user);
    boolean canRegister(User user);
    boolean canLogin(User user);
}
