package com.example.demo.data.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class Operation implements GrantedAuthority {

    @Id
    private String id;

    @Override
    public String getAuthority() {
        return id;
    }
}