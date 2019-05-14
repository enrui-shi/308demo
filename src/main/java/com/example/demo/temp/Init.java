package com.example.demo.temp;


import com.example.demo.Entity.Demographic;
import com.example.demo.Entity.ElectionResult;
import com.example.demo.Entity.Precinct;
import com.example.demo.Entity.PrecinctEdge;
import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Enum.Party;
import com.example.demo.Enum.StateName;
import com.example.demo.Service.PrecinctEdgeService;
import com.example.demo.Service.PrecinctService;
import com.example.demo.repository.PrecinctRepository;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Component
public class Init implements CommandLineRunner {
    @Autowired
    private PrecinctService precinctServices;

    @Autowired
    private PrecinctEdgeService precinctEdgeService;

    public void run(String args[]) {
        JSONParser parser = new JSONParser();
        Map<Long, Precinct> precincts = new HashMap<>();
        ArrayList<PrecinctEdge> precinctEdges = new ArrayList<>();
        //load precinct to precincts
        try {
            Object p_obj = parser.parse(new FileReader("src/main/resources/static/data/final_Ohio_precinct.json"));
            JSONObject jsonObject = (JSONObject) p_obj;
            JSONArray p_arr = (JSONArray) jsonObject.get("precincts");
            for (int i = 0; i < p_arr.size(); i++) {
                JSONObject precinctData = (JSONObject) p_arr.get(i);
                Precinct precinct = CreatePrecinct(precinctData);
                precinct.setStateName(StateName.OH);
                precinct.setNeighbourPrecincts(new ArrayList<Long>());
                precincts.put(precinct.getPrecinctID(), precinct);
            }
            System.out.println(precincts.size());
        } catch (Exception e) {
            System.out.println(e);
        }
        //load edge
        try {
            Object e_obj = parser.parse(new FileReader("src/main/resources/static/data/ohio_edge_final.json"));
            JSONObject jsonObject = (JSONObject) e_obj;
            JSONArray e_arr = (JSONArray) jsonObject.get("edges");
            int count = 0;
            for (int i = 0; i < e_arr.size(); i++) {
                JSONObject edge = (JSONObject) e_arr.get(i);
                Long p1 = (Long) edge.get("precinct1");
                Long p2 = (Long) edge.get("precinct2");
                Precinct precinct1 = precincts.get(p1);
                Precinct precinct2 = precincts.get(p2);
                PrecinctEdge precinctEdge = CreatePrecinctEdge(edge, precinct1, precinct2);
                precinctEdges.add(precinctEdge);
                if (precinct1.getPrecinctEdges() == null) {
                    precinct1.setPrecinctEdges(new ArrayList<>());
                }
                if (precinct2.getPrecinctEdges() == null) {
                    precinct2.setPrecinctEdges(new ArrayList<>());
                }
                precinct1.getPrecinctEdges().add(precinctEdge);
                precinct2.getPrecinctEdges().add(precinctEdge);
                precinct1.getNeighbourPrecincts().add(p2);
                precinct2.getNeighbourPrecincts().add(p1);
            }
            System.out.println(count);

        } catch (Exception e) {
            System.out.println(e);
        }
        int count = 0;
        for(PrecinctEdge precinctEdge: precinctEdges){
           // precinctEdgeService.addPrecinctEdge(precinctEdge);
            System.out.println(count++);
        }
        count=0;
        for(Precinct p:precincts.values()){
            if(p.getPrecinctEdges()==null){

            }else {
                System.out.println(p.getPrecinctID());
                for(PrecinctEdge pe:p.getPrecinctEdges()){
                    System.out.println("edge: "+pe.getPrecinct1()+" "+pe.getPrecinct2());
                }
                System.out.println(p);
                //precinctServices.addPrecinct(p);

                //System.out.println(count++);
            }
        }

    }

    // create a precinct using json
    static Precinct CreatePrecinct(JSONObject p) {
        Precinct precinct = new Precinct();
        JSONObject info = (JSONObject) p.get("info");
        JSONObject demo = (JSONObject) p.get("demo");
        JSONObject vote = (JSONObject) p.get("vote");
        //set the value of precinct
        precinct.setPrecinctID(Long.parseLong((String) info.get("id")));
        precinct.setCounty((String) info.get("county"));
        precinct.setDemographic(CreateDemographic(demo));
        precinct.setElectionResult(CreateElectionResult(vote));
        return precinct;
    }

