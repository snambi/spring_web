package com.example.demo.data.models;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;

public class News {

    // fields related to the news item
    private String headline;
    private String description;
    private URL url;
    private Timestamp publishedDate;

    // metadata about the news item
    private Integer votes;
    private List<String> tags;
    private Timestamp createdTime;
    private Timestamp updatedTime;

    // internal metadata about the news item
    private boolean approved;


    public News(String headline, URL url, List<String> tags) {
        this.headline = headline;
        this.url = url;
        this.tags = tags;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Timestamp getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Timestamp publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
