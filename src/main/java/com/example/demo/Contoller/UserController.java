package com.example.demo.Contoller;

import com.example.demo.Entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    // Create User

    @PostMapping(value = "/register", produces = "application/json")
    public User createUser(@RequestBody User user, HttpSession session) {

        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getUserEmail());
        userRepository.save(user);
        return userRepository.save(user);
    }


    @PostMapping(value = "/login", produces = "application/json")
    public Map login(@RequestBody User user, HttpSession session) {
        Optional<User> currentUser = userRepository.findByUserEmail(user.getUserEmail());
        Map<String, String> response = new HashMap();
        // check if the user exist
        if (!currentUser.isPresent()) {
            response.put("status", "error");
            response.put("error", "cannot find user");
            return response;
            // check password
        } else if (encoder.matches(user.getPassword(), currentUser.get().getPassword())) {
            session.setAttribute("currentUser", currentUser);
            response.put("status", "ok");
            return response;
        } else {
            response.put("status", "error");
            response.put("error", "wrong password");
            return response;
        }

    }

    @PostMapping("/logout")
    public Map logout(@RequestBody User user, HttpSession session) {
        Map<String, String> response = new HashMap();
        if (session.getAttribute("currentUser") == null) {
            response.put("status", "error");
            response.put("error", "no user login");
            return response;
        } else {
            session.setAttribute("currentUser", null);
            response.put("status", "ok");
            return response;
        }

    }

}


