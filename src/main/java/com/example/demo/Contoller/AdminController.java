package com.example.demo.Contoller;

import com.example.demo.Entity.User;
import com.example.demo.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @PostMapping(value = "/deleteUser", produces = "application/json")
    public Map<String,String> deleteUser(@RequestBody User user){
        Map<String,String> map = new HashMap<>();
        boolean result=adminService.deleteUser(user);
        if(result){
            map.put("status","OK");
        }else{
            map.put("status","error");
        }
        return map;
    }


    @PostMapping(value = "/update", produces = "application/json")
    public Map<String,String> updateUser(@RequestBody User user){
        Map<String,String> map = new HashMap<>();
        boolean result=adminService.changePassword(user);
        if(result){
            map.put("status","OK");
        }else{
            map.put("status","error");
        }
        return map;
    }
}
