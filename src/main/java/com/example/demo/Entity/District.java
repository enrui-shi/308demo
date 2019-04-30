package com.example.demo.Entity;

import com.example.demo.Enum.EthnicGroup;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class District {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long districtId;

    @OneToMany
    private List<Precinct> precincts;

    private Demographic demographic;

    @OneToMany
    private List<District> neighborDistrict;

    private boolean minorityTarget;

    private boolean minorityDistrict;

    @Enumerated(EnumType.STRING)
    private EthnicGroup targetEthnic;

    public District(List<Precinct> precincts, Demographic demographic, Long districtId) {
        this.precincts = precincts;
        this.demographic = demographic;
        this.districtId = districtId;
        this.neighborDistrict = new ArrayList<>();
    }
}
