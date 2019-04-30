package com.example.demo.Entity;

import com.example.demo.Enum.Party;

import javax.persistence.*;
import java.util.Map;

@Embeddable
public class ElectionResult {

    @ElementCollection
    @CollectionTable()
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn()
    private Map<Party, Integer> voteData;

    @Enumerated(EnumType.STRING)
    private Party winner;

    public Map<Party, Integer> getVoteData() {
        return voteData;
    }

    public void setVoteData(Map<Party, Integer> voteData) {
        this.voteData = voteData;
    }

    public Party getWinner() {
        return winner;
    }

    public void setWinner(Party winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "ElectionResult{" +
                "voteData=" + voteData.toString() +
                ", winner=" + winner +
                '}';
    }
}
