package com.example.demo.data.services;

import com.example.demo.data.models.News;
import com.example.demo.data.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INewsSvc extends JpaRepository<News, Long> {
}
