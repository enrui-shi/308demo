package com.example.demo.Service;

import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Entity.*;
import com.example.demo.Enum.StateName;

import com.example.demo.repository.PrecinctEdgeRepository;
import com.example.demo.repository.PrecinctRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        setClusters(algorithm);

        algorithm.startGraphPartition();

        algorithm.setColor();

        return algorithm;
    }

    public void setClusters(Algorithm a) {
        List<Precinct> precincts = precinctRepository.findAllByStateName(a.getCurrentState().getStateName());
        List<Cluster> clusters = new ArrayList<>();

        for(int i=0; i<precincts.size(); i++) {
            Cluster c = new Cluster(precincts.get(i).getPrecinctID(), a.getCurrentState().getStateName(), precincts.get(i).getDemographic());
            clusters.add(c);
        }

    }

    /*public List<ClusterEdge> convertEdges(List<PrecinctEdge> pes) {
        for() {

        }
    }*/

}
