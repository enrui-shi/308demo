package com.example.demo.Algorithm;

import com.example.demo.Entity.*;
import com.example.demo.Enum.Party;
import com.example.demo.Enum.StateName;
import com.example.demo.Service.BatchService;
import com.example.demo.Type.*;

import java.awt.*;
import java.util.*;
import java.util.List;


public class Algorithm {

    private List<Cluster> clusters;

    private List<Cluster> tempClusters;

    private List<ClusterEdge> clusterEdges;

    private State currentState;

    private List<ClusterPair> clusterPairs = new ArrayList<ClusterPair>();

    private Map<Long, District> precinctToDistrict;

    private List<Map<Long,String>> phaseOneChange = new ArrayList<>();

    public Algorithm() {
    }

    public Algorithm(State currentState) {
        this.currentState = currentState;
    }

    public Algorithm(State currentState, Map<Long, District> precinctToDistrict) {
        this.currentState = currentState;
        this.precinctToDistrict = precinctToDistrict;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    public void setClusterEdges(List<ClusterEdge> clusterEdges) {
        this.clusterEdges = clusterEdges;
    }

    public List<ClusterEdge> getClusterEdges() {
        return clusterEdges;
    }

    public Map<Long, District> getPrecinctToDistrict() {
        return precinctToDistrict;
    }


    public State getCurrentState() {
        return currentState;
    }


    public void startGraphPartition() {
        initData();
        System.out.println("######Start Graph Partition##########");
        int targetNumber = currentState.getPreference().getNumberOfDistrict();

        while (clusters.size() / 2 > targetNumber) {
            System.out.println("Try to merge "+clusters.size()+" clusters");
            tempClusters = new ArrayList<>();
            clusterPairs = new ArrayList<>();
            int pickIndex = (int) Math.floor(clusters.size() * 0.8);
            Collections.sort(clusters);
            for (int i = pickIndex; i < clusters.size(); i++) {
                tempClusters.add(clusters.get(i));
                clusters.get(i).setPaired(true);
            }
            System.out.println("The smallest removed cluster has population "+tempClusters.get(0).getDemographic().getTotalPopulation());
            clusters.removeAll(tempClusters);

            determineCandidatePair();
            System.out.println("Find "+clusterPairs.size()+" pairs");
            for (ClusterPair cp : clusterPairs) {
                //System.out.println("C1 is "+cp.getCluster1().getId()+" C2 is "+cp.getCluster2().getId());
                Cluster c = cp.combine();
                clusters.add(c);
            }
            tempBack();
            phaseOneChange.add(genChange());
        }
        finish(targetNumber);
        toDistrict();
    }

    public Map<Long,String> genChange(){
        Map<Long,String> map =new HashMap<>();
        for(Cluster cluster : clusters){
            Color c=new Color((int)(Math.random() * 0x1000000));
            String rgb = Integer.toHexString(c.getRGB());
            for(Precinct p:cluster.getPrecincts()){
                map.put(p.getPrecinctID(),rgb);
            }
        }
        return map;
    }
    public void initData(){
        for (ClusterEdge ce:this.clusterEdges){
            ce.getCluster1().addCE(ce);
            ce.getCluster2().addCE(ce);
            ce.getCluster1().addNeighbour(ce.getCluster2());
            ce.getCluster2().addNeighbour(ce.getCluster1());
        }
    }

    public void determineCandidatePair() {
        System.out.println("Find Candidate Pair");
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
                cluster.setPaired(true);
                tempClusters.add(cluster);
                clusters.remove(cluster);
            }
        }
    }

    public void finish(int target) {
        System.out.println("Final step with "+clusters.size());
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
    public void tempBack(){
        for(Cluster c: tempClusters){
            c.setPaired(false);
            clusters.add(c);
        }
    }

    public void toDistrict() {
        precinctToDistrict = new HashMap<>();
        Map<Long, District> idMap = new HashMap<>();
        clusterEdges = new ArrayList<>();
        for (int i = 0; i < clusters.size(); i++) {
            Cluster c = clusters.get(i);
            District d = new District(c.getPrecincts(), c.getDemographic(), (long) i);
            idMap.put(c.getId(), d);
            for(ClusterEdge ce:c.getClusterEdges()){
                if(!clusterEdges.contains(ce)){
                    clusterEdges.add(ce);
                }
            }
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
        for(District d :idMap.values()){
            for( Long pID  :d.getPrecincts().keySet()){
                precinctToDistrict.put(pID,d);
            }
        }
    }

    public Summary startSimulateAnnealing() {
        for (District d : currentState.getDistricts()) {
            double score = measureDistrict(d);
            d.setScore(score);
        }
        int count = 1000;
        while (count!=0){
            Precinct candidate = null;
            while(candidate == null) {
                int districtIndex = randomIndex(currentState.getDistricts().size());
                candidate =currentState.getDistricts().get(districtIndex).getCandidatePrecinct();
            }
            Move move = testMove(candidate);
            if(move != null){
                move.execute();
                precinctToDistrict.put(move.getPrecinct().getPrecinctID(),move.getTo());
            }
            count --;
        }

        return null;
    }

    public Move testMove(Precinct candidate){
        District from = getPrecinctBelongs(candidate.getPrecinctID());
        List<District>toDistrict = getToDistrict(candidate);
        Map<Double,Move> scoreChange = new HashMap<>();
        for(District to:toDistrict){
            Move move = new Move(from,to,candidate);
            double origin = from.getScore()+to.getScore();
            if (move.checkMajorityMinority(currentState.getPreference())) {
                move.tryMove();
                double changed =measureDistrict(move.getTo())+measureDistrict(move.getFrom());
                move.undo();
                if(changed-origin>0){
                    scoreChange.put(changed-origin,move);
                }
            }
        }
        if(!scoreChange.isEmpty()){
            double max = 0.0;
            for(double key:scoreChange.keySet()){
                if(key>max){
                    max = key;
                }
            }
           return scoreChange.get(max);
        }
        return null;

    }

    public double measureDistrict(District d){
        Preference p = currentState.getPreference();
        return p.getNormEqualPopulation()*equalPopulationMeasure(d)+p.getNormPartisanFairness()*efficiencyGapMeasure(d);
    }

    public double compactnessMeasure(District d){
        int bound = d.getBoundPrecinct().size();
        int total = d.getPrecincts().size();
        return (double)(total-bound)/(double)total;
    }
    public double efficiencyGapMeasure(District d){
        int dvote = 0;
        int rvote = 0;
        for(Precinct p:d.getPrecincts().values()){
            dvote+=p.getElectionResult().getVoteData().get(Party.DEMOCRATIC);
            rvote+=p.getElectionResult().getVoteData().get(Party.REPUBLICAN);
        }
        int total = dvote+rvote;
        double winrate = 0.0;
        if(dvote>rvote){
            winrate = ((double)dvote/(double)total-0.5)*2;
        }else{
            winrate = ((double)rvote/(double)total -0.5)*2;
        }
        return 1.0-winrate;
    }
    public double equalPopulationMeasure(District d){
        int dp =d.getDemographic().getTotalPopulation();
        double ap = (double)currentState.getTotalPopulation()/(double)currentState.getPreference().getNumberOfDistrict();
        double difference = Math.abs((double)dp-ap);
        if(difference>ap){
            return 1.0;
        }else{
            return 1-(difference)/ap;
        }
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

    public List<District>getToDistrict(Precinct p) {
        District from = getPrecinctBelongs(p.getPrecinctID());
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
        String[] colors = new String[]{"#ff0000","#0040ff","#ff8000","#00ff00","#ffb3d9","#9900cc","#ffff00","#006600","#00bfff","#00ffbf","#996600"};
        List<District> d = currentState.getDistricts();
        for (int i=0; i<d.size(); i++) {
            if (d.get(i).getColor()==null) {
                d.get(i).setColor(colors[randomIndex(11)]);
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

    public List<Cluster> getClusters() {
        return clusters;
    }

    public List<Cluster> getTempClusters() {
        return tempClusters;
    }

    public void setTempClusters(List<Cluster> tempClusters) {
        this.tempClusters = tempClusters;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public List<ClusterPair> getClusterPairs() {
        return clusterPairs;
    }

    public void setClusterPairs(List<ClusterPair> clusterPairs) {
        this.clusterPairs = clusterPairs;
    }

    public void setPrecinctToDistrict(Map<Long, District> precinctToDistrict) {
        this.precinctToDistrict = precinctToDistrict;
    }

    public List<Map<Long, String>> getPhaseOneChange() {
        return phaseOneChange;
    }

    public void setPhaseOneChange(List<Map<Long, String>> phaseOneChange) {
        this.phaseOneChange = phaseOneChange;
    }
}
