package com.example.demo.services;

import com.example.demo.data.models.News;

import java.util.List;
import java.util.Optional;

public interface INewsService {

    public News create(News news);

    public List<News> create(List<News> articles );

    public Optional<News> get(long Id);

    public News update(News news);

    public void delete( long Id);
}
