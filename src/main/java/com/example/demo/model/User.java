package com.example.demo.model;

import com.example.demo.utils.XmlDateTimeAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
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
    @XmlAttribute
    private long id;

    @XmlElement(name = "username")
    private String username;
    @XmlElement(name = "password")
    private String password;
    @XmlElement(name="email")
    private String email;
    @XmlElement(name="avatar")
    private String avatar;

    @Temporal(value = TemporalType.DATE)
    @XmlElement(name="registration")
    @XmlJavaTypeAdapter(XmlDateTimeAdapter.class)
    private Date registration;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(value = EnumType.STRING)
    @XmlElement(name = "roles")
    private Set<Role> roles;

    @Override
    public String toString() {
        return "User {" +
                " username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                " }";
    }
}
