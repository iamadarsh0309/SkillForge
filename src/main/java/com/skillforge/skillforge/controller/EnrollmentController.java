package com.skillforge.skillforge.controller;

import com.skillforge.skillforge.model.Enrollment;
import com.skillforge.skillforge.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @PostMapping
    public Enrollment enrollUser(@RequestBody Enrollment enrollment){
        return enrollmentRepository.save(enrollment);
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments(){
        return enrollmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Enrollment getEnrollmentById(@PathVariable String id){
        return enrollmentRepository.findById(id).orElseThrow(()-> new RuntimeException("Enrollment not found"));

    }

    @PutMapping("/{id}")
    public Enrollment updateProgress(@PathVariable String id,@RequestBody Enrollment updatedEnrollment) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(()-> new RuntimeException());
        enrollment.setProgress(updatedEnrollment.getProgress());
        return enrollmentRepository.save(enrollment);
    }

    @DeleteMapping("/{id}")
    public void deleteEnrollmentById(@PathVariable String id){
        enrollmentRepository.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Enrollment> getEnrollmentsByUserId(@PathVariable String userId) {
        return enrollmentRepository.findByUserId(userId);
    }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourseId(@PathVariable String courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

}
