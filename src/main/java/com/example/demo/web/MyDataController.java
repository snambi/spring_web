package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyDataController {

    @GetMapping("/mydata")
    public String myData( Model model){

        return "mydata";
    }
}
