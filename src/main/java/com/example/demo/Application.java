package com.example.demo;

import com.example.demo.Entity.State;
import com.example.demo.Enum.StateName;
import com.example.demo.temp.Init;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class  Application {
    public static State newJ=new State(StateName.NJ);
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //new Init().init();
    }
}
