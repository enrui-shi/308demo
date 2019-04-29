package com.example.demo.Entity;

import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Type.Bound;
import org.springframework.data.domain.Range;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.OneToOne;
import java.util.List;

@Embeddable
public class Preference {

    private int majorityMinorityDistrictNumber;

    private int numberOfDistrict;

    @OneToOne
    private Bound africanAmericanBound;

    @OneToOne
    private Bound asianBound;

    @OneToOne
    private Bound latinoBound;

    private double efficiencyGapWeight;

    private double compactnessWeight;

    private double partisanFairnessWeight;

    private double equalPopulationWeight;

    private double naturalConstrainWeight;

    public int getNumberOfDistrict() {
        return numberOfDistrict;
    }
}
