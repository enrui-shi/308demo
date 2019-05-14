package com.example.demo.Algorithm;

import com.example.demo.Entity.*;
import com.example.demo.Enum.StateName;
import com.example.demo.Service.BatchService;
import com.example.demo.Type.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Algorithm {

    private List<Cluster> clusters;

    private List<Cluster> tempClusters;

    private List<ClusterEdge> clusterEdges;

    private State currentState;

    private List<ClusterPair> clusterPairs = new ArrayList<ClusterPair>();

    private Map<Long, District> precinctToDistrict;

    private int totalPopulation;

    public Algorithm() {
    }

    public Map<Long, District> getPrecinctToDistrict() {
        return precinctToDistrict;
    }

    public Algorithm(State currentState) {
        this.currentState = currentState;
    }

    public State getCurrentState() {
        return currentState;
    }


    public void startGraphPartition() {
        int targetNumber = currentState.getPreference().getNumberOfDistrict();

        while (clusters.size() / 2 > targetNumber) {
            System.out.print("Try to merge "+clusters.size()+" clusters");
            tempClusters = new ArrayList<>();
            int pickIndex = (int) Math.floor(clusters.size() * 0.8);
            Collections.sort(clusters);
            for (int i = pickIndex; i < clusters.size(); i++) {
                tempClusters.add(clusters.get(i));
            }
            System.out.print("The smallest removed cluster has population "+tempClusters.get(0).getDemographic().getTotalPopulation());
            clusters.removeAll(tempClusters);

            determineCandidatePair();
            System.out.print("Find "+clusterPairs.size()+" pairs");
            for (ClusterPair cp : clusterPairs) {
                Cluster c = cp.combine();
                clusters.add(c);
            }
            clusters.addAll(tempClusters);
        }
        finish(targetNumber);
        toDistrict();
    }

    public void determineCandidatePair() {
        while (clusters.size() != 0) {
            int index =randomIndex(clusters.size()) ;
            Cluster cluster = clusters.get(index);
            Cluster pairedCluster = cluster.getBestNeighbourCluster();
            if (pairedCluster != null) {
                clusterPairs.add(new ClusterPair(cluster, pairedCluster));
                clusters.remove(cluster);
                clusters.remove(pairedCluster);
                cluster.setPaired(true);
                pairedCluster.setPaired(true);
            } else {
                tempClusters.add(cluster);
                clusters.remove(cluster);
            }
        }
    }

    public void finish(int target) {
        System.out.print("Final step with "+clusters.size());
        while (clusters.size() > target) {
            Collections.sort(clusters);
            Cluster c1 = clusters.get(0);
            Cluster c2 = c1.getBestNeighbourCluster();
            ClusterPair cp = new ClusterPair(c1, c2);
            clusters.remove(c1);
            clusters.remove(c2);
            c1 = cp.combine();
            clusters.add(c1);
        }
    }

    public void toDistrict() {
        Map<Long, District> idMap = new HashMap<>();
        for (int i = 0; i < clusters.size(); i++) {
            Cluster c = clusters.get(i);
            District d = new District(c.getPrecincts(), c.getDemographic(), (long) i);
            idMap.put(c.getId(), d);
            currentState.addDistrict(d);
        }
        currentState.setMinorityTarget();
        for (ClusterEdge ce : clusterEdges) {
            long id1 = ce.getCluster1().getId();
            long id2 = ce.getCluster2().getId();
            District d1 = idMap.get(id1);
            District d2 = idMap.get(id2);
            d1.addNeighborDistrict(d2);
            d2.addNeighborDistrict(d1);
        }
    }

    public Summary startSimulateAnnealing() {
        List<Precinct>movable = new ArrayList<>();
        for (District d : currentState.getDistricts()) {
            movable.addAll(d.getBoundPrecinct());
        }
        while (movable.size()!=0){
            int index = randomIndex(movable.size());
            Precinct candidate = movable.get(index);
            District from = getPrecinctBelongs(candidate.getPrecinctID());
            List<District>toDistrict = getToDistrict(candidate.getPrecinctID());
            for(District to:toDistrict){
                Move move = new Move(from,to,candidate);
                if (move.checkMajorityMinority(currentState.getPreference())){

                }


            }


        }

        return null;
    }


    public List<Summary> runBatch(Batch b, BatchService batchService) {
        List<Summary> summaries = new ArrayList<>();
        for (int i = 0; i < b.getNumBatch(); i++) {
            StateName stateName = b.getEnumStateName();
            this.currentState = new State(stateName);
            this.clusters = batchService.getClusters(stateName);
            this.clusterEdges = batchService.getClusterEdges(stateName);
            currentState.setPreference(b.generatePreference());
            this.startGraphPartition();
            Summary s = this.startSimulateAnnealing();
            batchService.addState(currentState);
            summaries.add(s);
        }
        return summaries;
    }


    public int randomIndex(int size){
        return (int) (Math.random() * size);
    }

    public List<District>getToDistrict(Long p) {
        District from = getPrecinctBelongs(p);
        List<District>to = new ArrayList<>();
        for(long np :p.getNeighbourPrecincts()) {
            if(getPrecinctBelongs(np)!= from){
                to.add(getPrecinctBelongs(np));
            }
        }
        return to;
    }

    public District getPrecinctBelongs(long p) {
        return precinctToDistrict.get(p);
    }


    // set district color after phase one finish
    public void setColor() {
        String[] colors = new String[]{"#ff0000","#0040ff","#ff8000","#00ff00","#ffb3d9","#9900cc","#ffff00","#006600"};
        List<District> d = currentState.getDistricts();
        int needColor = 0;
        for (int i=0; i<d.size(); i++) {
            if (d.get(i).getColor().equals("")) {
                d.get(i).setColor(colors[0]);
                List<District> neighbor = d.get(i).getNeighborDistrict();
                for (int j = 0; j < neighbor.size(); j++) {
                    if (neighbor.get(j).getColor().equals("")) {
                        needColor++;
                        if (needColor <= 7) {
                            neighbor.get(j).setColor(colors[needColor]);
                        }
                    }
                }
            }
        }
        checkColor();
    }

    // check if adjacent districts are in different colors
    public void checkColor() {
        List<District> d = currentState.getDistricts();
        for(int i=0; i<d.size(); i++) {
            List<District> neighbor = d.get(i).getNeighborDistrict();
            for(int j=0; j < neighbor.size(); j++) {
                // color is same ==> change neighbor color
                if(d.get(i).getColor().equals(neighbor.get(j).getColor())){
                    List<District> dnn = neighbor.get(j).getNeighborDistrict();
                    ArrayList<String> notUsedColor = new ArrayList<>(Arrays.asList("#ff0000","#0040ff","#ff8000",
                            "#00ff00","#ffb3d9","#9900cc","#ffff00","#006600"));
                    for(int k=0; k<dnn.size(); k++){
                        notUsedColor.remove(dnn.get(k).getColor());
                    }
                    if(notUsedColor.size() > 0) {
                        neighbor.get(j).setColor(notUsedColor.get(0));
                    } else {
                        System.out.println("colors are not enough!!!");
                        neighbor.get(j).setColor("#00ffff");
                    }
                }
            }
        }
    }
}
