package com.example.demo.Entity;

import com.example.demo.Enum.StateName;

import javax.persistence.*;

@Entity
public class ClusterEdge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Cluster cluster1;
    @OneToOne
    private Cluster cluster2;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

    private double countyJoinability;

    private double natureJoinability;

    private double demographicJoinability;

    public ClusterEdge(Long id, Cluster cluster1, Cluster cluster2, StateName stateName, double countyJoinability, double natureJoinability, double demographicJoinability) {
        this.id = id;
        this.cluster1 = cluster1;
        this.cluster2 = cluster2;
        this.stateName = stateName;
        this.countyJoinability = countyJoinability;
        this.natureJoinability = natureJoinability;
        this.demographicJoinability = demographicJoinability;
    }
    public ClusterEdge(){};

    public Cluster getCluster1() {
        return cluster1;
    }

    public Cluster getCluster2() {
        return cluster2;
    }

    public double getCountyJoinability() {
        return countyJoinability;
    }

    public double getDemographicJoinability() {
        return demographicJoinability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCluster1(Cluster cluster1) {
        this.cluster1 = cluster1;
    }

    public void setCluster2(Cluster cluster2) {
        this.cluster2 = cluster2;
    }

    public StateName getStateName() {
        return stateName;
    }

    public void setStateName(StateName stateName) {
        this.stateName = stateName;
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

    public void setDemographicJoinability(double demographicJoinability) {
        this.demographicJoinability = demographicJoinability;
    }

    public double getJoinability() {
        double joinability = (demographicJoinability + countyJoinability) / 2;
        return joinability;
    }

    public Cluster getConnectCluster(Cluster c) {
        if (c == cluster1) {
            return cluster2;
        } else if (c == this.cluster2) {
            return cluster1;
        } else {
            return null;
        }
    }

    public void merge(ClusterEdge ce) {
        this.countyJoinability = (this.countyJoinability + ce.getCountyJoinability()) / 2;
        this.demographicJoinability = (this.demographicJoinability + ce.getDemographicJoinability()) / 2;
    }

    public Cluster updateCluster(Cluster oldC, Cluster newC) {
        if (oldC == this.cluster1) {
            cluster1 = newC;
            return cluster2;
        } else if (oldC == this.cluster2) {
            cluster2 = newC;
            return cluster1;
        }
        return null;
    }


}
