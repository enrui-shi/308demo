package com.example.demo.Contoller;
import com.example.demo.Entity.State;
import com.example.demo.Entity.Preference;
import com.example.demo.repository.StateRepository;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/home")
public class mainController {

    StateRepository stateRepository;

    @PostMapping(value = "/main/startAlgorithm", consumes = "application/json", produces = "application/json")
    public State createState(@RequestBody Preference preference){
        System.out.println("lalalal");
        State state = new State(preference);
        System.out.println("Number of district in State " + state.getPreference().getNumberOfDistrict());
        return stateRepository.save(state);
    }

    /*@PostMapping(value = "/batch", consumes = "application/json", produces = "application/json")
    public List<State> createStateList(@RequestBody State state){

        stateRepository.save(state);
        return null;
    }*/
}
