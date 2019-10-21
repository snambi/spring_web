package com.example.demo.db.services;

import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserSvc extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long aLong);

    List<User> findByStatus(String Status);

    //@Query("SELECT u from User u where u.email = ?1")
    List<User> findByEmail( String email );
}



