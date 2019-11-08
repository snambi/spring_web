package com.example.demo.web;

import com.example.demo.data.models.News;
import com.example.demo.data.models.Post;
import com.example.demo.data.reactive.INewsRepository;
import com.example.demo.data.services.IPostSvc;
import com.example.demo.thymeleaf.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Calendar;


@Controller
public class HomeController {

    @Inject
    private IPostSvc iPostSvc;

    @Inject
    private Utils.UserLoggedin userLoggedin;

    @Inject
    private INewsRepository newsRepository;

    @GetMapping( value = {"/", "/home"})
    public String home(Model model, HttpServletRequest request ){

        String viewname = null;

        if( userLoggedin.hasLoggedInBefore(request) ){
            viewname = "home";
        }else{
            viewname = "home-landing";
        }

        return viewname;
    }

    @GetMapping( value = {"/news"})
    public String news( Model model){

        // loads 1 and display 1, stream data, data driven mode.
//        IReactiveDataDriverContextVariable reactiveDataDrivenMode = new ReactiveDataDriverContextVariable(
//                newsRepository.findAll(), 2);

        Flux<News> news =  newsRepository.findAll();
        model.addAttribute("items", news);

        //model.addAttribute("items", newsRepository.findAllNews());

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
