package com.di.uoa.ted.Bookings.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    @JsonIgnore
    private final Set<User> users = new HashSet<>();

    public Role() {
    }

    public Long getId() {
        return role_id;
    }

    public void setId(Long id) {
        this.role_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() { return users; }
}
