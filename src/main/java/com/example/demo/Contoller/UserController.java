package com.example.demo.Contoller;
import com.example.demo.Entity.User;
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
    public User createUser(@RequestBody User user, HttpSession session) {

        CharSequence sequence = user.getPassword();
        user.setPassword(encoder.encode(sequence));
        System.out.println(user.getUserEmail());
        return userRepository.save(user);
    }



    @PostMapping(value = "/login", produces = "application/json")
    public User login(@RequestBody User user, HttpSession session) {
        Optional<User> currentUser = userRepository.findByUserEmail(user.getUserEmail());
        if (!currentUser.isPresent()) {
            return null;
        } else if(encoder.matches(user.getPassword(),currentUser.get().getPassword())) {
            session.setAttribute("currentUser", currentUser);
            return currentUser.get();
        }else{
            return null;
        }

    }

    @PostMapping("/logout")
    public String logout(@RequestBody User user, HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            return "{status:\"error\"}";
        } else {
            session.setAttribute("currentUser", null);
            return "{status:\"OK\"}";
        }

    }

}


