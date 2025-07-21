package com.skillforge.skillforge.controller;

import com.skillforge.skillforge.model.AuthUser;
import com.skillforge.skillforge.repository.AuthUserRepository;
import com.skillforge.skillforge.security.CurrentUserUtil;
import com.skillforge.skillforge.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthUserController {

    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private CurrentUserUtil currentUserUtil;
    @Autowired
    private AuthUserRepository authUserRepository;

    @PostMapping("/signup")
    public String signup(@RequestBody AuthUser user){
        return authUserService.signUp(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthUser user){
        return authUserService.login(user);
    }

    @GetMapping("/me")
    public AuthUser getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer "

        return currentUserUtil.getUserFromToken(token);
    }

    @PutMapping("/me")
    public AuthUser updateCurrentUser(@RequestHeader("Authorization") String authHeader,
                                      @RequestBody AuthUser updatedUser) {
        String token = authHeader.substring(7);
        AuthUser existingUser = currentUserUtil.getUserFromToken(token);

        if (existingUser == null) throw new RuntimeException("User not found");

        existingUser.setName(updatedUser.getName());
        existingUser.setBio(updatedUser.getBio());
        existingUser.setProfileImage(updatedUser.getProfileImage());

        return authUserRepository.save(existingUser);
    }
}
