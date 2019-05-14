package com.example.demo.Contoller;

import com.example.demo.Entity.District;
import com.example.demo.Entity.State;
import com.example.demo.Entity.Preference;
import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Enum.EthnicGroup;
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
    public JsonNode startAlgorithm(@RequestBody Preference preference, HttpSession session) throws IOException {
        System.out.println("get user input preference");
        if (session.getAttribute("stateName") == null) {
            String response = "{\"status\", \"error\", \"error\", \"select state first\"}";
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseNode = mapper.readTree(response);
            return responseNode;
        } else {
            StateName stateName = (StateName) session.getAttribute("stateName");
            /* for test
            System.out.println(preference.getNumberOfDistrict());
            System.out.println(preference.getCompactnessWeight());
            for(EthnicGroup key: preference.getEthnicGroupNumber().keySet()) {
                System.out.println(key);
            }*/

            Algorithm algorithm = p1s.createAlgorithm(stateName, preference);


            session.setAttribute("state", algorithm.getCurrentState());
            session.setAttribute("precinctToDistrict", algorithm.getPrecinctToDistrict());

            Map<Long, District> precinctToDistrict = algorithm.getPrecinctToDistrict();

            // map precinct Id to district-color
            String colorPrecinct = "{ \"colors\": [";
            for (Map.Entry<Long, District> entry : precinctToDistrict.entrySet()) {
                colorPrecinct += "{\""+entry.getKey()+"\": \""+entry.getValue().getColor()+"\"},";
            }
            colorPrecinct.substring(0, (colorPrecinct.length()-1));
            colorPrecinct += "] }";

            System.out.println(colorPrecinct);

            // convert string to json
            ObjectMapper mapper = new ObjectMapper();
            JsonNode resultNode = mapper.readTree(colorPrecinct);

            return resultNode;
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
}
