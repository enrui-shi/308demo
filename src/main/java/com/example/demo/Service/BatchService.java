package com.example.demo.Service;

import com.example.demo.Entity.Cluster;
import com.example.demo.Entity.ClusterEdge;
import com.example.demo.Entity.State;
import com.example.demo.Enum.StateName;
import com.example.demo.repository.ClusterRepository;
import com.example.demo.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BatchService {
    @Autowired
    StateRepository stateRepository;

    @Autowired
    com.example.demo.repository.ClusterEdgeRepository ClusterEdgeRepository;

    @Autowired
    com.example.demo.repository.ClusterRepository ClusterRepository;

    public void addState(State s) {
        stateRepository.save(s);
    }
    public List<ClusterEdge> getClusterEdges(StateName stateName){
        return ClusterEdgeRepository.findAllByStateName(stateName);
    }

    public List<Cluster>getClusters(StateName stateName){
        return ClusterRepository.findAllByStateName(stateName);
    }

}
