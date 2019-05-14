package com.example.demo.Entity;

import com.example.demo.Enum.StateName;

import javax.persistence.*;

@Entity
public class PrecinctEdge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long precinct1;

    private Long precinct2;

    private double countyJoinability;

    private double natureJoinability;

    private double demographicJoinability;

    @Enumerated(EnumType.STRING)
    private StateName stateName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrecinct1() {
        return precinct1;
    }

    public void setPrecinct1(Long precinct1) {
        this.precinct1 = precinct1;
    }

    public Long getPrecinct2() {
        return precinct2;
    }

    public void setPrecinct2(Long precinct2) {
        this.precinct2 = precinct2;
    }

    public double getCountyJoinability() {
        return countyJoinability;
    }

    public void setCountyJoinability(double countyJoinability) {
        this.countyJoinability = countyJoinability;
    }

    public double getNatureJoinability() {
        return natureJoinability;
    }

    public void setNatureJoinability(double natureJoinability) {
        this.natureJoinability = natureJoinability;
    }

    public double getDemographicJoinability() {
        return demographicJoinability;
    }

    public void setDemographicJoinability(double demographicJoinability) {
        this.demographicJoinability = demographicJoinability;
    }

    public StateName getStateName() {
        return stateName;
    }

    public void setStateName(StateName stateName) {
        this.stateName = stateName;
    }

    @Override
    public String toString() {
        return "PrecinctEdge{" +
                "id=" + id +
                ", precinct1=" + precinct1 +
                ", precinct2=" + precinct2 +
                ", countyJoinability=" + countyJoinability +
                ", natureJoinability=" + natureJoinability +
                ", demographicJoinability=" + demographicJoinability +
                '}';
    }
}
