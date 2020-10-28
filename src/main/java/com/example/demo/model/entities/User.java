package com.example.demo.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public User(String name) {
        this.username = name;
    }

    private String firstName;
    private String secondName;
    private String avatar;
    private Date birthday;

    private String username;
    private String password;
    private String email;

    private boolean verified;
    private String activationCode;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;
}
