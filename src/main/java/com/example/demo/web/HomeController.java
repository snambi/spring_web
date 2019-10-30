package com.example.demo.web;

import com.example.demo.data.models.Post;
import com.example.demo.data.services.IPostSvc;
import com.example.demo.thymeleaf.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;


@Controller
public class HomeController {

    @Inject
    private IPostSvc iPostSvc;

    @Inject
    private Utils.UserLoggedin userLoggedin;

    @GetMapping( value = {"/", "/home"})
    public String home(Model model, HttpServletRequest request ){

        String viewname = null;

        if( userLoggedin.isLoggedin(request) ){
            viewname = "home-landing";
        }else{
            viewname = "home-landing";
        }

        return viewname;
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
}
