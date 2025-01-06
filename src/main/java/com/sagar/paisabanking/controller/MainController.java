package com.sagar.paisabanking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    private String index() {
        return "index";
    }

    @GetMapping("/dashboard")
    private String dashboard(){
        return "Dashboard";
    }




}
