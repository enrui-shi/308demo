package com.example.demo.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cluster {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @OneToMany
    private List<Precinct> precincs;

    @OneToMany
    private List<Cluster> neighborClusters;

    private Demographic demographic;

    @OneToMany
    private List<ClusterEdge> clusterEdges;

    private boolean paired;
}
