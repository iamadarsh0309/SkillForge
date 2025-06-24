package com.skillforge.skillforge.controller;

import com.skillforge.skillforge.model.User;
import com.skillforge.skillforge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable  String id){
        User user =  userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        return  user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id , @RequestBody User updatedUser){
        User user = userRepository.findById(id).orElseThrow();
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        return userRepository.save(user);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable  String id){
        userRepository.deleteById(id);
    }
}
