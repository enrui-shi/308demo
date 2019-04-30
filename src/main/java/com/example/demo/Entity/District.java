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

    public void addNeighborDistrict(District d){
        this.neighborDistrict.add(d);
    }

    public Demographic getDemographic() {
        return demographic;
    }

    public District(List<Precinct> precincts, Demographic demographic, Long districtId) {
        this.precincts = precincts;
        this.demographic = demographic;
        this.districtId = districtId;
        this.neighborDistrict = new ArrayList<>();
    }

    public boolean checkMinorityBound(Map<EthnicGroup,Bound> groupBound, Precinct p){
        if(!this.minorityTarget){
            return true;
        }else{
            Bound ethnicBound = groupBound.get(targetEthnic);
            int sign = 1;
            if(precincts.contains(p)){
                int total = demographic.getTotalPopulation() - p.getDemographic().getTotalPopulation();
                int group = demographic.getNumberByGroup(targetEthnic) - p.getDemographic().getNumberByGroup(targetEthnic);
                return ethnicBound.checkInbound((double) group / total);
            }else {
                int total = demographic.getTotalPopulation() + p.getDemographic().getTotalPopulation();
                int group = demographic.getNumberByGroup(targetEthnic) + p.getDemographic().getNumberByGroup(targetEthnic);
                return ethnicBound.checkInbound((double) group / total);
            }
        }
    }
}
