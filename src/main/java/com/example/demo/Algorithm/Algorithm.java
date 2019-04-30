package com.example.demo.Algorithm;

import com.example.demo.Entity.Cluster;
import com.example.demo.Entity.ClusterEdge;
import com.example.demo.Entity.State;
import com.example.demo.Type.ClusterPair;
import com.example.demo.Type.Summary;
import com.example.demo.Entity.District;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
            List<Cluster> tempClusters = new ArrayList<>();
            int pickIndex = (int)Math.floor(clusters.size() * 0.8);
            Collections.sort(clusters);
            for(int i = pickIndex; i< clusters.size();i++){
                tempClusters.add(clusters.get(i));
            }
            clusters.removeAll(tempClusters);

            determineCandidatePair();

            for(ClusterPair cp:clusterPairs){
                Cluster c = cp.combine();
                clusters.add(c);
            }
            clusters.addAll(tempClusters);
        }
        finish(targetNumber);
        toDistrict();


    }

    public void determineCandidatePair(){
        for(int i = 0; i<this.clusters.size();i++){
            Cluster cluster = this.clusters.get(i);
            Cluster pairedCluster = cluster.getBestNeighbourCluster();
            if( pairedCluster != null){
                clusterPairs.add(new ClusterPair(cluster,pairedCluster));
                this.clusters.remove(cluster);
                this.clusters.remove(pairedCluster);
                cluster.setPaired(true);
                pairedCluster.setPaired(true);
            }
        }
    }
    public void finish(int target){
        while(clusters.size()>target){
            Collections.sort(clusters);
            Cluster c1 = clusters.get(0);
            Cluster c2 = c1.getBestNeighbourCluster();
            ClusterPair cp = new ClusterPair(c1,c2);
            clusters.remove(c1);
            clusters.remove(c2);
            c1 = cp.combine();
            clusters.add(c1);
        }
    }
    public void toDistrict(){
        Map<String,String> idMap = new HashMap<>();
        for(int i = 0;i<clusters.size();i++){
            Cluster c = clusters.get(i);
            //District d = new District(c.getPrecincts(),c.getDemographic(),(Long)i);
            //idMap.put(c.getId(),Integer.toString(i));
        }
        for(ClusterEdge ce:clusterEdges){

        }
    }

}
