package com.example.demo.db.services;

import com.example.demo.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostSvc extends JpaRepository<Post, Long> {
}
