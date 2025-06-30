package com.skillforge.skillforge.security;

import com.skillforge.skillforge.model.AuthUser;
import com.skillforge.skillforge.model.UserRole;
import com.skillforge.skillforge.repository.AuthUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
public class RequireMentor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthUserRepository authUserRepository;

    public Optional<AuthUser> getMentorFromRequest(HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");
        if(authHeader!=null && authHeader.startsWith("Bearer")){
            String token = authHeader.substring(7);
            String email = jwtUtil.extractEmail(token);
            Optional<AuthUser> userOpt = authUserRepository.findByEmail(email);
            if(userOpt.isPresent() && userOpt.get().getRole() == UserRole.MENTOR){
                return userOpt;
            }
        }
        return Optional.empty();
    }
}
