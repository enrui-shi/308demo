package com.example.demo.Type;

import javax.persistence.OneToOne;

public class Batch {

    private int numBatch;

    private String stateName;

    private Bound numOfMMBound;

    private Bound equalPopulationBound;

    private Bound compactnessBound;

    private Bound partisanFairnessBound;

    @OneToOne
    private Bound natureConstrainBound;

    public Batch(int numBatch, String stateName, Bound numOfMMBound, Bound equalPopulationBound, Bound compactnessBound, Bound partisanFairnessBound, Bound natureConstrainBound) {
        this.numBatch = numBatch;
        this.stateName = stateName;
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
}
