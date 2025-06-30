package com.skillforge.skillforge.service;

import com.skillforge.skillforge.model.AuthUser;
import com.skillforge.skillforge.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
@Service
public class AuthUserDetailsService implements UserDetailsService {
    @Autowired
    private AuthUserRepository authUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthUser user = authUserRepository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException("User not found with this email")
        );
        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}
