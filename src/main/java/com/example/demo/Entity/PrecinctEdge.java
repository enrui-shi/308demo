package com.example.demo.Entity;

import javax.persistence.*;

@Entity
public class PrecinctEdge {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Precinct precinct1;
    @OneToOne
    private Precinct precinct2;

    private double countyJoinability;

    private double natureJoinability;

    private double demographicJoinability;
}
