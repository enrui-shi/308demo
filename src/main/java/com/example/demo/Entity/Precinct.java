package com.example.demo.Entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Precinct {
    @Id
    private Long precinctID;

    private ElectionResult electionResult;

    @OneToMany
    private List<PrecinctEdge> precinctEdges;

    private Demographic demographic;

    private double majMinRatio;

    private String county;

    public Long getPrecinctID() {
        return precinctID;
    }

    public void setPrecinctID(Long precinctID) {
        this.precinctID = precinctID;
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
