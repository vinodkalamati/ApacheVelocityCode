package com.cgi.demoproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

    @GetMapping("/tempo")
    public String next() {
        return "tempo";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/upload")
    public String redirect(){
        return "status";
    }

    public ModelAndView error(){
        return new ModelAndView("index","message","Internal server error");
    }
}
