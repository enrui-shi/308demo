package com.example.demo.Service;

import com.example.demo.Entity.Precinct;
import com.example.demo.Entity.PrecinctEdge;
import com.example.demo.repository.PrecinctEdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrecinctEdgeService {
    @Autowired
    PrecinctEdgeRepository precinctEdgeRepository;


    public void addPrecinctEdge(PrecinctEdge p) {
        precinctEdgeRepository.save(p);
    }
}
