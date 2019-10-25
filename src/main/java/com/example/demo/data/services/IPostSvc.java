package com.example.demo.data.services;

import com.example.demo.data.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostSvc extends JpaRepository<Post, Long> {
}
