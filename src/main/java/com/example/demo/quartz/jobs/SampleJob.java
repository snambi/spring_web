package com.example.demo.quartz.jobs;

import com.example.demo.data.models.News;
import com.example.demo.services.INewsService;
import com.example.demo.utils.RssUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SampleJob implements Job {

    private static Logger log = LoggerFactory.getLogger( SampleJob.class );

    @Inject
    INewsService newsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {

        log.info("Begin Fetch job");

        // Add new items for every invocation

        Map<String,String> NYT = new HashMap<>();

        // geographical news
        NYT.put("https://rss.nytimes.com/services/xml/rss/nyt/World.xml", "World");
        NYT.put("https://rss.nytimes.com/services/xml/rss/nyt/AsiaPacific.xml", "Asia Pacific");
        NYT.put("https://rss.nytimes.com/services/xml/rss/nyt/Americas.xml", "Americas");
        NYT.put("https://rss.nytimes.com/services/xml/rss/nyt/MiddleEast.xml", "Middle East");
        NYT.put("https://rss.nytimes.com/services/xml/rss/nyt/Europe.xml", "Europe");
        NYT.put("https://rss.nytimes.com/services/xml/rss/nyt/Africa.xml", "Africa");


        for( String feed : NYT.keySet() ){
            List<News> news = RssUtils.convert(feed, NYT.get(feed), "NY Times" );
            newsService.create(news);
        }


    }
}
