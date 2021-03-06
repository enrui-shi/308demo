package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();


    public boolean deleteUser(User user){
        Optional<User> currentUser = userRepository.findById(user.getId());
        if(currentUser.isPresent()){
            User cuser = currentUser.get();
            userRepository.delete(cuser);
            return true;
        }else {
            return false;
        }
    }

    public boolean changePassword(User user){
        Optional<User> currentUser = userRepository.findByUserEmail(user.getUserEmail());
        if(currentUser.isPresent()){
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }else {
            return false;
        }
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
