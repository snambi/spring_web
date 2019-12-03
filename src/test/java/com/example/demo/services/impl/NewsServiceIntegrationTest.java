package com.example.demo.services.impl;

import com.example.demo.data.models.News;
import com.example.demo.services.INewsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
public class NewsServiceIntegrationTest {

    @TestConfiguration
    static class NewsServiceImplTestContextConfiguration {

        @Bean
        public INewsService employeeService() {
            return new NewsService();
        }
    }

    @Inject
    INewsService  newsService;

    @Test
    public void createTest(){

        News news = new News();
        News result = newsService.create(news);

        Assert.assertNotNull(result);
    }

}
