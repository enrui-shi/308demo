package com.example.demo.Service;

import com.example.demo.Entity.Demographic;
import com.example.demo.Entity.ElectionResult;
import com.example.demo.Entity.Precinct;
import com.example.demo.Entity.PrecinctEdge;
import com.example.demo.repository.DemographicRepository;
import com.example.demo.repository.ElectionResultRepository;
import com.example.demo.repository.PrecinctEdgeRepository;
import com.example.demo.repository.PrecinctRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitService {
    @Autowired
    PrecinctRepository precinctRepository;
    @Autowired
    PrecinctEdgeRepository precinctEdgeRepository;
    @Autowired
    DemographicRepository demographicRepository;
    @Autowired
    ElectionResultRepository electionResultRepository;

    public void addPrecinctEdge(PrecinctEdge p) {
        precinctEdgeRepository.save(p);
    }
    public void addPrecinct(Precinct p) {
        precinctRepository.save(p);
    }
    public void addDemographic(Demographic d){
        demographicRepository.save(d);
    }
    public void addElectionResult(ElectionResult e){
        electionResultRepository.save(e);
    }
}
