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
}
