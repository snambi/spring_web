package com.example.demo.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "NEWS")
public class News {

    @Id
    @GeneratedValue
    private long newsId;

    // fields related to the news item
    @Column(name = "HEADLINE")
    private String headline;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "URL")
    private URL url;

    @Column(name = "PUBLISHED_DATE")
    private Timestamp publishedDate;

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
}
