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
    private Map<Party,Integer> voteData;

    @Enumerated(EnumType.STRING)
    private Party winner;


}
