package com.example.demo.Entity;

import com.example.demo.Enum.EthnicGroup;

import javax.persistence.*;
import java.util.Map;

@Embeddable
public class Demographic {

    private int totalPopulation;
    @ElementCollection
    @CollectionTable()
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn()
    private Map<EthnicGroup,Integer> ethnicData;

    public int getTotalPopulation() {
        return totalPopulation;
    }

    public Map<EthnicGroup, Integer> getEthnicData() {
        return ethnicData;
    }

    public void merge(Demographic d){
        totalPopulation+=d.totalPopulation;
        ethnicData.forEach((k,v)->v+=d.getEthnicData().get(k));
    }
}
