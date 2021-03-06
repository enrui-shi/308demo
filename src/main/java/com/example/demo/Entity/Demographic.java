package com.example.demo.Entity;

import com.example.demo.Enum.EthnicGroup;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Entity
public class Demographic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int totalPopulation;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="EMP_ethnicData")
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name="EthnicGroup")
    @Column(name="data")
    private Map<EthnicGroup, Integer> ethnicData;


    public int getTotalPopulation() {
        return totalPopulation;
    }

    public Map<EthnicGroup, Integer> getEthnicData() {
        return ethnicData;
    }

    public void setTotalPopulation(int totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public void setEthnicData(Map<EthnicGroup, Integer> ethnicData) {
        this.ethnicData = ethnicData;
    }

    public void merge(Demographic d) {
        totalPopulation += d.totalPopulation;
        for(EthnicGroup ethnicGroup:ethnicData.keySet()){
            ethnicData.replace(ethnicGroup,ethnicData.get(ethnicGroup)+d.getEthnicData().get(ethnicGroup));
        }
    }

    public int getNumberByGroup(EthnicGroup eg) {
        return ethnicData.get(eg);
    }

    public double getRatioByGroup(EthnicGroup eg) {
        return (double) ethnicData.get(eg) / (double)totalPopulation;
    }


    public void removeDemo(Demographic d){
        totalPopulation -= d.totalPopulation;
        for(EthnicGroup ethnicGroup:ethnicData.keySet()){
            ethnicData.replace(ethnicGroup,ethnicData.get(ethnicGroup)-d.getEthnicData().get(ethnicGroup));
        }

    }


    @Override
    public String toString() {
        return "\"Demographic\": {" +
                "\"totalPopulation\":" + totalPopulation +
                // TO DO enum toString() might be an error
                ", \"ethnicData\":" + ethnicData.toString().replace('=',':') +
                '}';
    }
}
