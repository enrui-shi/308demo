package com.example.demo.Contoller;

import com.example.demo.repository.StateRepository;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class mainController {

    /*StateRepository stateRepository;

    @PostMapping(value = "/main", consumes = "application/json", produces = "application/json")
    public State createState(@RequestBody State state){
        return stateRepository.save(state);
    }

    @PostMapping(value = "/batch", consumes = "application/json", produces = "application/json")
    public List<State> createState(@RequestBody State state){

        stateRepository.save(state);
    }*/
}
