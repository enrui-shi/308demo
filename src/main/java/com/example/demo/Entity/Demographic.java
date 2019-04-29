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
}
