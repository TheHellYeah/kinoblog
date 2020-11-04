package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Value("${user.default-avatar}")
    private String defaultAvatar;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean register(User user) {
        if(canRegister(user)) {
            user.setRoles(Collections.singleton(Role.USER));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if(user.getAvatar() == null) user.setAvatar(defaultAvatar);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserExists(User user) {
        return userRepository.existsByUsernameAndPassword(user.getUsername(), passwordEncoder.encode(user.getPassword()));
    }

    private boolean canRegister(User user) {
        return !userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with name " + username + " not found");
        }
        return (UserDetails) user;
    }
}
