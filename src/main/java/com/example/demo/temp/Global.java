package com.example.demo.temp;

import com.example.demo.Entity.*;
import com.example.demo.Enum.StateName;
import com.example.demo.Service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Global implements CommandLineRunner {

    @Autowired
    private InitService initService;

    public void run(String args[]){
        System.out.println("start init NJ precinct");
        List<Precinct> njP = initService.getPrecinctsByState(StateName.NJ);
        System.out.println("finish");
        System.out.println(njP.size());
        System.out.println("start init Nj edge");
        List<PrecinctEdge> njE = initService.getPrecinctEdgeByState(StateName.NJ);
        System.out.println("finish");
        System.out.println(njE.size());
    }


}
