package com.example.demo.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
public abstract class Person {

    private String firstName;
    private String secondName;
    private String avatar;
    private Date birthday;
}
