package com.example.demo.Contoller;

import com.example.demo.repository.StateRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class mainController {

    StateRepository stateRepository;
    //@PostMapping(value = "/main",produces = "application/json")
}
