package com.example.demo.Contoller;

import com.example.demo.Entity.District;
import com.example.demo.Entity.State;
import com.example.demo.Entity.Preference;
import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Enum.StateName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.Service.PhaseOneService;
import com.example.demo.Service.PhaseTwoService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/home")
public class mainController {
    @Autowired
    PhaseOneService p1s;
    @Autowired
    PhaseTwoService p2s;

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

    @PostMapping(value = "/main/phaseOneWithUpdate",consumes = "application/json", produces = "application/json")
    public Map phaseOneWithUpdate(@RequestBody Preference preference, HttpSession session){
        if (session.getAttribute("stateName") == null) {
            Map<String, String> response = new HashMap();
            response.put("status", "error");
            response.put("error", "select state first");
            return response;
        } else {
            Map<String, String> response = new HashMap();
            StateName stateName = (StateName) session.getAttribute("stateName");
            Algorithm algorithm = p1s.createAlgorithm(stateName, preference);
            session.setAttribute("precinctToDistrict", algorithm.getPrecinctToDistrict());
            session.setAttribute("state", algorithm.getCurrentState());
            session.setAttribute("PhaseOneChange",algorithm.getPhaseOneChange());
            response.put("status","OK");
            return response;
        }
    }

    @PostMapping(value = "/main/getphasechange",consumes = "application/json", produces = "application/json")
    public Map getPhaseOneChange(HttpSession session){
        List<Map<Long,String>> list = (List<Map<Long, String>>) session.getAttribute("PhaseOneChange");
        if(list.size()>0) {
            return list.remove(0);
        }else{
            Map<Long, String> response = new HashMap();
            response.put(Long.valueOf(0),"end");
            return response;
        }
    }

    //private final DeferredResult<List<JsonNode>> phaseTwoResult = new DeferredResult<>();

    /* long pulling request to response to GUI */
    @RequestMapping("/main/startPhaseTwo")
    public Map startPhaseTwo(@RequestParam(required = false) Long timestamp, HttpSession session) throws IOException {
    //public DeferredResult<List<JsonNode>> startPhaseTwo(@RequestParam(required = false) Long timestamp, HttpSession session) throws IOException {

        State s = (State) session.getAttribute("state");
        Map<Long, District> pToD =(Map<Long, District>) session.getAttribute("precinctToDistrict");
        Algorithm a = new Algorithm(s, pToD);

        a.startSimulateAnnealing();
        Map<String, String> response = new HashMap();
        response.put("status", "ok");
        return response;
    }

    @RequestMapping("/main/getChangeOfPhase2")
    public Map getPhase2Change(@RequestParam(required = false) Long timestamp, HttpSession session) {
        // TO DO get change from list
        return null;
    }

    @PostMapping(value = "/main/showDemo", consumes = "application/json", produces = "application/json")
    public Map showDemo(@RequestParam Long precinctID, HttpSession session) {
        System.out.println(precinctID);
        // find precinct demographic data
        return p1s.showDemo(precinctID);
    }

}
