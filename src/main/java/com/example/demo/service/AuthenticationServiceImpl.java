package com.example.demo.service;

import com.example.demo.security.AuthenticationResponse;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Date;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Value("${user.default-avatar}")
    private String defaultAvatar;

    @Value("$user.pass")
    private String pass;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setUsername("Artem222");
        user.setEmail("123");
        user.setPassword(passwordEncoder.encode(pass));
        user.setRoles(Collections.singleton(Role.ADMIN));
        userRepository.save(user);
    }

    @Override
    public AuthenticationResponse login(User logged) {
        User user = userRepository.findByUsername(logged.getUsername());
        String token = tokenProvider.generateToken(user.getUsername());
        log.info("{} logged in successfully", user);
        return new AuthenticationResponse(user, token);
    }

    @Override
    public AuthenticationResponse register(User user) {
        user.setAvatar(defaultAvatar);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        user.setRegistration(new Date());
        userRepository.save(user);
        String token = tokenProvider.generateToken(user.getUsername());
        log.info("{} registered successfully", user);
        return new AuthenticationResponse(user, token);
    }

    @Override
    public boolean canLogin(User logged) {
        User user = userRepository.findByUsername(logged.getUsername());
        return user != null && isPasswordMatches(logged.getPassword(), user.getPassword());
    }

    private boolean isPasswordMatches(String unencodedPassword, String encodedPassword) {
        return passwordEncoder.matches(unencodedPassword, encodedPassword);
    }

    public boolean canRegister(User user) {
        boolean isUserAlreadyExists = userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail());
        return !isUserAlreadyExists;
    }
}
