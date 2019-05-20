package com.example.demo.Type;


import com.example.demo.Entity.Preference;
import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Enum.StateName;

import java.util.EnumMap;
import java.util.Map;

public class Batch {

    private int numBatch;

    private Bound numDistrictBound;

    private String stateName;

    private Bound numOfMMBound;

    private Bound equalPopulationBound;

    private Bound compactnessBound;

    private Bound partisanFairnessBound;

    private Bound lengthWidthCompactnessBound;

    public Batch() {

    }

    public Batch(int numBatch, Bound numDistrictBound, String stateName, Bound numOfMMBound, Bound equalPopulationBound,
                 Bound compactnessBound, Bound partisanFairnessBound, Bound lengthWidthCompactnessBound) {
        this.numBatch = numBatch;
        this.numDistrictBound = numDistrictBound;
        this.stateName = stateName;
        this.numOfMMBound = numOfMMBound;
        this.equalPopulationBound = equalPopulationBound;
        this.compactnessBound = compactnessBound;
        this.partisanFairnessBound = partisanFairnessBound;
        this.lengthWidthCompactnessBound = lengthWidthCompactnessBound;
    }

    public int getNumBatch() {
        return numBatch;
    }

    public String getStateName() {
        return stateName;
    }

    public StateName getEnumStateName() {
        if (stateName.equals("NY")) {
            return StateName.NY;
        } else if (stateName.equals("OH")) {
            return StateName.OH;
        } else {
            return StateName.NJ;
        }
    }
    public Bound getNumDistrictBound() {
        return numDistrictBound;
    }

    public Bound getNumOfMMBound() {
        return numOfMMBound;
    }

    public Bound getEqualPopulationBound() {
        return equalPopulationBound;
    }

    public Bound getCompactnessBound() {
        return compactnessBound;
    }

    public Bound getPartisanFairnessBound() {
        return partisanFairnessBound;
    }

    public Bound getLengthWidthCompactnessBound() {
        return lengthWidthCompactnessBound;
    }

    public void setNumBatch(int numBatch) {
        this.numBatch = numBatch;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void setNumDistrictBound(Bound numDistrictBound) {
        this.numDistrictBound = numDistrictBound;
    }

    public void setNumOfMMBound(Bound numOfMMBound) {
        this.numOfMMBound = numOfMMBound;
    }

    public void setEqualPopulationBound(Bound equalPopulationBound) {
        this.equalPopulationBound = equalPopulationBound;
    }

    public void setCompactnessBound(Bound compactnessBound) {
        this.compactnessBound = compactnessBound;
    }

    public void setPartisanFairnessBound(Bound partisanFairnessBound) {
        this.partisanFairnessBound = partisanFairnessBound;
    }

    public void setLengthWidthCompactnessBound(Bound lengthWidthCompactnessBound) {
        this.lengthWidthCompactnessBound = lengthWidthCompactnessBound;
    }

    public Preference generatePreference() {
        Preference p = new Preference((int)numDistrictBound.generateValue(), compactnessBound.generateValue(),
                partisanFairnessBound.generateValue(), equalPopulationBound.generateValue(),lengthWidthCompactnessBound.generateValue());
        Map<EthnicGroup,Bound> map =new EnumMap(EthnicGroup.class);
        map.put(EthnicGroup.AFRIAN_AMERICAN,new Bound(0.7,0.3));
        map.put(EthnicGroup.ASIAN_PACIFIC, new Bound(0.7,0.3));
        map.put(EthnicGroup.LATINO, new Bound(0.7,0.3));
        p.setEthnicGroupBound(map);
        return p;
    }


    @Override
    public String toString() {
        return "Batch{" +
                "numBatch=" + numBatch +
                ", numDistrictBound=" + numDistrictBound +
                ", stateName='" + stateName + '\'' +
                ", numOfMMBound=" + numOfMMBound +
                ", equalPopulationBound=" + equalPopulationBound +
                ", compactnessBound=" + compactnessBound +
                ", partisanFairnessBound=" + partisanFairnessBound +
                ", lengthWidthCompactnessBound=" + lengthWidthCompactnessBound +
                '}';
    }
}
