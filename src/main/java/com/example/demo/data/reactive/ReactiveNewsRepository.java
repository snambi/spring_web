package com.example.demo.data.reactive;

import com.example.demo.data.models.News;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ReactiveNewsRepository implements INewsRepository{

    private static List<News> news = new ArrayList<>();

    static{
        try {

            news.add( new News("Great election", new URL("http://www.google.com"), Arrays.asList("Politics")));
            news.add( new News("Big Storm", new URL("http://www.google.com"), Arrays.asList("Weather")));
            news.add( new News("Large fire", new URL("http://www.google.com"), Arrays.asList("Weather")));
            news.add( new News("Stock crash", new URL("http://www.google.com"), Arrays.asList("Finance")));
            news.add( new News("Holiday Ideas", new URL("http://www.google.com"), Arrays.asList("Events")));
            news.add( new News("Diwali Sales", new URL("http://www.google.com"), Arrays.asList("Commerce")));
            news.add( new News("Movies week", new URL("http://www.google.com"), Arrays.asList("Movies")));
            news.add( new News("President trump", new URL("http://www.google.com"), Arrays.asList("Politics")));
            news.add( new News("PG&E", new URL("http://www.google.com"), Arrays.asList("Finance")));


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Flux<News> findAll() {
        return Flux.fromIterable(news).delayElements(Duration.ofSeconds(2));
    }

    @Override
    public List<News> findAllNews() {
        return news;
    }
}
