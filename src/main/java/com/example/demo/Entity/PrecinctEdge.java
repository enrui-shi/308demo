package com.example.demo.Entity;

import javax.persistence.*;

@Entity
public class PrecinctEdge {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;
    @OneToOne
    private Precinct precinct1;
    @OneToOne
    private Precinct precinct2;

    private double countyJoinability;

    private double natureJoinability;

    private double demographicJoinability;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Precinct getPrecinct1() {
        return precinct1;
    }

    public void setPrecinct1(Precinct precinct1) {
        this.precinct1 = precinct1;
    }

    public Precinct getPrecinct2() {
        return precinct2;
    }

    public void setPrecinct2(Precinct precinct2) {
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
}
