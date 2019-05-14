package com.example.demo.Entity;

import com.example.demo.Enum.StateName;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cluster implements Comparable<Cluster> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

    @OneToMany
    private List<Precinct> precincts;

    @OneToMany
    private List<Cluster> neighborClusters;

    @OneToOne
    private Demographic demographic;

    @ManyToMany
    private List<ClusterEdge> clusterEdges;

    private boolean paired;

    public Cluster(StateName stateName, Demographic demographic) {
        this.stateName = stateName;
        this.demographic = demographic;
    }

    public void setPrecincts(List<Precinct> precincts) {
        this.precincts = precincts;
    }

    public void setNeighborClusters(List<Cluster> neighborClusters) {
        this.neighborClusters = neighborClusters;
    }

    public void setDemographic(Demographic demographic) {
        this.demographic = demographic;
    }

    public void setClusterEdges(List<ClusterEdge> clusterEdges) {
        this.clusterEdges = clusterEdges;
    }

    public void setStateName(StateName stateName) {
        this.stateName = stateName;
    }

    public Long getId() {
        return id;
    }

    public List<Precinct> getPrecincts() {
        return precincts;
    }

    public List<Cluster> getNeighborClusters() {
        return neighborClusters;
    }

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

    public void merge(Cluster c) {
        precincts.addAll(c.getPrecincts());
        neighborClusters.addAll(c.neighborClusters);
        neighborClusters.remove(this);
        demographic.merge(c.getDemographic());

    }

    public Cluster getBestNeighbourCluster() {
        Cluster pairCluster = null;
        double max = 0.0;
        for (ClusterEdge ce : clusterEdges) {
            if (!ce.getConnectCluster(this).isPaired()) {
                if (ce.getJoinability() >= max) {
                    max = ce.getJoinability();
                    pairCluster = ce.getConnectCluster(this);
                }
            }
        }
        return pairCluster;
    }

    public ClusterEdge getEdgeByCluster(Cluster c) {
        for (ClusterEdge ce : clusterEdges) {
            if (ce.getConnectCluster(this) == c) {
                return ce;
            }
        }
        return null;
    }

    @Override
    public int compareTo(Cluster o) {
        int population1 = demographic.getTotalPopulation();
        int population2 = o.getDemographic().getTotalPopulation();
        if (population1 > population2) {
            return 1;
        } else if (population1 == population2) {
            return 0;
        } else {
            return -1;
        }
    }


}
