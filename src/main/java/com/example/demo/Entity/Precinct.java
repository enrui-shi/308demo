package com.example.demo.Entity;


import com.example.demo.Enum.StateName;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Precinct {
    @Id
    private Long precinctID;

    private ElectionResult electionResult;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

    @ManyToMany
    private List<PrecinctEdge> precinctEdges;

    private Demographic demographic;

    private double majMinRatio;

    private List<Long>NeighbourPrecincts;

    private String county;

    public Long getPrecinctID() {
        return precinctID;
    }

    public void setPrecinctID(Long precinctID) {
        this.precinctID = precinctID;
    }

    public StateName getStateName() {
        return stateName;
    }

    public void setStateName(StateName stateName) {
        this.stateName = stateName;
    }

    public ElectionResult getElectionResult() {
        return electionResult;
    }

    public void setElectionResult(ElectionResult electionResult) {
        this.electionResult = electionResult;
    }

    public List<PrecinctEdge> getPrecinctEdges() {
        return precinctEdges;
    }

    public void setPrecinctEdges(List<PrecinctEdge> precinctEdges) {
        this.precinctEdges = precinctEdges;
    }

    public Demographic getDemographic() {
        return demographic;
    }

    public void setDemographic(Demographic demographic) {
        this.demographic = demographic;
    }

    public double getMajMinRatio() {
        return majMinRatio;
    }

    public void setMajMinRatio(double majMinRatio) {
        this.majMinRatio = majMinRatio;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public List<Long> getNeighbourPrecincts() {
        return NeighbourPrecincts;
    }

    @Override
    public String toString() {
        return "Precinct{" +
                "precinctID=" + precinctID +
                ", electionResult=" + electionResult.toString() +
                ", precinctEdges=" + precinctEdges.toString() +
                ", demographic=" + demographic.toString() +
                ", majMinRatio=" + majMinRatio +
                ", county='" + county + '\'' +
                '}';
    }
}
