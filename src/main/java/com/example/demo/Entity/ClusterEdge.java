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

    public double getJoinability(){
        return (demographicJoinability+countyJoinability)/2;
    }
    public Cluster getConnectCluster(Cluster c){
        if(c == this.cluster1){
            return this.cluster2;
        }else if (c == this.cluster2){
            return this.cluster1;
        }else{
            return null;
        }
    }





}
