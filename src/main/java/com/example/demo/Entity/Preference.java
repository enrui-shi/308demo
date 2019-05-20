package com.example.demo.Entity;

import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Type.Bound;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

@Embeddable
public class Preference {
    @ElementCollection
    @CollectionTable()
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn()
    private Map<EthnicGroup, Integer> ethnicGroupNumber;

    private int numberOfDistrict;

    @ElementCollection
    @CollectionTable()
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn()
    private Map<EthnicGroup, Bound> ethnicGroupBound;

    private double compactnessWeight;

    private double partisanFairnessWeight;

    private double equalPopulationWeight;

    private double lengthWidthWeigth;

    public Preference(){

    }

    public Preference(int numberOfDistrict, double compactnessWeight, double partisanFairnessWeight, double equalPopulationWeight, double lengthWidthWeigth) {
        this.numberOfDistrict = numberOfDistrict;
        this.compactnessWeight = compactnessWeight;
        this.partisanFairnessWeight = partisanFairnessWeight;
        this.equalPopulationWeight = equalPopulationWeight;
        this.lengthWidthWeigth = lengthWidthWeigth;
    }

    public int getNumberByGroup(EthnicGroup eg) {
        return ethnicGroupNumber.get(eg);
    }

    public Map<EthnicGroup, Integer> getEthnicGroupNumber() {
        return ethnicGroupNumber;
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

    public void setNumberOfDistrict(int numberOfDistrict) {
        this.numberOfDistrict = numberOfDistrict;
    }

    public void setEthnicGroupBound(Map<EthnicGroup, Bound> ethnicGroupBound) {
        this.ethnicGroupBound = ethnicGroupBound;
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

    public int getNumberOfDistrict() {
        return numberOfDistrict;
    }

    public Map<EthnicGroup, Bound> getEthnicGroupBound() {
        return ethnicGroupBound;
    }

    public double getTotalWeight(){
        return compactnessWeight+partisanFairnessWeight+equalPopulationWeight+lengthWidthWeigth;
    }
    public double getNormCompactness(){
        return compactnessWeight/getTotalWeight();
    }
    public double getNormPartisanFairness(){
        return partisanFairnessWeight/getTotalWeight();
    }
    public double getNormEqualPopulation(){
        return equalPopulationWeight/getTotalWeight();
    }
    public double getNormLengthWidth(){return lengthWidthWeigth/getTotalWeight();}
}
