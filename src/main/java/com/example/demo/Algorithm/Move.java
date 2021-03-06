package com.example.demo.Algorithm;

import com.example.demo.Entity.District;
import com.example.demo.Entity.Precinct;
import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Entity.Preference;
import com.example.demo.Enum.Measurement;
import com.example.demo.Type.Bound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Move {
    private District from;
    private District to;
    private Precinct precinct;
    private Map<Measurement,Double> changedToScore;
    private Map<Measurement,Double>changedFromScore;

    public Move(District from, District to, Precinct precinct) {
        this.from = from;
        this.to = to;
        this.precinct = precinct;
    }

    public District getFrom() {
        return from;
    }

    public District getTo() {
        return to;
    }

    public Precinct getPrecinct() {
        return precinct;
    }



    /*
        true for accept, false for reject
     */
    public boolean checkMajorityMinority(Preference p) {
        Map<EthnicGroup, Bound> groupBound = p.getEthnicGroupBound();
        return from.checkMinorityBound(groupBound, precinct) && to.checkMinorityBound(groupBound, precinct);
    }
    public void undo(){
        from.addPrecinct(precinct);
        to.removePrecinct(precinct);
        from.addUsedPrecinct(precinct);
    }
    public void tryMove(){
        from.removePrecinct(precinct);
        to.addPrecinct(precinct);
        to.addUsedPrecinct(precinct);
    }

    public void execute(){
        from.removePrecinct(precinct);
        to.addPrecinct(precinct);
        from.setScore(changedFromScore);
        to.setScore(changedToScore);
        to.addUsedPrecinct(precinct);
    }

    public void setChangedToScore(Map<Measurement, Double> changedToScore) {
        this.changedToScore = changedToScore;
    }

    public void setChangedFromScore(Map<Measurement, Double> changedFromScore) {
        this.changedFromScore = changedFromScore;
    }
    public double getFromTotalScore(){
        return changedFromScore.get(Measurement.TOTAL);
    }
    public double getToTotalScore(){
        return changedToScore.get(Measurement.TOTAL);
    }

    @Override
    public String toString(){
        return "Move["+precinct.getPrecinctID()+" from "+from.getDistrictId()+" to "+to.getDistrictId()+"]";
    }
    public boolean checkContiguity(){
        return from.checkContiguity()&&to.checkContiguity();
    }

}
