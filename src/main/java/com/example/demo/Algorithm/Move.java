package com.example.demo.Algorithm;

import com.example.demo.Entity.District;
import com.example.demo.Entity.Precinct;
import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Entity.Preference;
import com.example.demo.Type.Bound;

import java.util.Map;

public class Move {
    private District from;
    private District to;
    private Precinct precinct;

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

    public void execute(){

    }

}
