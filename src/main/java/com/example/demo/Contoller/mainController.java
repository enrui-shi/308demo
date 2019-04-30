package com.example.demo.Contoller;
import com.example.demo.Entity.District;
import com.example.demo.Entity.State;
import com.example.demo.Entity.Preference;
import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Enum.StateName;

import org.json.*;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
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
    public Map createState(@RequestParam String stateName, HttpSession session){
        System.out.println("get statename");
        StateName stateName1 = StateName.valueOf(stateName);
        session.setAttribute("stateName", stateName1);
        System.out.println("State: " + stateName);
        Map<String,String> response = new HashMap();
        response.put("status","error");
        response.put("error","cannot find user");
        return response;
    }

    @PostMapping(value = "/main/startPhaseOne", consumes = "application/json", produces = "application/json")
    public JsonNode startAlgorithm(@RequestBody Preference preference, HttpSession session) throws IOException{
        System.out.println("get user input preference");
        if(session.getAttribute("stateName") == null) {
            String response = "{\"status\", \"error\", \"error\", \"select state first\"}";
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseNode = mapper.readTree(response);
            return responseNode;
        } else {
            StateName stateName = (StateName) session.getAttribute("stateName");
            State state = new State(stateName);
            state.setPreference(preference);
            Algorithm algorithm = new Algorithm(state);
            algorithm.startGraphPartition();

            session.setAttribute("state", algorithm.getCurrentState());

            Map<Long, District> pctDstMap = algorithm.getPctDstMap();

            /* map precinct Id to district (districtID, district_demographic) */
            String phaseOneJson = "{ return: [";
            for(Map.Entry<Long,District> entry: pctDstMap.entrySet()) {
                phaseOneJson += "{ \"precinctID\" : \""+entry.getKey()+"\", "+entry.getValue().toString()+"} , ";
            }
            phaseOneJson += "] }";

            /* convert string to json */
            ObjectMapper mapper = new ObjectMapper();
            JsonNode resultNode = mapper.readTree(phaseOneJson);

            return resultNode;
        }
    }
}
