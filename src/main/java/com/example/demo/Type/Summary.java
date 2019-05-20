package com.example.demo.Type;

import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Enum.Measurement;
import com.example.demo.Enum.Party;
import com.example.demo.Enum.StateName;

import java.util.Map;

public class



Summary {

    private Map<Party, Integer> seatsByParty;

    private Map<Measurement, Double> score;

    private Long stateId;

    private StateName stateName;

    private Map<EthnicGroup,Integer>majorityMinorityDistrict;

    private String winner;

    public Map<Party, Integer> getSeatsByParty() {
        return seatsByParty;
    }

    public void setSeatsByParty(Map<Party, Integer> seatsByParty) {
        this.seatsByParty = seatsByParty;
    }

    public Map<Measurement, Double> getScore() {
        return score;
    }

    public void setScore(Map<Measurement, Double> score) {
        this.score = score;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public StateName getStateName() {
        return stateName;
    }

    public void setStateName(StateName stateName) {
        this.stateName = stateName;
    }

    public Map<EthnicGroup, Integer> getMajorityMinorityDistrict() {
        return majorityMinorityDistrict;
    }

    public void setMajorityMinorityDistrict(Map<EthnicGroup, Integer> majorityMinorityDistrict) {
        this.majorityMinorityDistrict = majorityMinorityDistrict;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "seatsByParty=" + seatsByParty +
                ", score=" + score +
                ", stateId=" + stateId +
                ", stateName=" + stateName +
                ", majorityMinorityDistrict=" + majorityMinorityDistrict +
                ", winner='" + winner + '\'' +
                '}';
    }
}

