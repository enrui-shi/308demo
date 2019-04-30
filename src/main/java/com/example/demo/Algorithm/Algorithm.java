package com.example.demo.Algorithm;

import com.example.demo.Entity.*;
import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Enum.StateName;
import com.example.demo.Type.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Algorithm {

    private List<Cluster> clusters;

    private List<Cluster> tempClusters;

    private List<ClusterEdge> clusterEdges;

    private State currentState;

    private List<ClusterPair> clusterPairs;

    private List<Summary> summarys;

    private Map<Long,District>pctDstMap;

    public Algorithm() {
    }

    public Algorithm(State currentState) {
        this.currentState = currentState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public List<Summary> getSummarys() {
        return summarys;
    }

    public void startGraphPartition(){
        int targetNumber = currentState.getPreference().getNumberOfDistrict();

        while(clusters.size()/2>targetNumber) {
            tempClusters = new ArrayList<>();
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
        while(clusters.size()!=0){
            int index = (int)(Math.random()*clusters.size());
            Cluster cluster = clusters.get(index);
            Cluster pairedCluster = cluster.getBestNeighbourCluster();
            if(pairedCluster != null){
                clusterPairs.add(new ClusterPair(cluster,pairedCluster));
                clusters.remove(cluster);
                clusters.remove(pairedCluster);
                cluster.setPaired(true);
                pairedCluster.setPaired(true);
            }else{
                tempClusters.add(cluster);
                clusters.remove(cluster);
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
        Map<Long,District> idMap = new HashMap<>();
        for(int i = 0;i<clusters.size();i++){
            Cluster c = clusters.get(i);
            District d = new District(c.getPrecincts(),c.getDemographic(),(long)i);
            idMap.put(c.getId(),d);
            currentState.addDistrict(d);
        }
        currentState.setMinorityTarget();
        for(ClusterEdge ce:clusterEdges){
            long id1 = ce.getCluster1().getId();
            long id2 = ce.getCluster2().getId();
            District d1 = idMap.get(id1);
            District d2 = idMap.get(id2);
            d1.addNeighborDistrict(d2);
            d2.addNeighborDistrict(d1);
        }
    }

    public void startSimulateAnnealing(){



    }




    public List<Summary>runBatch(Batch b){
        for(int i = 0;i<b.getNumBatch();i++){


        }


        return summarys;
    }

}
