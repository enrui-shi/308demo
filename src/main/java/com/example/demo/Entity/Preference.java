package com.example.demo.Entity;

import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Type.Bound;
import org.springframework.data.domain.Range;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
public class Preference {

    private int majorityMinorityDistricNumber;

    private int numberOfDistrict;

    @Embedded
    private Bound minorityBound;

    private String ethnicGroup;

    private double efficiencyGapWeight;

    private double compactnessWeight;

    private double partisanFairnessWeight;

    private double equalPopulationWeight;

    private double naturalConstrainWeight;


}
