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

@Component
public class Global implements CommandLineRunner {

    @Autowired
    private InitService initService;

    public void run(String args[]){
        System.out.println("start init precinct");
        //List<Precinct> ohP = initService.getPrecinctsByState(StateName.OH);
        List<Precinct> iaP = initService.getPrecinctsByState(StateName.IA);

        /*for(Precinct p:iaP){
            System.out.println(p.getNeighbourPrecincts());
        }*/

        //List<Precinct> nyP = initService.getPrecinctsByState(StateName.NY);
        System.out.println("finish");
        //System.out.println("OH precinct size:"+ohP.size());
        System.out.println("IA precinct size:"+iaP.size());
        //System.out.println("NY precinct size:"+nyP.size());

        System.out.println("start init edge");
        //List<PrecinctEdge> ohE = initService.getPrecinctEdgeByState(StateName.OH);
        List<PrecinctEdge> iaE = initService.getPrecinctEdgeByState(StateName.IA);
        //List<PrecinctEdge> nyE = initService.getPrecinctEdgeByState(StateName.NY);
        System.out.println("finish");
        //System.out.println("OH edge size: "+ohE.size());
        System.out.println("IA edge size: "+iaE.size());
        //System.out.println("NY edge size: "+nyE.size());

        System.out.println("Starting convert precinct to cluster");
        //Map<Long,Cluster> oh_map= addData(ohP);
        Map<Long,Cluster> ia_map= addData(iaP);
        //Map<Long,Cluster> ny_map= addData(nyP);
        // add precinct ot global
        //GVAL.oh = new ArrayList<>(oh_map.values());
        GVAL.ia = new ArrayList<>(ia_map.values());
        //GVAL.ny = new ArrayList<>(ny_map.values());
        System.out.println("finish convert precinct");
        System.out.println("start convert edge");
        //GVAL.ohe = addEdge(ohE,oh_map);
        GVAL.iae = addEdge(iaE,ia_map);
        //GVAL.nye = addEdge(nyE,ny_map);

        System.out.println("finish convert edge");


        //check
        //System.out.println("OH: "+ GVAL.oh.size());
        //System.out.println("OH edge: "+ GVAL.ohe.size());
        System.out.println("IA: "+ GVAL.ia.size());
        System.out.println("IA edge: "+ GVAL.iae.size());
        //System.out.println("NY: " + GVAL.ny.size());
        //System.out.println("NY edge: " + GVAL.nye.size());

        //System.out.println(GVAL.oh.get(0));
        //System.out.println(GVAL.ohe.get(0));
        System.out.println(GVAL.ia.get(0));
        System.out.println(GVAL.iae.get(0));
        //System.out.println(GVAL.ny.get(0));
        //System.out.println(GVAL.nye.get(0));
    }
    public Map<Long,Cluster> addData(List<Precinct> lp){
        Map<Long,Cluster> map = new HashMap<>();
        for(Precinct p:lp){
            Cluster c=new Cluster();
            c.setId(p.getPrecinctID());
            List<Precinct> cPs = new ArrayList<Precinct>();
            cPs.add(p);
            c.setPrecincts(cPs);
            c.setDemographic(p.getDemographic());
            c.setStateName(p.getStateName());
            c.setPaired(false);
            map.put(p.getPrecinctID(),c);
        }
        return map;
    }
    public ArrayList<ClusterEdge> addEdge(List<PrecinctEdge> pe,Map<Long,Cluster> map){
        ArrayList<ClusterEdge> list = new ArrayList<>();
        for(PrecinctEdge e:pe){
            ClusterEdge ce=new ClusterEdge();
            ce.setCluster1(map.get(e.getPrecinct1()));
            ce.setCluster2(map.get(e.getPrecinct2()));
            ce.setCountyJoinability(e.getCountyJoinability());
            ce.setDemographicJoinability(e.getDemographicJoinability());
            ce.setNatureJoinability(e.getNatureJoinability());
            list.add(ce);
        }
        return list;
    }
}
