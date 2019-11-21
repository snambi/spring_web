package com.example.demo.web;

import com.example.demo.data.models.Post;
import com.example.demo.data.services.IPostSvc;
import com.example.demo.services.INewsService;
import com.example.demo.thymeleaf.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Calendar;


@Controller
public class HomeController {

    @Inject
    private IPostSvc iPostSvc;

    @Inject
    private INewsService newsService;

    @Inject
    private Utils.ThymeLeafUtils thymeLeafUtils;

    @GetMapping( value = {"/", "/home"})
    public String home(Model model, HttpServletRequest request ){

        String viewname = null;

        if( thymeLeafUtils.hasLoggedInBefore(request) ){
            viewname = "home";
        }else{
            viewname = "home-landing";
        }

        return viewname;
    }

    @GetMapping("/news")
    public String news( Model model ){

        //List<News> news =  newsRepository.findAll().;
        return "news";
    }


    @GetMapping( value = {"/news_flux"})
    public String news_flux(Model model){
        return "news";
    }

    @GetMapping("/status")
    public String status( Model model){

        model.addAttribute("active", 66);
        model.addAttribute("decom", 33);
        model.addAttribute("error", 443);

        return "status";
    }

    @GetMapping("/write")
    public String sendForm(Post post) {

        return "writePost";
    }

    @PostMapping("/write")
    public String processForm(Post post) {

        return "showMessage";
    }

    @GetMapping("/test")
    public String test( Model model ){
        Timestamp ts = new Timestamp(Calendar.getInstance().getTime().getTime());
        model.addAttribute("time", ts);
        return "test";
    }
}
