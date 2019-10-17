package com.example.demo.db.services;

import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserSvc extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long aLong);

    List<User> findByStatus(String Status);

}



