package com.example.demo.Entity;

import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Type.Bound;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long districtId;

    @OneToMany
    private Map<Long, Precinct> precincts;

    private Demographic demographic;

    @OneToMany
    private List<District> neighborDistrict;

    private boolean minorityTarget;

    private boolean minorityDistrict;

    private String color;

    @Enumerated(EnumType.STRING)
    private EthnicGroup targetEthnic;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void addNeighborDistrict(District d) {
        this.neighborDistrict.add(d);
    }

    public void setMinorityTarget(boolean minorityTarget) {
        this.minorityTarget = minorityTarget;
    }

    public void setTargetEthnic(EthnicGroup targetEthnic) {
        this.targetEthnic = targetEthnic;
    }

    public Demographic getDemographic() {
        return demographic;
    }

    public District(List<Precinct> precincts, Demographic demographic, Long districtId) {
        for (Precinct p : precincts) {
            this.precincts.put(p.getPrecinctID(), p);
        }
        this.demographic = demographic;
        this.districtId = districtId;
        this.neighborDistrict = new ArrayList<>();
    }

    public boolean checkMinorityBound(Map<EthnicGroup, Bound> groupBound, Precinct p) {
        if (!this.minorityTarget) {
            return true;
        } else {
            Bound ethnicBound = groupBound.get(targetEthnic);
            if (precincts.containsValue(p)) {
                int total = demographic.getTotalPopulation() - p.getDemographic().getTotalPopulation();
                int group = demographic.getNumberByGroup(targetEthnic) - p.getDemographic().getNumberByGroup(targetEthnic);
                return ethnicBound.checkInbound((double) group / total);
            } else {
                int total = demographic.getTotalPopulation() + p.getDemographic().getTotalPopulation();
                int group = demographic.getNumberByGroup(targetEthnic) + p.getDemographic().getNumberByGroup(targetEthnic);
                return ethnicBound.checkInbound((double) group / total);
            }
        }
    }

    public String toString() {
        return "district: {" +
                "districtID:" + districtId +
                ", d_color:" + color +
                "," + demographic.toString() +
                '}';
    }

}
