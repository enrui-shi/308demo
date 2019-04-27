package com.example.demo.Contoller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testController {
    @RequestMapping("/")
    public String index() {
        return "login";
    }
    @GetMapping("/main")
    public String mainPage(){return "main";}
    @GetMapping("/register")
    public String register(){ return "register";}
    @GetMapping("/batch")
    public String batch(){ return "batch";}
}
