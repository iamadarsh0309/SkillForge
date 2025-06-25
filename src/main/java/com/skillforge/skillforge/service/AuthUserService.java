package com.skillforge.skillforge.service;

import com.skillforge.skillforge.model.AuthUser;
import com.skillforge.skillforge.repository.AuthUserRepository;
import com.skillforge.skillforge.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService {
    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String signUp(AuthUser user){
        if (authUserRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authUserRepository.save(user);
        return "Signup Success";
    }

    public String login(AuthUser user) {
        Optional<AuthUser> existing = authUserRepository.findByEmail(user.getEmail());
        if(existing.isPresent()){
            AuthUser found = existing.get();
            if(passwordEncoder.matches(user.getPassword(), found.getPassword())){
                return jwtUtil.generateToken(user.getEmail());
            }
            else{
                return "Invalid credentials";
            }
        }
        return "User not found";
    }
}
