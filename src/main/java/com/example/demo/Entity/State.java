package com.example.demo.Entity;


import com.example.demo.Enum.StateName;

import javax.persistence.*;
import java.util.Set;

@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stateId;

    @OneToMany
    private Set<District> districts;

    @OneToMany
    private Set<Precinct> precincts;

    private Preference preference;

    @Enumerated(EnumType.STRING)
    private StateName stateName;



    public State(Set<District> districts, Set<Precinct> precincts, Preference preference, StateName stateName) {
        this.districts = districts;
        this.precincts = precincts;
        this.preference = preference;
        this.stateName = stateName;
    }

    public Long getStateId() {
        return stateId;
    }

    public Set<District> getDistricts() {
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


    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public void setDistricts(Set<District> districts) {
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

}
