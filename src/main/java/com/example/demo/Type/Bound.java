package com.example.demo.Type;

import javax.persistence.*;

@Entity
public class Bound {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private double upperBound;

    private double lowerBound;

    public Bound(double upperBound, double lowerBound) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public boolean checkInbound(double value){
        return value<upperBound &&value>lowerBound;
    }
    public double generateValue(){
        return (Math.random())*(upperBound-lowerBound)+lowerBound;
    }
}
