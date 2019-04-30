package com.example.demo.Contoller;

import com.example.demo.Entity.State;
import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Enum.StateName;

import com.example.demo.Service.BatchService;
import com.example.demo.Type.Batch;
import com.example.demo.Type.Summary;
import com.example.demo.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/batch")
public class batchController {
    @Autowired
    BatchService batchService;

    @PostMapping(value = "/creatBatch", consumes = "application/json", produces = "application/json")
    public List<Summary> createBatch(@RequestBody Batch batch, HttpSession session) {
        System.out.println("create batch");
        Algorithm algorithm = new Algorithm();
        return algorithm.runBatch(batch, batchService);
    }

}
