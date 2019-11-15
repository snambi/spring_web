package com.example.demo;

import com.apptastic.rssreader.Item;
import com.apptastic.rssreader.RssReader;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class RSSReaderTest {

    @Test
    public void readTest(){

        RssReader reader = new RssReader();

        String URL = "https://rss.nytimes.com/services/xml/rss/nyt/Technology.xml";

        Stream<Item> rssFeed = null;

        try {
            rssFeed = reader.read(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Item> articles = rssFeed.collect(Collectors.toList());

        articles.forEach(i -> System.out.println(i.getTitle()));
    }
}
