package com.example.demo.Service;

import com.example.demo.Entity.State;
import com.example.demo.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class StateService {
    @Autowired
    StateRepository stateRepository;
    public void addState(State s) {
        stateRepository.save(s);
    }

}
