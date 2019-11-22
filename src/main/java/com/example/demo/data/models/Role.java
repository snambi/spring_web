package com.example.demo.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity( name = "ROLE")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "ID")
    private long id;

    @Column(name= "USER_NAME")
    private String userName;

    @Column( name = "ROLE")
    private String role;

    @Column( name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_TIME")
    private Timestamp createdTime;

    @Column(name = "UPDATED_TIME")
    private Timestamp updatedTime;

    @Override
    public String getAuthority() {
        return role;
    }

}