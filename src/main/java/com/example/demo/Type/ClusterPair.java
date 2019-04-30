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

    public Cluster combine() {
        List<ClusterEdge> edges2 = cluster2.getClusterEdges();
        for (ClusterEdge ce2 : edges2) {
            Cluster connect = ce2.updateCluster(cluster2, cluster1);
            if (cluster1.getNeighborClusters().contains(connect)) {
                ClusterEdge ce1 = cluster1.getEdgeByCluster(connect);
                ce1.merge(ce2);
            } else if (connect != cluster1) {
                cluster1.getClusterEdges().add(ce2);
            }
        }
        cluster1.merge(cluster2);
        cluster1.setPaired(false);
        return cluster1;
    }
}
