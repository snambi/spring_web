package com.example.demo.rest;

import com.example.demo.data.models.News;
import com.example.demo.data.services.INewsSvc;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/v1/news")
public class NewsController {

    @Inject
    INewsSvc iNewsSvc;

    @GetMapping("/{id}")
    public ResponseEntity<News> get(@PathVariable Long id ){

        Optional<News> news = iNewsSvc.findById(id);

        if (news == null || !news.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(news.get());
        }
    }

    @PostMapping("/")
    public ResponseEntity<News> create( @RequestBody News news )throws URISyntaxException {

        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        news.setCreatedTime(timestamp);


        News saved = iNewsSvc.saveAndFlush(news);
        if (saved == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getNewsId())
                    .toUri();

            return ResponseEntity.created(uri)
                                    .body(saved);
        }
    }

    @PutMapping("/id")
    public ResponseEntity<News> update( @RequestBody News news , @PathVariable Long id ){

        News updatedNews = iNewsSvc.saveAndFlush(news);

        if (updatedNews == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedNews);
        }
    }

    @DeleteMapping("/id")
    public ResponseEntity<News> delete( @PathVariable Long id ){

        News n = new News();
        n.setNewsId(id);
        iNewsSvc.delete( n );

        return ResponseEntity.noContent().build();
    }
}
