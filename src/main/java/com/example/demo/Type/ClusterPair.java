package com.example.demo.Type;

import com.example.demo.Entity.Cluster;
import com.example.demo.Entity.ClusterEdge;
import java.util.List;

public class ClusterPair {

    private Cluster cluster1;

    private Cluster cluster2;

    public ClusterPair(Cluster cluster1, Cluster cluster2) {
        this.cluster1 = cluster1;
        this.cluster2 = cluster2;
    }

    public Cluster combine(){

        List<ClusterEdge> ce2 = cluster2.getClusterEdges();


        return null;
    }
}
