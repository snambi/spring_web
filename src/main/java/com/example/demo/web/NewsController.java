package com.example.demo.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class NewsController {

    @GetMapping("/news")
    public String news( Model model ){

        //List<News> news =  newsRepository.findAll().;
        return "news";
    }


    @GetMapping( value = {"/news_flux"})
    public String news_flux(Model model){
        return "news";
    }
}
