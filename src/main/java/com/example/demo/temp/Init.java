package com.example.demo.temp;


import com.example.demo.Entity.Demographic;
import com.example.demo.Entity.ElectionResult;
import com.example.demo.Entity.Precinct;
import com.example.demo.Enum.EthnicGroup;
import com.example.demo.Enum.Party;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Init {
    public static void init(){
        JSONParser parser = new JSONParser();
        ArrayList<Precinct> precincts = new ArrayList<>();
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/static/data/final_Ohio_precinct.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) jsonObject.get("precincts");
            System.out.println(arr.size());

            JSONObject pre = (JSONObject) arr.get(0);
            System.out.println(pre.get("info"));

            String a=(String)((JSONObject)pre.get("info")).get("id");
            Long aa=Long.parseLong(a);
            System.out.println(aa);
            int count=0;
            for(int i=0; i<arr.size(); i++){
                JSONObject precinctData = (JSONObject) arr.get(i);
                Precinct precinct = CreatePrecinct(precinctData);
                precincts.add(precinct);
                System.out.print(count++);
            }

            System.out.println((String) jsonObject.get("name"));
        }catch (Exception e){
            System.out.println(e);
        }
    }
    // create a precinct using json
    static Precinct CreatePrecinct(JSONObject p){
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
    static Demographic CreateDemographic(JSONObject demo){
        Demographic demographic=new Demographic();
        //setMap
        Map<EthnicGroup,Integer> ethnicData = new EnumMap<EthnicGroup, Integer>(EthnicGroup.class);
        ethnicData.put(EthnicGroup.AFRIAN_AMERICAN, (Integer) demo.get("AfricanAmerican"));
        ethnicData.put(EthnicGroup.ASIAN_PACIFIC, (Integer) demo.get("Asian"));
        ethnicData.put(EthnicGroup.LATINO, (Integer) demo.get("Hispanic"));
        ethnicData.put(EthnicGroup.WHITE, (Integer) demo.get("white"));
        //set data fro demographic
        demographic.setEthnicData(ethnicData);
        demographic.setTotalPopulation((int) demo.get("total"));
        return demographic;
    }

    // create a electionResult using json
    static ElectionResult CreateElectionResult(JSONObject vote){
        ElectionResult electionResult = new ElectionResult();
        //setup Map
        Map<Party,Integer> voteDate = new EnumMap<Party, Integer>(Party.class);
        voteDate.put(Party.DEMOCRATIC,(Integer) vote.get("Democratic"));
        voteDate.put(Party.REPUBLICAN,(Integer) vote.get("Republican"));
        // set data for electionResult
        electionResult.setVoteData(voteDate);
        if((int)vote.get("democratic")>=(int)vote.get("repulican")){
            electionResult.setWinner(Party.DEMOCRATIC);
        }else{
            electionResult.setWinner(Party.REPUBLICAN);
        }
        return electionResult;

    }
}
