package com.example.demo.model;

import com.example.demo.utils.Views;
import com.example.demo.utils.XmlDateTimeAdapter;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Public.class)
    private long id;

    @JsonView(Views.Public.class)
    private String username;

    private String password;

    @JsonView(Views.Public.class)
    private String email;

    @JsonView(Views.Public.class)
    private String avatar;

    @Temporal(value = TemporalType.DATE)
    @JsonView(Views.Public.class)
    private Date registration;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(value = EnumType.STRING)
    @JsonView(Views.Public.class)
    private Set<Role> roles;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonView(Views.UserPage.class)
    private List<Review> reviews;

    @Override
    public String toString() {
        return "User {" +
                " username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                " }";
    }

    public void addReview(Review review) {
        reviews.add(review);
    }
}
