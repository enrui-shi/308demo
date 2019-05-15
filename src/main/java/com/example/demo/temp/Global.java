package com.example.demo.temp;

import com.example.demo.Entity.*;
import com.example.demo.Enum.StateName;
import com.example.demo.Service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
public class Global implements CommandLineRunner {

    @Autowired
    private InitService initService;

    public void run(String args[]){
        System.out.println("start init OH precinct");
        List<Precinct> njP = initService.getPrecinctsByState(StateName.NJ);
        System.out.println("finish");
        System.out.println(njP.size());
        System.out.println("start init OH edge");
        List<PrecinctEdge> njE = initService.getPrecinctEdgeByState(StateName.NJ);
        System.out.println("finish");
        System.out.println(njE.size());
        System.out.println("Starting convert precinct to cluster");
        Map<Long,Cluster> cmap= new HashMap<>();
        for(Precinct p:njP){
            Cluster c=new Cluster();
            c.setId(p.getPrecinctID());
            List<Precinct> cPs = new ArrayList<Precinct>();
            cPs.add(p);
            c.setPrecincts(cPs);
            c.setDemographic(p.getDemographic());
            c.setStateName(p.getStateName());
            c.setPaired(false);
            cmap.put(p.getPrecinctID(),c);
        }
        GVAL.oh = new ArrayList<>(cmap.values());
        System.out.println("finish convert precinct");
        System.out.println("start convert edge");
        GVAL.ohe = new ArrayList<ClusterEdge>();
        for(PrecinctEdge pe:njE){
            ClusterEdge ce=new ClusterEdge();
            ce.setCluster1(cmap.get(pe.getPrecinct1()));
            ce.setCluster2(cmap.get(pe.getPrecinct2()));
            ce.setCountyJoinability(pe.getCountyJoinability());
            ce.setDemographicJoinability(pe.getDemographicJoinability());
            ce.setNatureJoinability(pe.getNatureJoinability());
            GVAL.nje.add(ce);
        }
        System.out.println("finish convert edge");


        //check
        System.out.println(GVAL.oh.size());
        System.out.println(GVAL.ohe.size());
        System.out.println(GVAL.oh.get(0));
        System.out.println(GVAL.ohe.get(0));
    }


}
