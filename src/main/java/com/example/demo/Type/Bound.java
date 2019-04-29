package com.example.demo.Type;

import javax.persistence.*;

@Entity
public class Bound {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private double upperBound;

    private double lowerBound;
}
