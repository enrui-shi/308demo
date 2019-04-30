package com.example.demo.Contoller;
import com.example.demo.Entity.State;
import com.example.demo.Entity.Preference;
import com.example.demo.Algorithm.Algorithm;
import com.example.demo.Enum.StateName;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/home")
public class mainController {

    @PostMapping(value = "/main/startAlgorithm", consumes = "application/json", produces = "application/json")
    public State createState(@RequestParam String stateName){
        System.out.println("lalalal");
        StateName stateName1 = StateName.valueOf(stateName);
        State state = new State(stateName1);
        System.out.println("Number of district in State " + state.getPreference().getNumberOfDistrict());
        return state;
    }

    /*@PostMapping(value = "/batch", consumes = "application/json", produces = "application/json")
    public List<State> createStateList(@RequestBody State state){

        stateRepository.save(state);
        return null;
    }*/
}
