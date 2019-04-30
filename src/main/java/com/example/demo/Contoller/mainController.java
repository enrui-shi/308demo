package com.example.demo.Contoller;
import com.example.demo.Entity.State;
import com.example.demo.Entity.Preference;
import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Enum.StateName;
import com.example.demo.Type.Status;
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
    public Status createState(@RequestParam String stateName, HttpSession session){
        System.out.println("get statename");
        StateName stateName1 = StateName.valueOf(stateName);
        session.setAttribute("stateName", stateName1);
        System.out.println("State: " + stateName);
        Status status = new Status("'status': 'OK'");
        return status;
    }

    @PostMapping(value = "/main/startAlgorithm", consumes = "application/json", produces = "application/json")
    public Status startAlgorithm(@RequestBody Preference preference, HttpSession session){
        System.out.println("get user input preference");
        if(session.getAttribute("stateName") == null) {
            Status status = new Status("'status': 'error', 'error':'select state first'");
            return status;
        } else {
            StateName stateName = (StateName) session.getAttribute("stateName");
            State state = new State(stateName);
            state.setPreference(preference);
            Algorithm algorithm = new Algorithm(state);
            algorithm.startGraphPartition();
            // TO DO
            algorithm.getCurrentState();
            Status status = new Status("'status':'OK'");
            return status;
        }
    }
}
