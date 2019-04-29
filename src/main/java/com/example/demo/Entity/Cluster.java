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

    public List<ClusterEdge> getClusterEdges() {
        return clusterEdges;
    }

    public Demographic getDemographic() {
        return demographic;
    }

    public boolean isPaired() {
        return paired;
    }

    public void setPaired(boolean paired) {
        this.paired = paired;
    }

    public Cluster getBestNeighbourCluster(){
        Cluster pairCluster = null;
        Double max = 0.0;
        for (ClusterEdge ce:clusterEdges) {
            if(!ce.getConnectCluster(this).isPaired()){
                if(ce.getJoinability()>max){
                    max = ce.getJoinability();
                    pairCluster = ce.getConnectCluster(this);
                }
            }
        }
        return pairCluster;
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
