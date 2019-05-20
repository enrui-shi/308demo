package com.example.demo.Type;

import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Enum.Measurement;
import com.example.demo.Enum.Party;
import com.example.demo.Enum.StateName;

import java.util.Map;

public class Summary {

    private Map<Party, Integer> vote;

    private Map<Measurement, Double> score;

    private Long stateId;

    private StateName stateName;

    private Map<EthnicGroup,Integer>majorityMinorityDistrict;

    private String winner;

    public Map<Party, Integer> getSeatsByParty() {
        return vote;
    }

    public void setSeatsByParty(Map<Party, Integer> seatsByParty) {
        this.vote = seatsByParty;
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
    public String toString(){
        String form = "%d,%d,%d,%.4f,%.3f,%.3f,%.3f,%.3f";
        return String.format(form,stateId,vote.get(Party.REPUBLICAN),vote.get(Party.DEMOCRATIC),score.get(Measurement.TOTAL),
                score.get(Measurement.EQUAL_POPULATION),score.get(Measurement.PARTISAN_FAIRNESS),
                score.get(Measurement.SIMPLE_COMPACTNESS),score.get(Measurement.LENGTH_WIDTH));
    }
}

