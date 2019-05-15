package com.example.demo.Service;

import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Entity.*;
import com.example.demo.Enum.StateName;
import com.example.demo.Enum.*;

import com.example.demo.repository.PrecinctEdgeRepository;
import com.example.demo.repository.PrecinctRepository;
import com.example.demo.temp.GVAL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import static com.example.demo.Enum.EthnicGroup.*;

@Service
public class PhaseOneService {
    @Autowired
    PrecinctRepository precinctRepository;
    @Autowired
    PrecinctEdgeRepository precinctEdgeRepository;

    public Algorithm createAlgorithm(StateName stateName, Preference preference) {
        State state = new State(stateName);
        state.setPreference(preference);

        Algorithm algorithm = new Algorithm(state);
        if(stateName == stateName.OH) {
            algorithm.setClusterEdges(GVAL.ohe);
            algorithm.setClusters(GVAL.oh);
        } else if(stateName == stateName.NY) {
            algorithm.setClusterEdges(GVAL.nye);
            algorithm.setClusters(GVAL.ny);
        } else {
            algorithm.setClusterEdges(GVAL.nje);
            algorithm.setClusters(GVAL.nj);
        }

        algorithm.startGraphPartition();

        algorithm.setColor();

        return algorithm;
    }



    public JsonNode returnPhaseOne(Algorithm algorithm) throws IOException {
        Map<Long, District> precinctToDistrict = algorithm.getPrecinctToDistrict();

        // map precinct Id to district-color
        String colorPrecinct = "{ \"colors\": [";
        for (Map.Entry<Long, District> entry : precinctToDistrict.entrySet()) {
            colorPrecinct += "{\""+entry.getKey()+"\": \""+entry.getValue().getColor()+"\"},";
        }
        colorPrecinct.substring(0, (colorPrecinct.length()-1));
        colorPrecinct += "] }";

        System.out.println(colorPrecinct);

        JsonNode resultNode = null;

        // convert string to json
        try {
            ObjectMapper mapper = new ObjectMapper();
            resultNode = mapper.readTree(colorPrecinct);
        } catch (IOException e) {
            System.out.println(e);
        }

        return resultNode;
    }

    public Map showDemo(Long p_ID) {
        Optional<Precinct> p = precinctRepository.findById(p_ID);
        if(p.isPresent()){
            Precinct precinct = p.get();
            System.out.println(precinct.getDemographic());
            Map<String, String> response = new HashMap<>();
            response.put("Demographic", "found");
            response.put("totalPopulation", precinct.getDemographic().getTotalPopulation()+"");
            response.put("ASIAN_PACIFIC", precinct.getDemographic().getEthnicData().get(EthnicGroup.ASIAN_PACIFIC)+"");
            response.put("LATINO", precinct.getDemographic().getEthnicData().get(LATINO)+"");
            response.put("WHITE", precinct.getDemographic().getEthnicData().get(WHITE)+"");
            response.put("AFRIAN_AMERICAN", precinct.getDemographic().getEthnicData().get(AFRIAN_AMERICAN)+"");
            return response;
        } else {
            System.out.println("Can't find precinct");
            Map<String, String> response = new HashMap();
            response.put("Demographic", "Undefined");
            return response;
        }
    }

}
