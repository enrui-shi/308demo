package com.example.demo.Contoller;

import com.example.demo.Algorithm.Algorithm;

import com.example.demo.Service.BatchService;
import com.example.demo.Type.Batch;
import com.example.demo.Type.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("/runbatch")
public class batchController {
    @Autowired
    BatchService batchService;

    @PostMapping(value = "/creatPiratesBatch", consumes = "application/json", produces = "application/json")
    public Map createBatch(@RequestBody Batch batch, HttpSession session) {
        System.out.println("create batch");
        Algorithm algorithm = new Algorithm();
        List<Summary> s = algorithm.runBatch(batch, batchService);
        Map<Long, String> result = new HashMap<>();
        for(int i=0; i<s.size(); i++) {
            result.put(s.get(i).getStateId(), s.get(i).toString());
        }
        return result;
    }

}
