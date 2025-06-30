package com.skillforge.skillforge.security;

import com.skillforge.skillforge.model.AuthUser;
import com.skillforge.skillforge.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CurrentUserUtil {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthUserRepository authUserRepository;

    public AuthUser getUserFromToken(String token) {
        String email = jwtUtil.extractEmail(token);
        Optional<AuthUser> userOpt = authUserRepository.findByEmail(email);
        return userOpt.orElse(null);
    }
}
