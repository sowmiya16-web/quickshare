package com.example.quickshare.controller;
 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/")
    public String home() {
        return "login";  // or return "dashboard"; depending on your first page
    }
}
