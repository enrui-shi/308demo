package com.example.demo.Service;

import com.example.demo.Entity.District;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.example.demo.Enum.EthnicGroup.AFRIAN_AMERICAN;

@Service
public class PhaseTwoService {

    // show african-american population distribution
    public Map showDemoAA(Long c_ID, Map<Long, District> pToD) {
        Map<String, String> response = new HashMap<>();
        response.put("AFRIAN_AMERICAN", pToD.get(c_ID).getDemographic().getRatioByGroup(AFRIAN_AMERICAN) + "");
        return response;
    }


}
