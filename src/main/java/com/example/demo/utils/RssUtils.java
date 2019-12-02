package com.example.demo.utils;

import com.apptastic.rssreader.Item;
import com.apptastic.rssreader.RssReader;
import com.example.demo.data.models.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RssUtils {

    private static Logger log = LoggerFactory.getLogger(RssUtils.class);

    public static List<News> convert(String url , String category, String channel ){

        RssReader reader = new RssReader();
        Stream<Item> rssFeed = null;

        try {
            rssFeed = reader.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Item> articles = rssFeed.collect(Collectors.toList());

        // print the info into console
        //articles.forEach(i -> log.info( i.getTitle() + ", "+ i.getLink()));

        List<News> newsList = News.from(articles, category, channel );

        return newsList;
    }

}
