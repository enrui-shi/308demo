package com.example.demo.Entity;

import javax.persistence.*;

@Entity
public class ClusterEdge {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Cluster cluster1;
    @OneToOne
    private Cluster cluster2;

    private double countyJoinability;

    private double natureJoinability;

    private double demographicJoinability;

    public double getCountyJoinability() {
        return countyJoinability;
    }

    public double getDemographicJoinability() {
        return demographicJoinability;
    }

    public double getJoinability(){
        Double joinability = (demographicJoinability+countyJoinability)/2;
        return joinability;
    }
    public Cluster getConnectCluster(Cluster c){
        if(c == cluster1){
            return cluster2;
        }else if(c == this.cluster2){
            return cluster1;
        }else{
            return null;
        }
    }

    public void merge(ClusterEdge ce){
        this.countyJoinability  = (this.countyJoinability+ce.getCountyJoinability())/2;
        this.demographicJoinability = (this.demographicJoinability+ce.getDemographicJoinability())/2;
    }

    public Cluster updateCluster(Cluster oldC, Cluster newC){
        if(oldC == this.cluster1){
            cluster1 = newC;
            return cluster2;
        }else if(oldC == this.cluster2){
            cluster2 = newC;
            return cluster1;
        }
        return null;
    }







}
