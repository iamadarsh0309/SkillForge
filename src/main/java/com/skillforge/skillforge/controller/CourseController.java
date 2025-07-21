package com.skillforge.skillforge.controller;

import com.skillforge.skillforge.model.AuthUser;
import com.skillforge.skillforge.model.Course;
import com.skillforge.skillforge.model.UserRole;
import com.skillforge.skillforge.repository.AuthUserRepository;
import com.skillforge.skillforge.repository.CourseRepository;
import com.skillforge.skillforge.security.CurrentUserUtil;
import com.skillforge.skillforge.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")

public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public Course createCourse(@RequestBody Course course, HttpServletRequest request) {
        String jwt = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractUsername(jwt);
        AuthUser mentor = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (mentor.getRole() != UserRole.MENTOR) {
            throw new RuntimeException("Only mentors can create courses");
        }

        course.setMentorId(mentor.getId());
        return courseRepository.save(course);
    }

    @GetMapping("/my")
    public List<Course> getMyCourses(HttpServletRequest request) {
        String jwt = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractUsername(jwt);
        AuthUser mentor = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return courseRepository.findByMentorId(mentor.getId());
    }


    @GetMapping
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable String id){
        Course course = courseRepository.findById(id).orElseThrow(()->
                new RuntimeException("Course not found"));
        return course;
    }

    @GetMapping("/search")
    public List<Course> searchCourse(@RequestParam String keyword){
        return courseRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @PutMapping("/{id}")
    public Course updateCourseById(@PathVariable String id, @RequestBody Course updatedCourse, HttpServletRequest request) {
        String jwt = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractUsername(jwt);
        AuthUser mentor = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // 🔒 Secure check
        if (!course.getMentorId().equals(mentor.getId())) {
            throw new RuntimeException("You are not allowed to update this course");
        }

        course.setCategory(updatedCourse.getCategory());
        course.setDescription(updatedCourse.getDescription());
        course.setDuration(updatedCourse.getDuration());
        course.setPrice(updatedCourse.getPrice());
        course.setTitle(updatedCourse.getTitle());

        return courseRepository.save(course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourseById(@PathVariable String id, HttpServletRequest request) {
        String jwt = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractUsername(jwt);
        AuthUser mentor = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // 🔒 Secure check
        if (!course.getMentorId().equals(mentor.getId())) {
            throw new RuntimeException("You are not allowed to delete this course");
        }

        courseRepository.deleteById(id);
    }


}
