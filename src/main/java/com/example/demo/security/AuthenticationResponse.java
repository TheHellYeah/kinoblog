package com.example.demo.security;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import lombok.Getter;

import java.util.Date;
import java.util.Set;

@Getter
public class AuthenticationResponse {

    private String token;
    private String username;
    private String email;
    private String avatar;
    private Date registration;
    private Set<Role> roles;

    public AuthenticationResponse(User user, String token) {
        this.token = token;
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.registration = user.getRegistration();
        this.roles = user.getRoles();
    }
}
