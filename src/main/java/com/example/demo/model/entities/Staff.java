package com.example.demo.model.entities;

import javax.persistence.Entity;
import java.util.List;

public class Staff extends Person {

    private List<Career> careers;
}
