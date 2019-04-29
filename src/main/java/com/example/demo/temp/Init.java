package com.example.demo.temp;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Init {
    public static void init(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/static/data/final_Ohio_precinct.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) jsonObject.get("precincts");
            System.out.println(arr.size());

            JSONObject pre = (JSONObject) arr.get(0);
            System.out.println(pre.get("info"));
            for(int i=0; i<arr.size(); i++){
                JSONObject precinct = (JSONObject) arr.get(i);

            }

            System.out.println((String) jsonObject.get("name"));
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
