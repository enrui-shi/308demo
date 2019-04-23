package com.example.demo.Entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Precinct {
    @Id
    private Long precinctID;

    private ElectionResult lectionResult;

    @OneToMany
    private List<PrecinctEdge> precinctEdges;

    private Demographic demographic;

    private double majMinRation;
}
