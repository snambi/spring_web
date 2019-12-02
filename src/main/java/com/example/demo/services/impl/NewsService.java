package com.example.demo.services.impl;

import com.example.demo.data.models.News;
import com.example.demo.data.services.INewsSvc;
import com.example.demo.services.INewsService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Component
public class NewsService implements INewsService {

    @Inject
    INewsSvc iNewsSvc;

    @Override
    public News create(News news) {

        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        news.setCreatedTime(timestamp);

        News saved = iNewsSvc.saveAndFlush(news);
        return saved;
    }

    @Override
    public List<News> create(List<News> articles) {
        if( articles != null ){

            // before inserting check whether the articles are already present
            List<URL> urls = new ArrayList<>();
            articles.forEach( x -> urls.add(x.getUrl()) );

            List<News> fetched = iNewsSvc.findByUrl(urls);

            for( News n : articles ){
                boolean found = false;

                for( News f : fetched ){
                    if( f.getUrl().toString().equals( n.getUrl().toString())){
                        found = true;
                    }
                }

                if( found == false ){
                    create(n);
                }
            }
        }

        return articles;
    }

    @Override
    public Optional<News> get(long Id) {

        Optional<News> news = iNewsSvc.findById(Id);
        return news;
    }

    @Override
    public News update(News news) {

        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        news.setUpdatedTime(timestamp);

        News updatedNews = iNewsSvc.saveAndFlush(news);
        return updatedNews;
    }

    @Override
    public void delete(long Id) {

        News n = new News();
        n.setNewsId(Id);
        iNewsSvc.delete( n );
    }
}
