package com.example.demo.Contoller;

import com.example.demo.Entity.District;
import com.example.demo.Entity.State;
import com.example.demo.Entity.Preference;
import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Enum.StateName;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.example.demo.Service.PhaseOneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.async.DeferredResult;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/home")
public class mainController {
    @Autowired
    PhaseOneService p1s;


    @PostMapping(value = "/main/createState", consumes = "application/json", produces = "application/json")
    public Map createState(@RequestParam String stateName, HttpSession session) {
        System.out.println("get statename");
        StateName stateName1 = StateName.valueOf(stateName);
        session.setAttribute("stateName", stateName1);
        System.out.println("State: " + stateName);
        Map<String, String> response = new HashMap();
        response.put("status", "ok");
        return response;
    }

    @PostMapping(value = "/main/startPhaseOne", consumes = "application/json", produces = "application/json")
    public Map startAlgorithm(@RequestBody Preference preference, HttpSession session) throws IOException {
        System.out.println("get user input preference");
        if (session.getAttribute("stateName") == null) {
            Map<String, String> response = new HashMap();
            response.put("status", "error");
            response.put("error", "select state first");
            return response;
        } else {
            StateName stateName = (StateName) session.getAttribute("stateName");


            Algorithm algorithm = p1s.createAlgorithm(stateName, preference);


            session.setAttribute("state", algorithm.getCurrentState());
            session.setAttribute("precinctToDistrict", algorithm.getPrecinctToDistrict());

            Map<String, String> result = p1s.returnPhaseOne(algorithm);
            for(int i=0; i<algorithm.getCurrentState().getDistricts().size(); i++){
                System.out.println(algorithm.getCurrentState().getDistricts().get(i).getColor());
            }
            return result;
        }
    }


    private final DeferredResult<List<JsonNode>> phaseTwoResult = new DeferredResult<>();

    /* long pulling request to response to GUI */
    @RequestMapping("/main/startPhaseTwo")
    public DeferredResult<List<JsonNode>> startPhaseTwo(@RequestParam(required = false) Long timestamp, HttpSession session) throws IOException {

        State s = (State) session.getAttribute("state");
        Map<Long, District> pToD =(Map<Long, District>) session.getAttribute("precinctToDistrict");
        Algorithm a = new Algorithm(s, pToD);

        a.startSimulateAnnealing();
        // TO DO
        return phaseTwoResult;
    }

    @PostMapping(value = "/main/showDemo", consumes = "application/json", produces = "application/json")
    public Map showDemo(@RequestParam Long precinctID, HttpSession session) {
        System.out.println(precinctID);
        // find precinct demographic data
        return p1s.showDemo(precinctID);
    }

}
