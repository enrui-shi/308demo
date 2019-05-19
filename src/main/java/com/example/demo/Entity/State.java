package com.example.demo.Entity;


import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Enum.StateName;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;


@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stateId;

    @OneToMany
    private List<District> districts;

    @OneToMany
    private Set<Precinct> precincts;

    private Preference preference;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

    private int totalPopulation;

    public State() {
    }

    public State(StateName stateName) {
        districts = new ArrayList<>();
        this.stateName = stateName;
    }

    public State(List<District> districts, Set<Precinct> precincts, Preference preference, StateName stateName) {
        this.districts = districts;
        this.precincts = precincts;
        this.preference = preference;
        this.stateName = stateName;
        this.totalPopulation = 0;
    }

    public Long getStateId() {
        return stateId;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public Set<Precinct> getPrecincts() {
        return precincts;
    }

    public StateName getStateName() {
        return stateName;
    }

    public Preference getPreference() {
        return preference;
    }

    public int getTotalPopulation() {
        if(totalPopulation == 0){
            for(District d: districts){
                totalPopulation+=d.getDemographic().getTotalPopulation();
            }
        }
        return totalPopulation;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public void setPrecincts(Set<Precinct> precincts) {
        this.precincts = precincts;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public void setStateName(StateName stateName) {
        this.stateName = stateName;
    }

    public void addDistrict(District d) {
        districts.add(d);
    }

    public void setMinorityTarget() {
        Map<EthnicGroup, Integer> groups = preference.getEthnicGroupNumber();
        for (EthnicGroup eg : groups.keySet()) {
            districts.sort(Comparator.comparing(d -> d.getDemographic().getRatioByGroup(eg)));
            for (int i = 1; i <= groups.get(eg); i++) {
                District d = districts.get(districts.size() - i);
                d.setMinorityTarget(true);
                d.setTargetEthnic(eg);
            }
        }

    }

}
