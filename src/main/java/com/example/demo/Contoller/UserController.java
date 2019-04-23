package com.example.demo.Contoller;
import com.example.demo.Entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    // Create User

    @PostMapping(value = "/register",produces = "application/json")
    public User createUser(@RequestBody User user, HttpSession session){
        System.out.println(user.getUserEmail());
        return userRepository.save(user);
    }

    @PostMapping(value = "/login",produces = "application/json")

    public User login(@RequestBody User user, HttpSession session) {
        Optional<User> currentUser = userRepository.findByUserEmail(user.getUserEmail());
        System.out.printf("login request received");
        if(!currentUser.isPresent()){
            return null;
        }else {
            session.setAttribute("currentUser",currentUser);
            return currentUser.get();
        }

    }
    @PostMapping("/logout")
    public String logout(@RequestBody User user, HttpSession session){
        if(session.getAttribute("currentUser")==null){
            return "{status:\"error\"}";
        }else {
            session.setAttribute("currentUser", null);
            return "{status:\"OK\"}";
        }

    }
}
