package com.example.demo.Service;

import com.example.demo.Entity.Cluster;
import com.example.demo.Enum.StateName;
import com.example.demo.repository.ClusterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class ClusterService {
    @Autowired
    ClusterRepository ClusterRepository;
    public List<Cluster>getClusters(StateName stateName){
        return ClusterRepository.findAllByStateName(stateName);
    }
}
