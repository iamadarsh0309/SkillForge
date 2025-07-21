package com.skillforge.skillforge.controller;

import com.skillforge.skillforge.model.AuthUser;
import com.skillforge.skillforge.model.UserRole;
import com.skillforge.skillforge.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private AuthUserRepository authUserRepository;

    //  Get all mentors
    @GetMapping("/mentors")
    public List<AuthUser> getAllMentors() {
        return authUserRepository.findByRole(UserRole.MENTOR);
    }

    //  Get all students
    @GetMapping("/students")
    public List<AuthUser> getAllStudents() {
        return authUserRepository.findByRole(UserRole.STUDENT);
    }

    // ✅ Get public profile by ID
    @GetMapping("/{id}")
    public AuthUser getUserById(@PathVariable String id) {
        return authUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
