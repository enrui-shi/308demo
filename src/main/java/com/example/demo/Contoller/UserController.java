package com.example.demo.Contoller;
import com.example.demo.Entity.User;
import com.example.demo.Type.Status;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    // Create User

    @PostMapping(value = "/register", produces = "application/json")
    public Status createUser(@RequestBody User user, HttpSession session) {

        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getUserEmail());
        userRepository.save(user);
        return new Status("ok");
    }



    @PostMapping(value = "/login", produces = "application/json")
    public Status login(@RequestBody User user, HttpSession session) {
        Optional<User> currentUser = userRepository.findByUserEmail(user.getUserEmail());
            // check if the user exist
        if (!currentUser.isPresent()) {
            return new Status("error");
            // check password
        } else if(encoder.matches(user.getPassword(),currentUser.get().getPassword())) {
            session.setAttribute("currentUser", currentUser);
            return new Status("ok");
        }else{
            return new Status("error");
        }

    }

    @PostMapping("/logout")
    public Status logout(@RequestBody User user, HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            return new Status("error");
        } else {
            session.setAttribute("currentUser", null);
            return new Status("ok");
        }

    }

}


