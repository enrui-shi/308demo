package com.example.demo.Entity;

import com.example.demo.Enum.Party;

import javax.persistence.*;
import java.util.Map;

@Entity
public class ElectionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="EMP_election")
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name="Party")
    @Column(name="data")
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
