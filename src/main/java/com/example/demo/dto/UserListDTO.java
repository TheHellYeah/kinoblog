package com.example.demo.dto;

import com.example.demo.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
public class UserListDTO {

    @XmlElement(name = "user")
    private List<User> users;

    public UserListDTO(List<User> users) {
        this.users = users;
    }
}
