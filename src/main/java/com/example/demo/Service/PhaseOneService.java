package com.example.demo.Service;

import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Entity.*;
import com.example.demo.Enum.StateName;

import com.example.demo.repository.PrecinctEdgeRepository;
import com.example.demo.repository.PrecinctRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
        algorithm.setClusters(initClusters(algorithm));

        algorithm.startGraphPartition();

        algorithm.setColor();

        return algorithm;
    }

    public List<Cluster> initClusters(Algorithm a) {
        Map<Long, Precinct> precincts = new HashMap<>();
        List<Precinct> pList = precinctRepository.findAllByStateName(a.getCurrentState().getStateName());
        for(int i=0; i<pList.size(); i++) {
            precincts.put(pList.get(i).getPrecinctID(), pList.get(i));
        }

        Map<Long, Cluster> clusters = new HashMap<>();
        List<Cluster> cList = new ArrayList<>();
        List<ClusterEdge> ceList = new ArrayList<>();

        // init cluster with id, stateName, demographic
        for(int i=0; i<precincts.size(); i++) {
            Cluster c = new Cluster(precincts.get(i).getPrecinctID(), a.getCurrentState().getStateName(), precincts.get(i).getDemographic());
            cList.add(c);
            clusters.put(c.getId(), c);
        }

        // set cluster neighbor list
        for(int i=0; i<cList.size(); i++) {
            // find the corresponding precinct by cluster id
            Precinct p = precincts.get(cList.get(i).getId());
            List<Cluster> neighborList = new ArrayList<>();
            for(int j=0; j<p.getNeighbourPrecincts().size(); j++) {
                neighborList.add(clusters.get(p.getNeighbourPrecincts().get(j)));
            }
            cList.get(i).setNeighborClusters(neighborList);
        }

        // set cluster edge
        for(int i=0; i<cList.size(); i++) {
            Precinct p = precincts.get(cList.get(i).getId());
            List<ClusterEdge> edgeList = new ArrayList<>();
            for(int j=0; j<p.getPrecinctEdges().size(); j++) {
                PrecinctEdge pe = p.getPrecinctEdges().get(j);
                ClusterEdge e = new ClusterEdge(pe.getId(), clusters.get(pe.getPrecinct1()), clusters.get(pe.getPrecinct2()), p.getStateName(),
                        pe.getCountyJoinability(), pe.getNatureJoinability(), pe.getDemographicJoinability());
                edgeList.add(e);

            }
            cList.get(i).setClusterEdges(edgeList);
        }

        return cList;
    }

    public List<ClusterEdge> convertEdges(List<PrecinctEdge> pes) {

    }

}
