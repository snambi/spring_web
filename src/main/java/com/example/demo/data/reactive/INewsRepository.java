package com.example.demo.data.reactive;

import com.example.demo.data.models.News;
import reactor.core.publisher.Flux;

import java.util.List;

public interface INewsRepository {

    Flux<News> findAll();

    List<News> findAllNews();

}
