package com.example.demo.data.models;

import com.apptastic.rssreader.Item;
import com.example.demo.utils.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "NEWS")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "NEWS_ID")
    private long newsId;

    // fields related to the news item
    @Column(name = "HEADLINE")
    private String headline;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "URL")
    private URL url;

    @Column( name = "AUTHOR")
    private String author;

    @Column( name = "channel")
    private String channel;

    @Column( name = "CATEGORY")
    private String category;

    @Column( name = "SUB_CATEGORY")
    private String subCategory;

    @Column(name = "PUBLISHED_DATE")
    private Timestamp publishedDate;

    @Column(name = "PERMA_LINK")
    private boolean isPermalink;

    // metadata about the news item
    @Column(name = "VOTES")
    private Integer votes;

    @Column(name = "VIEWS")
    private Integer views;

    @Column(name = "TAGS")
    private String tags;

    @Column(name = "CREATED_TIME")
    private Timestamp createdTime;

    @Column(name = "UPDATED_TIME")
    private Timestamp updatedTime;

    // internal metadata about the news item
    @Column(name = "APPROVED")
    private boolean approved;


    public News(String headline, URL url, String tags) {
        this.headline = headline;
        this.url = url;
        this.tags = tags;
    }

    public static News from(Item item, String category, String channel ) {
        News news = new News();

        item.getLink().ifPresent( link -> {
            try {
                news.setUrl( new URL(link) );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });

        // TODO: Category is set directly, rather than getting it from NYT feed.
        news.setCategory(category);
        news.setChannel(channel);

        item.getTitle().ifPresent( tite -> news.setHeadline(tite));
        item.getDescription().ifPresent( desc -> news.setDescription(desc ));
        item.getAuthor().ifPresent( author -> news.setAuthor(author));
        item.getCategory().ifPresent(cat -> news.setSubCategory(cat));
        item.getPubDate().ifPresent( pubDate -> news.setPublishedDate(TimeUtils.convert(pubDate)));
        item.getIsPermaLink().ifPresent( permaLink -> news.setPermalink(permaLink));


        //item.getChannel()

        return news;
    }

    public static List<News> from( List<Item> items, String category, String channel ){

        List<News> newsList = new ArrayList<>();

        if( items != null ){
            for( Item i : items){
                News n = News.from(i, category, channel);
                newsList.add(n);
            }
        }

        return newsList;
    }
}
