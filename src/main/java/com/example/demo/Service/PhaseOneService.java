package com.example.demo.Service;

import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Entity.*;
import com.example.demo.Enum.StateName;

import com.example.demo.repository.PrecinctEdgeRepository;
import com.example.demo.repository.PrecinctRepository;
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
        initClusters(algorithm);

        algorithm.startGraphPartition();

        algorithm.setColor();

        return algorithm;
    }

    public void initClusters(Algorithm a) {
       /* StateName stateName = a.getCurrentState().getStateName();

        Map<Long, Precinct> precincts = new HashMap<>();
        List<Precinct> pList = precinctRepository.findAllByStateName(stateName);
        List<PrecinctEdge> peList = precinctEdgeRepository.findAllByStateName(stateName);

        for(int i=0; i<pList.size(); i++) {
            precincts.put(pList.get(i).getPrecinctID(), pList.get(i));
        }

        Map<Long, Cluster> clusters = new HashMap<>();
        List<Cluster> cList = new ArrayList<>();
        List<ClusterEdge> ceList = new ArrayList<>();


        // init cluster with id, stateName, demographic
        for(int i=0; i<precincts.size(); i++) {
            Cluster c = new Cluster(precincts.get(i).getPrecinctID(), stateName, precincts.get(i).getDemographic());
            cList.add(c);
            clusters.put(c.getId(), c);
        }

        // set cluster neighbor list
        for(int i=0; i<cList.size(); i++) {
            // find the corresponding precinct by cluster id
            Precinct p = precincts.get(cList.get(i).getId());
            for(int j=0; j<p.getNeighbourPrecincts().size(); j++) {
                cList.get(i).getNeighborClusters().add(clusters.get(p.getNeighbourPrecincts().get(j)));
            }
        }

        // set cluster edge
        for(int i=0; i<peList.size(); i++) {
            PrecinctEdge pe = peList.get(i);
            Long p1_ID = pe.getPrecinct1();
            Long p2_ID = pe.getPrecinct2();
            Cluster c1 = clusters.get(p1_ID);
            Cluster c2 = clusters.get(p2_ID);
            ClusterEdge ce = new ClusterEdge(pe.getId(), c1, c2, stateName, pe.getCountyJoinability(),
                    pe.getNatureJoinability(), pe.getDemographicJoinability());
            c1.getClusterEdges().add(ce);
            c2.getClusterEdges().add(ce);
            ceList.add(ce);
        }

        a.setClusters(cList);
        a.setClusterEdges(ceList);*/

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

        // convert string to json
        ObjectMapper mapper = new ObjectMapper();
        JsonNode resultNode = mapper.readTree(colorPrecinct);

        return resultNode;
    }

    public void showDemo(Long p_ID) {
        Optional<Precinct> p = precinctRepository.findById(p_ID);
        if(p.isPresent()){
            Precinct precinct = p.get();

            System.out.println(precinct.getDemographic());
        }
    }

}
