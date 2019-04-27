package com.example.demo.Entity;


import com.example.demo.Enum.StateName;

import javax.persistence.*;
import java.util.Set;

@Entity
public class State {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long stateId;

    @OneToMany
    private Set<District> districts;

    @OneToMany
    private Set<Precinct> precincts;

    private Preference preference;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

}
