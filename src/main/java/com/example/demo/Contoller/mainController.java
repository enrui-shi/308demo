package com.example.demo.Contoller;
import com.example.demo.Entity.District;
import com.example.demo.Entity.State;
import com.example.demo.Entity.Preference;
import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Enum.StateName;
import com.example.demo.Type.DistrictForGUI;
import com.example.demo.Type.Status;

import org.json.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map startAlgorithm(@RequestBody Preference preference, HttpSession session){
        System.out.println("get user input preference");
        if(session.getAttribute("stateName") == null) {
            Map<String,String> response = new HashMap();
            response.put("status","error");
            response.put("error","select state first");
            return response;
        } else {
            StateName stateName = (StateName) session.getAttribute("stateName");
            State state = new State(stateName);
            state.setPreference(preference);
            Algorithm algorithm = new Algorithm(state);
            algorithm.startGraphPartition();

            session.setAttribute("state", algorithm.getCurrentState());

            List<District> districts = algorithm.getCurrentState().getDistricts();

            /* map precinct Id to district (districtID, district_demographic) */
            Map<Long, DistrictForGUI> phaseOneReturn = new HashMap();

           /* for(int i = 0; i < districts.size(); i++) {
                for(int j=0; j < districts[i].) {

                }
            }*/
            //DistrictForGUI dGUI = new DistrictForGUI();

            return phaseOneReturn;
        }
    }
}
