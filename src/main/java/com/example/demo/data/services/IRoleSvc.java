package com.example.demo.data.services;

import com.example.demo.data.models.Role;
import com.example.demo.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoleSvc extends JpaRepository<Role, Long> {

    Role findByUserName(String username);

    List<Role> findByRole( String role);
}
