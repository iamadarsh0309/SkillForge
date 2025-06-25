package com.skillforge.skillforge.controller;

import com.skillforge.skillforge.model.AuthUser;
import com.skillforge.skillforge.repository.AuthUserRepository;
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

    @PostMapping("/signup")
    public String signup(@RequestBody AuthUser user){
        return authUserService.signUp(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthUser user){
        return authUserService.login(user);
    }
}
