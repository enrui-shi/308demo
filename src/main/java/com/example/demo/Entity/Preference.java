package com.example.demo.Entity;

import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Type.Bound;
import java.util.Map;

import javax.persistence.Embeddable;

@Embeddable
public class Preference {

    private int majorityMinorityDistrictNumber;

    private int numberOfDistrict;

    private Map<EthnicGroup,Bound> ethnicGroupBound;

    private double efficiencyGapWeight;

    private double compactnessWeight;

    private double partisanFairnessWeight;

    private double equalPopulationWeight;

    private double naturalConstrainWeight;

    public int getNumberOfDistrict() {
        return numberOfDistrict;
    }
}
