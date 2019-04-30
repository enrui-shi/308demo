package com.example.demo.Service;

import com.example.demo.Entity.Precinct;
import com.example.demo.repository.PrecinctRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrecinctService {
    @Autowired
    PrecinctRepository precinctRepository;

    public void addPrecinct(Precinct p) {
        precinctRepository.save(p);
    }

}
