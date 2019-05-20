package com.example.demo.Service;

import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Entity.*;
import com.example.demo.Enum.StateName;
import com.example.demo.Enum.*;

import com.example.demo.repository.PrecinctEdgeRepository;
import com.example.demo.repository.PrecinctRepository;
import com.example.demo.temp.GVAL;
import com.fasterxml.jackson.core.type.TypeReference;
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

        ObjectMapper objectMapper = new ObjectMapper();
        Algorithm algorithm = new Algorithm(state);
        try{
        if(stateName == stateName.OH) {
            System.out.println("OH DATA LOAD");
            //algorithm.setClusterEdges(objectMapper.readValue(objectMapper.writeValueAsString(GVAL.ohe), new TypeReference<List<ClusterEdge>>(){}));
            //algorithm.setClusters(objectMapper.readValue(objectMapper.writeValueAsString(GVAL.oh), new TypeReference<List<Cluster>>(){}));
            algorithm.setClusterEdges(GVAL.ohe);
            algorithm.setClusters(GVAL.oh);
            System.out.println(algorithm.getClusterEdges().get(0));
        } else if(stateName == stateName.NY) {
            algorithm.setClusterEdges(GVAL.nye);
            algorithm.setClusters(GVAL.ny);
        } else {
            algorithm.setClusterEdges(GVAL.iae);
            algorithm.setClusters(GVAL.ia);
        }
        }catch (Exception e){
            System.out.println(e);
        }
        algorithm.startGraphPartition();

        algorithm.setColor();

        return algorithm;
    }



    public Map returnPhaseOne(Algorithm algorithm) throws IOException {
        Map<Long, District> precinctToDistrict = algorithm.getPrecinctToDistrict();
        Map<String, String> result = new HashMap();
        // map precinct Id to district-color
        for (Map.Entry<Long, District> entry : precinctToDistrict.entrySet()) {
            result.put(Long.toString(entry.getKey()), entry.getValue().getColor());
        }
        System.out.println("phase one finished and start to color");
        return result;
    }

    public Map showDemo(Long p_ID) {
        Optional<Precinct> p = precinctRepository.findById(p_ID);
        Map<String, String> response = new HashMap<>();
        if(p.isPresent()){
            Precinct precinct = p.get();
            System.out.println(precinct.getDemographic());
            response.put("Demographic", "found");
            response.put("totalPopulation", precinct.getDemographic().getTotalPopulation()+"");
            response.put("ASIAN_PACIFIC", precinct.getDemographic().getEthnicData().get(EthnicGroup.ASIAN_PACIFIC)+"");
            response.put("LATINO", precinct.getDemographic().getEthnicData().get(LATINO)+"");
            response.put("WHITE", precinct.getDemographic().getEthnicData().get(WHITE)+"");
            response.put("AFRIAN_AMERICAN", precinct.getDemographic().getEthnicData().get(AFRIAN_AMERICAN)+"");
            return response;
        } else {
            System.out.println("Can't find precinct");
            response.put("Demographic", "Undefined");
            return response;
        }
    }

    public Map showDemoAfterPlay(Long c_ID, Map<Long, District> pToD) {
        Map<String, String> response = new HashMap<>();

        if (pToD.size() == 0) {
            response.put("Demographic", "Undefined");
            return response;
        } else {
            response.put("Demographic", "found");
            response.put("id", Long.toString(pToD.get(c_ID).getDistrictId()));
            response.put("totalPopulation", pToD.get(c_ID).getDemographic().getTotalPopulation() + "");
            response.put("ASIAN_PACIFIC", pToD.get(c_ID).getDemographic().getEthnicData().get(EthnicGroup.ASIAN_PACIFIC) + "");
            response.put("LATINO", pToD.get(c_ID).getDemographic().getEthnicData().get(LATINO) + "");
            response.put("WHITE", pToD.get(c_ID).getDemographic().getEthnicData().get(WHITE) + "");
            response.put("AFRIAN_AMERICAN", pToD.get(c_ID).getDemographic().getEthnicData().get(AFRIAN_AMERICAN) + "");
            return response;
        }
    }

}
