package com.example.demo.Entity;

import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Type.Bound;
import java.util.Map;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Preference {

    private int majorityMinorityDistrictNumber;

    private int numberOfDistrict;

    @OneToMany
    private Map<EthnicGroup,Bound> ethnicGroupBound;

    private double efficiencyGapWeight;

    private double compactnessWeight;

    private double partisanFairnessWeight;

    private double equalPopulationWeight;

    private double naturalConstrainWeight;

    public int getMajorityMinorityDistrictNumber() {
        return majorityMinorityDistrictNumber;
    }

    public Map<EthnicGroup, Bound> getEthnicGroupBound() {
        return ethnicGroupBound;
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

    public void setMajorityMinorityDistrictNumber(int majorityMinorityDistrictNumber) {
        this.majorityMinorityDistrictNumber = majorityMinorityDistrictNumber;
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
}
