package com.example.demo.Type;

import javax.persistence.*;

@Entity
public class Bound {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double upperBound;

    private double lowerBound;

    public Bound(){

    }

    public Bound(double upperBound, double lowerBound) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public boolean checkInbound(double value) {
        return value < upperBound && value > lowerBound;
    }

    public double differentFromBound(double value){
        if(checkInbound(value)){
            return 0;
        }else{
            if(value>upperBound){
                return value-upperBound;
            }else{
                return lowerBound-value;
            }
        }
    }
    public boolean better(double old, double n){
        return n<=old;
    }

    public double generateValue() {
        return (Math.random()) * (upperBound - lowerBound) + lowerBound;
    }
}
