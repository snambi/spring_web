package com.example.demo.data.services;

import com.example.demo.data.models.News;
import com.example.demo.data.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.net.URL;
import java.util.List;

public interface INewsSvc extends JpaRepository<News, Long> {

    News findByUrl( URL url );

    @Query( value = "Select * from News where URL in ( :urls ) ", nativeQuery = true)
    List<News> findByUrl(@Param("urls") List<URL> urls );
}
