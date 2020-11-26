package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void appointUser(long userId, Role role) {
        User user = userRepository.getOne(userId);
        user.getRoles().add(role);
        userRepository.save(user);
        log.info("User {} appointed to role {}", user.getUsername(), role);
    }

    @Override
    public void dismiss(long userId) {
        User user = userRepository.getOne(userId);
        user.setRoles(new HashSet<>());
        user.getRoles().add(Role.USER);
        userRepository.save(user);
        log.info("User {} dismissed", user.getUsername());
    }
}
