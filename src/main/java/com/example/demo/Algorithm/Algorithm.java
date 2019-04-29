package com.example.demo.Algorithm;

import com.example.demo.Entity.Cluster;
import com.example.demo.Entity.ClusterEdge;
import com.example.demo.Entity.State;
import com.example.demo.Type.ClusterPair;
import com.example.demo.Type.Summary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Algorithm {

    private List<Cluster> clusters;

    private List<ClusterEdge> clusterEdges;

    private State currentState;

    private List<ClusterPair> clusterPairs;

    private List<Summary> summarys;

    public Algorithm(State currentState) {
        this.currentState = currentState;
    }

    public void startGraphPartition(){
        int targetNumber = currentState.getPreference().getNumberOfDistrict();
        while(clusters.size()/2>targetNumber) {

            Collections.sort(clusters);
            int pickIndex = (int)Math.floor(clusters.size() * 0.8);
            List<Cluster> tempClusters = new ArrayList<>();
            for(int i = pickIndex; i< clusters.size();i++){
                tempClusters.add(clusters.get(i));
            }
            clusters.removeAll(tempClusters);

            for(int i = 0; i<clusters.size();i++){
                Cluster cluster = clusters.get(i);
                Cluster pairedCluster = cluster.getBestNeighbourCluster();
                if( pairedCluster != null){
                    clusterPairs.add(new ClusterPair(cluster,pairedCluster));
                    clusters.remove(cluster);
                    clusters.remove(pairedCluster);
                    cluster.setPaired(true);
                    pairedCluster.setPaired(true);
                    i--;
                }
            }

            for(ClusterPair cp:clusterPairs){
                Cluster c = cp.combine();
            }

        }
    }

}
