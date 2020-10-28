package com.example.demo.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String secondName;
    private String avatar;

    @Temporal(value = TemporalType.DATE)
    private Date birthday;

    private String username;
    private String password;
    private String email;

    private boolean verified;
    private String activationCode;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(value = EnumType.STRING)
    private Set<UserRole> role;
}