    // create a demographic using json
    static Demographic CreateDemographic(JSONObject demo) {
        Demographic demographic = new Demographic();
        //setMap
        Map<EthnicGroup, Integer> ethnicData = new EnumMap<EthnicGroup, Integer>(EthnicGroup.class);
        ethnicData.put(EthnicGroup.AFRIAN_AMERICAN, ((Long) demo.get("AfricanAmerican")).intValue());
        ethnicData.put(EthnicGroup.ASIAN_PACIFIC, ((Long) demo.get("Asian")).intValue());
        ethnicData.put(EthnicGroup.LATINO, ((Long) demo.get("Hispanic")).intValue());
        ethnicData.put(EthnicGroup.WHITE, ((Long) demo.get("white")).intValue());
        //set data fro demographic
        demographic.setEthnicData(ethnicData);
        demographic.setTotalPopulation(((Long) demo.get("total")).intValue());
        return demographic;
    }

    // create a electionResult using json
    static ElectionResult CreateElectionResult(JSONObject vote) {
        ElectionResult electionResult = new ElectionResult();
        //setup Map
        Map<Party, Integer> voteDate = new EnumMap<Party, Integer>(Party.class);
        voteDate.put(Party.DEMOCRATIC, ((Long) vote.get("Democratic")).intValue());
        voteDate.put(Party.REPUBLICAN, ((Long) vote.get("Republican")).intValue());
        // set data for electionResult
        electionResult.setVoteData(voteDate);
        if (((Long) vote.get("Democratic")).intValue() >= ((Long) vote.get("Republican")).intValue()) {
            electionResult.setWinner(Party.DEMOCRATIC);
        } else {
            electionResult.setWinner(Party.REPUBLICAN);
        }
        return electionResult;

    }


    static PrecinctEdge CreatePrecinctEdge(JSONObject edge, Precinct p1, Precinct p2) {

        PrecinctEdge precinctEdge = new PrecinctEdge();
        precinctEdge.setPrecinct1(p1.getPrecinctID());
        precinctEdge.setPrecinct2(p2.getPrecinctID());
        if ((boolean) edge.get("county")) {
            precinctEdge.setCountyJoinability(1);
        } else {
            precinctEdge.setCountyJoinability(0);
        }
        precinctEdge.setNatureJoinability(1);
        precinctEdge.setDemographicJoinability(CalDemoJoinability(p1, p2));
        return precinctEdge;
    }

    static double CalDemoJoinability(Precinct p1, Precinct p2) {
        double p1_total = p1.getDemographic().getTotalPopulation();
        Map<EthnicGroup, Integer> p1_data = p1.getDemographic().getEthnicData();
        double p2_total = p2.getDemographic().getTotalPopulation();
        Map<EthnicGroup, Integer> p2_data = p2.getDemographic().getEthnicData();
        if (p1_total == 0 || p2_total == 0) {
            return 0;
        } else {
            double aa_differ = Math.abs((1.0*p1_data.get(EthnicGroup.AFRIAN_AMERICAN) / p1_total) - (1.0*p2_data.get(EthnicGroup.AFRIAN_AMERICAN) / p2_total));
            double white_differ = Math.abs((1.0*p1_data.get(EthnicGroup.WHITE) / p1_total) - (1.0*p2_data.get(EthnicGroup.WHITE) / p2_total));
            double l_differ = Math.abs((1.0*p1_data.get(EthnicGroup.LATINO) / p1_total) - (1.0*p2_data.get(EthnicGroup.LATINO) / p2_total));
            double asia_differ = Math.abs((1.0*p1_data.get(EthnicGroup.ASIAN_PACIFIC) / p1_total) - (1.0*p2_data.get(EthnicGroup.ASIAN_PACIFIC) / p2_total));
            return 1.0 - (aa_differ + white_differ + l_differ + asia_differ) / 4;
        }
    }
}
