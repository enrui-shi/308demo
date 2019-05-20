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

    public double generateValue() {
        return (Math.random()) * (upperBound - lowerBound) + lowerBound;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }

    @Override
    public String toString() {
        return "Bound{" +
                "upperBound=" + upperBound +
                ", lowerBound=" + lowerBound +
                '}';
    }
}
