package com.example.demo.Contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/login")
    public String index() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/batch")
    public String batch() {
        return "batch";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/summary")
    public String summary() {
        return "summary";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}
