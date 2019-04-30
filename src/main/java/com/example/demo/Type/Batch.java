package com.example.demo.Type;


import com.example.demo.Entity.Preference;
import com.example.demo.Enum.StateName;

public class Batch {

    private int numBatch;

    private int numDistrict;
    private String stateName;

    private Bound numOfMMBound;

    private Bound equalPopulationBound;

    private Bound compactnessBound;

    private Bound partisanFairnessBound;

    private Bound natureConstrainBound;

    public Batch(int numBatch, int numDistrict, String stateName, Bound numOfMMBound, Bound equalPopulationBound, Bound compactnessBound, Bound partisanFairnessBound, Bound natureConstrainBound) {
        this.numBatch = numBatch;
        this.numDistrict = numDistrict;
        this.numOfMMBound = numOfMMBound;
        this.equalPopulationBound = equalPopulationBound;
        this.compactnessBound = compactnessBound;
        this.partisanFairnessBound = partisanFairnessBound;
        this.natureConstrainBound = natureConstrainBound;

    }

    public int getNumBatch() {
        return numBatch;
    }

    public String getStateName() {
        return stateName;
    }

    public StateName getEnumStateName(){
        if(stateName.equals("New York")){
            return StateName.NY;
        }else if (stateName.equals("Ohio")){
            return StateName.OH;
        }else{
            return StateName.NJ;
        }
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

    public Bound getNatureConstrainBound() {
        return natureConstrainBound;
    }

    public void setNumBatch(int numBatch) {
        this.numBatch = numBatch;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
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

    public void setNatureConstrainBound(Bound natureConstrainBound) {
        this.natureConstrainBound = natureConstrainBound;
    }
    public Preference generatePreference(){
        Preference p = new Preference(numDistrict,compactnessBound.generateValue(),partisanFairnessBound.generateValue(),equalPopulationBound.generateValue());
        return p;
    }
}
