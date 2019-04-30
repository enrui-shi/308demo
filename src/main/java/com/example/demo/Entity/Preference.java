package com.example.demo.Entity;

import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Type.Bound;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Preference {

    private Map<EthnicGroup,Integer> ethnicGroupNumber;

    private int numberOfDistrict;

    @OneToMany
    private Map<EthnicGroup,Bound> ethnicGroupBound;

    private double efficiencyGapWeight;

    private double compactnessWeight;

    private double partisanFairnessWeight;

    private double equalPopulationWeight;

    private double naturalConstrainWeight;

    public Preference(int numberOfDistrict, double efficiencyGapWeight, double compactnessWeight, double partisanFairnessWeight, double equalPopulationWeight, double naturalConstrainWeight) {
        this.numberOfDistrict = numberOfDistrict;
        this.efficiencyGapWeight = efficiencyGapWeight;
        this.compactnessWeight = compactnessWeight;
        this.partisanFairnessWeight = partisanFairnessWeight;
        this.equalPopulationWeight = equalPopulationWeight;
        this.naturalConstrainWeight = naturalConstrainWeight;
    }

    public int getNumberByGroup(EthnicGroup eg){
        return ethnicGroupNumber.get(eg);
    }

    public Map<EthnicGroup, Integer> getEthnicGroupNumber() {
        return ethnicGroupNumber;
    }


    public double getEfficiencyGapWeight() {
        return efficiencyGapWeight;
    }

    public double getCompactnessWeight() {
        return compactnessWeight;
    }

    public double getPartisanFairnessWeight() {
        return partisanFairnessWeight;
    }

    public double getEqualPopulationWeight() {
        return equalPopulationWeight;
    }

    public double getNaturalConstrainWeight() {
        return naturalConstrainWeight;
    }


    public void setNumberOfDistrict(int numberOfDistrict) {
        this.numberOfDistrict = numberOfDistrict;
    }

    public void setEthnicGroupBound(Map<EthnicGroup, Bound> ethnicGroupBound) {
        this.ethnicGroupBound = ethnicGroupBound;
    }

    public void setEfficiencyGapWeight(double efficiencyGapWeight) {
        this.efficiencyGapWeight = efficiencyGapWeight;
    }

    public void setCompactnessWeight(double compactnessWeight) {
        this.compactnessWeight = compactnessWeight;
    }

    public void setPartisanFairnessWeight(double partisanFairnessWeight) {
        this.partisanFairnessWeight = partisanFairnessWeight;
    }

    public void setEqualPopulationWeight(double equalPopulationWeight) {
        this.equalPopulationWeight = equalPopulationWeight;
    }

    public void setNaturalConstrainWeight(double naturalConstrainWeight) {
        this.naturalConstrainWeight = naturalConstrainWeight;
    }

    public int getNumberOfDistrict() {
        return numberOfDistrict;
    }

    public Map<EthnicGroup, Bound> getEthnicGroupBound() {
        return ethnicGroupBound;
    }
}
