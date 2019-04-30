package com.example.demo.Type;

import com.example.demo.Enum.Measurement;
import com.example.demo.Enum.Party;
import com.example.demo.Enum.StateName;

import java.util.Map;

public class Summary {

    private Map<Party, Integer> seatsByParty;

    private Map<Measurement, Double> objectiveFunctionValue;

    private Long stateId;

    private StateName stateName;

    private int majorityMinorityDistrict;
}

