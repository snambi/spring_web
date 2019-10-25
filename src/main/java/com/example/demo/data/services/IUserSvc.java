package com.example.demo.data.services;

import com.example.demo.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserSvc extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long aLong);

    User findByUserName(String username);

    List<User> findByStatus(String Status);

    //@Query("SELECT u from User u where u.email = ?1")
    List<User> findByEmail( String email );


}



