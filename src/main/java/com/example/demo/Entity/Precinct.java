package com.example.demo.Entity;


import com.example.demo.Enum.StateName;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Precinct implements Serializable {
    @Id
    private Long precinctID;

    @OneToOne(fetch = FetchType.EAGER)
    private ElectionResult electionResult;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<PrecinctEdge> precinctEdges;

    @OneToOne(fetch = FetchType.EAGER)
    private Demographic demographic;

    private double minX;

    private double maxX;

    private double minY;

    private double maxY;

    private double majMinRatio;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Long>NeighbourPrecincts;

    public void setNeighbourPrecincts(List<Long> neighbourPrecincts) {
        NeighbourPrecincts = neighbourPrecincts;
    }

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

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }
    public String neighbourList(){
        String str = "[";
        for(Long id:NeighbourPrecincts){
            str+=(id+",");
        }
        return str+"]";
    }

    @Override
    public String toString() {
        return "Precinct{" +
                "precinctID=" + precinctID +
                ", neighbourPrecinct"+neighbourList()+
                ", electionResult=" + electionResult.toString() +
                ", precinctEdges=" + precinctEdges.toString() +
                ", demographic=" + demographic.toString() +
                ", majMinRatio=" + majMinRatio +
                ", county='" + county + '\'' +
                '}';
    }
}
