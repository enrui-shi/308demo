package com.example.demo.Service;

import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Entity.*;
import com.example.demo.Enum.StateName;
import com.example.demo.Enum.*;

import com.example.demo.repository.PrecinctEdgeRepository;
import com.example.demo.repository.PrecinctRepository;
import com.example.demo.temp.GVAL;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.Enum.EthnicGroup.*;

@Service
public class PhaseOneService {
    @Autowired
    PrecinctRepository precinctRepository;
    @Autowired
    PrecinctEdgeRepository precinctEdgeRepository;

    public Algorithm createAlgorithm(StateName stateName, Preference preference) {
        State state = new State(stateName);

        state.setPreference(preference);

        ObjectMapper objectMapper = new ObjectMapper();
        Algorithm algorithm = new Algorithm(state);
        try{
        if(stateName == stateName.OH) {
            System.out.println("OH DATA LOAD");
            //algorithm.setClusterEdges(objectMapper.readValue(objectMapper.writeValueAsString(GVAL.ohe), new TypeReference<List<ClusterEdge>>(){}));
            //algorithm.setClusters(objectMapper.readValue(objectMapper.writeValueAsString(GVAL.oh), new TypeReference<List<Cluster>>(){}));
            Map<Long,Cluster> oh_t = cloneCluster(GVAL.oh);
            List<ClusterEdge> ohe_t = cloneClusterEdge(GVAL.ohe,oh_t);
            List<Cluster> oh_l = new ArrayList<>(oh_t.values());
            algorithm.setClusterEdges(ohe_t);
            algorithm.setClusters(oh_l);
            System.out.println(algorithm.getClusterEdges().get(0));
        } else if(stateName == stateName.NY) {
            System.out.println("NY DATA LOAD");
            Map<Long,Cluster> ny_t = cloneCluster(GVAL.ny);
            List<ClusterEdge> nye_t = cloneClusterEdge(GVAL.nye,ny_t);
            List<Cluster> ny_l = new ArrayList<>(ny_t.values());
            algorithm.setClusterEdges(nye_t);
            algorithm.setClusters(ny_l);
        } else {
            System.out.println("IA DATA LOAD");
            Map<Long,Cluster> ia_t = cloneCluster(GVAL.ia);
            List<ClusterEdge> iae_t = cloneClusterEdge(GVAL.iae,ia_t);
            List<Cluster> ia_l = new ArrayList<>(ia_t.values());
            System.out.println(ia_t.size());
            System.out.println(iae_t.size());
            algorithm.setClusterEdges(iae_t);
            algorithm.setClusters(ia_l);
        }
        }catch (Exception e){
            System.out.println("error!!!!!!!!!!!!!");
            System.out.println(e);
        }
        algorithm.startGraphPartition();

        algorithm.setColor();

        return algorithm;
    }



    public Map returnPhaseOne(Algorithm algorithm) throws IOException {
        Map<Long, District> precinctToDistrict = algorithm.getPrecinctToDistrict();
        Map<String, String> result = new HashMap();
        // map precinct Id to district-color
        for (Map.Entry<Long, District> entry : precinctToDistrict.entrySet()) {
            result.put(Long.toString(entry.getKey()), entry.getValue().getColor());
        }
        System.out.println("phase one finished and start to color");
        return result;
    }

    public Map showDemo(Long p_ID) {
        Optional<Precinct> p = precinctRepository.findById(p_ID);
        Map<String, String> response = new HashMap<>();
        if(p.isPresent()){
            Precinct precinct = p.get();
            System.out.println(precinct.getDemographic());
            response.put("Demographic", "found");
            response.put("totalPopulation", precinct.getDemographic().getTotalPopulation()+"");
            response.put("ASIAN_PACIFIC", precinct.getDemographic().getEthnicData().get(EthnicGroup.ASIAN_PACIFIC)+"");
            response.put("LATINO", precinct.getDemographic().getEthnicData().get(LATINO)+"");
            response.put("WHITE", precinct.getDemographic().getEthnicData().get(WHITE)+"");
            response.put("AFRIAN_AMERICAN", precinct.getDemographic().getEthnicData().get(AFRIAN_AMERICAN)+"");
            return response;
        } else {
            System.out.println("Can't find precinct");
            response.put("Demographic", "Undefined");
            return response;
        }


    }

    public Map showDemoAfterPlay(Long c_ID, Map<Long, District> pToD) {
        Map<String, String> response = new HashMap<>();

        if (pToD.size() == 0) {
            response.put("Demographic", "Undefined");
            return response;
        } else {
            response.put("Demographic", "found");
            response.put("id", Long.toString(pToD.get(c_ID).getDistrictId()));
            response.put("totalPopulation", pToD.get(c_ID).getDemographic().getTotalPopulation() + "");
            response.put("ASIAN_PACIFIC", pToD.get(c_ID).getDemographic().getEthnicData().get(EthnicGroup.ASIAN_PACIFIC) + "");
            response.put("LATINO", pToD.get(c_ID).getDemographic().getEthnicData().get(LATINO) + "");
            response.put("WHITE", pToD.get(c_ID).getDemographic().getEthnicData().get(WHITE) + "");
            response.put("AFRIAN_AMERICAN", pToD.get(c_ID).getDemographic().getEthnicData().get(AFRIAN_AMERICAN) + "");
            return response;
        }
    }



    public static List<ClusterEdge> cloneClusterEdge(List<ClusterEdge> clusterEdges,Map<Long,Cluster> cmap) {
        List<ClusterEdge> copy = new ArrayList<>();
        for(ClusterEdge ce:clusterEdges){
            ClusterEdge tce = new ClusterEdge();
            tce.setNatureJoinability(ce.getNatureJoinability());
            tce.setDemographicJoinability(ce.getDemographicJoinability());
            tce.setCountyJoinability(ce.getCountyJoinability());
            tce.setStateName(ce.getStateName());
            tce.setCluster1(cmap.get(ce.getCluster1().getId()));
            tce.setCluster2(cmap.get(ce.getCluster2().getId()));
            copy.add(tce);
        }

        return copy;
    }

    public static Map<Long,Cluster> cloneCluster(List<Cluster> clusters) {
        Map<Long,Cluster> copy= new HashMap<>();
        for (Cluster c:clusters){
            Cluster t = new Cluster();
            t.setId(c.getId());
            t.setStateName(c.getStateName());
            t.setPaired(c.isPaired());
            Demographic dt =new Demographic();
            dt.setTotalPopulation(c.getDemographic().getTotalPopulation());
            Map<EthnicGroup, Integer> tmap = new EnumMap<EthnicGroup, Integer>(EthnicGroup.class);
            for(EthnicGroup eg: c.getDemographic().getEthnicData().keySet()){
                tmap.put(eg,c.getDemographic().getEthnicData().get(eg));
            }
            dt.setEthnicData(tmap);
            t.setDemographic(dt);
            List<Precinct> tp = new ArrayList<>();
            tp.add(c.getPrecincts().get(0));
            t.setPrecincts(tp);
            copy.put(t.getId(),t);
        }

        return copy;
    }

}
