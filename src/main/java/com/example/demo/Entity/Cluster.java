package com.example.demo.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cluster implements Comparable< Cluster > {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @OneToMany
    private List<Precinct> precincts;

    @OneToMany
    private List<Cluster> neighborClusters;

    private Demographic demographic;

    @OneToMany
    private List<ClusterEdge> clusterEdges;

    private boolean paired;

    public Demographic getDemographic() {
        return demographic;
    }

    public Cluster getBestNeighbourCluster(){
        return null;

    }

    @Override
    public int compareTo(Cluster o) {
        int population1 = demographic.getTotalPopulation();
        int population2 = o.getDemographic().getTotalPopulation();
        if(population1>population2){
            return 1;
        }
        else if(population1 == population2){
            return 0;
        }
        else{
            return -1;
        }
    }
}
