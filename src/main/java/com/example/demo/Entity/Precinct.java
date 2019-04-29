package com.example.demo.Entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class Precinct {
    @Id
    private String precinctID;

    private ElectionResult lectionResult;

    @OneToMany
    private List<PrecinctEdge> precinctEdges;

    private Demographic demographic;

    private double majMinRatio;

    private String county;

    public String getPrecinctID() {
        return precinctID;
    }

    public void setPrecinctID(String precinctID) {
        this.precinctID = precinctID;
    }

    public ElectionResult getLectionResult() {
        return lectionResult;
    }

    public void setLectionResult(ElectionResult lectionResult) {
        this.lectionResult = lectionResult;
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
}
