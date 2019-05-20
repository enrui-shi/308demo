package com.example.demo.Entity;

import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Enum.Measurement;
import com.example.demo.Enum.Party;
import com.example.demo.Type.Bound;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.*;

@Entity
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long districtId;

    @OneToMany
    private Map<Long, Precinct> precincts ;

    @OneToOne
    private Demographic demographic;

    @OneToMany
    private List<District> neighborDistrict;

    @ElementCollection
    private List<Long>usedPrecincts;

    private boolean minorityTarget;

    private boolean minorityDistrict;

    @ElementCollection
    @CollectionTable()
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn()
    private Map<Measurement,Double>measureScore;


    private String color;

    @Enumerated(EnumType.STRING)
    private EthnicGroup targetEthnic;

    public List<District> getNeighborDistrict() {
        return neighborDistrict;
    }

    public void setNeighborDistrict(List<District> neighborDistrict) {
        this.neighborDistrict = neighborDistrict;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void addNeighborDistrict(District d) {
        this.neighborDistrict.add(d);
    }

    public void setMinorityTarget(boolean minorityTarget) {
        this.minorityTarget = minorityTarget;
    }

    public void setTargetEthnic(EthnicGroup targetEthnic) {
        this.targetEthnic = targetEthnic;
    }

    public Map<Long, Precinct> getPrecincts() {
        return precincts;
    }

    public boolean isMinorityDistrict() {
        return minorityDistrict;
    }

    public EthnicGroup getTargetEthnic() {
        return targetEthnic;
    }
    public Party getWinner(){
        Map<Party, Integer>vote = new HashMap<>();
        vote.put(Party.REPUBLICAN,0);
        vote.put(Party.DEMOCRATIC,0);
        for(Precinct p:precincts.values()){
            vote.forEach((k,v)->v+=p.getElectionResult().getVoteData().get(k));
        }
        if(vote.get(Party.REPUBLICAN)>vote.get(Party.DEMOCRATIC)){
            return Party.REPUBLICAN;
        }
        return Party.DEMOCRATIC;

    }
    public Map<Measurement, Double> getScore() {
        return measureScore;
    }

    public void setScore(Map<Measurement, Double> score) {
        this.measureScore = score;
    }

    public void setMeasureScore(Map<Measurement, Double> measureScore) {
        this.measureScore = measureScore;
    }

    public double getTotalScore(){
        return measureScore.get(Measurement.TOTAL);
    }

    public Demographic getDemographic() {
        return demographic;
    }

    public Map<Measurement, Double> getMeasureScore() {
        return measureScore;
    }

    public District(List<Precinct> precincts, Demographic demographic, Long districtId) {
        this.precincts = new HashMap<>();
        neighborDistrict = new ArrayList<>();
        usedPrecincts = new ArrayList<>();
        for (Precinct p : precincts) {
            this.precincts.put(p.getPrecinctID(), p);
        }
        this.demographic = demographic;
        this.districtId = districtId;
        this.neighborDistrict = new ArrayList<>();
    }

    public void removePrecinct(Precinct p){
        this.demographic.removeDemo(p.getDemographic());
        precincts.remove(p.getPrecinctID());

    }

    public void addPrecinct(Precinct p){
        this.demographic.merge(p.getDemographic());
        precincts.put(p.getPrecinctID(),p);
    }

    public boolean checkMinorityBound(Map<EthnicGroup, Bound> groupBound, Precinct p) {
        if (!this.minorityTarget) {
            return true;
        } else {
            Bound ethnicBound = groupBound.get(targetEthnic);
            if (precincts.containsValue(p)) {
                int total = demographic.getTotalPopulation() - p.getDemographic().getTotalPopulation();
                int group = demographic.getNumberByGroup(targetEthnic) - p.getDemographic().getNumberByGroup(targetEthnic);
                return ethnicBound.checkInbound((double) group / total);
            } else {
                int total = demographic.getTotalPopulation() + p.getDemographic().getTotalPopulation();
                int group = demographic.getNumberByGroup(targetEthnic) + p.getDemographic().getNumberByGroup(targetEthnic);
                return ethnicBound.checkInbound((double) group / total);
            }
        }
    }

    public List<Precinct> getBoundPrecinct(){
        List<Precinct>bound = new ArrayList<>();
        for(Precinct p :precincts.values()){
            //System.out.println(p);
            if(!this.isInnerPrecinct(p)){
                bound.add(p);
            }
        }
        return bound;
    }
    public Precinct getCandidatePrecinct(){
        List<Precinct> bp = getBoundPrecinct();
        for(Precinct p:bp){
            if(!usedPrecincts.contains(p.getPrecinctID())){
                return p;
            }
        }
        return null;
    }

    public void addUsedPrecinct(Precinct p){
        this.usedPrecincts.add(p.getPrecinctID());
    }
    public boolean isInnerPrecinct(Precinct pct){
        for( Long p:pct.getNeighbourPrecincts()){
            if(!precincts.containsKey(p)){
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return "{\"district\": {" +
                "\"districtID\":" + districtId +
                ", \"d_color\":" + color +
                ", " + precinctString() +
                "," + demographic.toString() +
                "}}";
    }

    public boolean checkContiguity(){
        List<Precinct>used = new ArrayList<>();
        List<Precinct>queue = new ArrayList<>();
        queue.add(getRandomPrecinct());
        while(queue.size()!= 0){
            Precinct p = queue.remove(0);
            for(Long pID:p.getNeighbourPrecincts()){
                if(precincts.get(pID)!= null &&!used.contains(precincts.get(pID))&&!queue.contains(precincts.get(pID))){
                    queue.add(precincts.get(pID));
                }
            }
            used.add(p);
        }
        return used.size()== precincts.size();
    }

    public Precinct getRandomPrecinct(){
        for(Precinct p:precincts.values()){
            return p;
        }
        return null;
    }

    public String precinctString() {
        String str = "\"precincts\":[";
        for (Map.Entry<Long, Precinct> entry : precincts.entrySet()) {
            str += "{ \"precinctID\" : \"" + entry.getKey() + "\"} , ";
        }
        str.substring(0, (str.length()-1)); // delete the extra ","
        str += "]";
        return str;

    }
    public double getWidth(){
        double minX = 0;
        double maxX = -500;
        for(Precinct p:precincts.values()){
            if(p.getMinX()<minX){
                minX = p.getMinX();
            }
            if(p.getMinX()>maxX);
                maxX = p.getMaxX();
        }
        return maxX-minX;
    }
    public double getLength(){
        double minY = 500;
        double maxY = 0;
        for(Precinct p:precincts.values()){
            if(p.getMinY()<minY){
                minY= p.getMinY();
            }
            if(p.getMaxY()>maxY){
                maxY = p.getMaxY();
            }
        }
        return maxY -minY;
    }


}
