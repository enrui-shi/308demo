package com.example.demo.Contoller;
import com.example.demo.Entity.State;
import com.example.demo.Entity.Preference;
import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Enum.StateName;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/home")
public class mainController {

    @PostMapping(value = "/main/createState", consumes = "application/json", produces = "application/json")
    public StateName createState(@RequestParam String stateName, HttpSession session){
        System.out.println("create state");
        StateName stateName1 = StateName.valueOf(stateName);
        session.setAttribute("stateName", stateName1);
        System.out.println("State: " + stateName);
        return stateName1;
    }

    @PostMapping(value = "/main/startAlgorithm", consumes = "application/json", produces = "application/json")
    public Algorithm startAlgorithm(@RequestBody Preference preference, HttpSession session){
        if(session.getAttribute("stateName") == null) {
            return null;
        } else {
            StateName stateName = (StateName) session.getAttribute("stateName");
            State state = new State(stateName);
            state.setPreference(preference);
            Algorithm algorithm = new Algorithm(state);
            algorithm.startGraphPartition();
            return algorithm;
        }
    }
}
