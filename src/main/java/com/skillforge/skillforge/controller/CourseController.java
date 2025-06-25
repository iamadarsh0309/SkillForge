package com.skillforge.skillforge.controller;

import com.skillforge.skillforge.model.Course;
import com.skillforge.skillforge.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")

public class CourseController {
    @Autowired
    private CourseRepository courseRepository;
    @PostMapping
    public Course addCourse(@RequestBody Course course){

        return courseRepository.save(course);
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
    public Course updateCourseById(@PathVariable String id , @RequestBody Course updatedCourse){
        Course course = courseRepository.findById(id).orElseThrow(()->
                new RuntimeException("Course not found"));
        course.setCategory(updatedCourse.getCategory());
        course.setDescription(updatedCourse.getDescription());
        course.setDuration(updatedCourse.getDuration());
        course.setPrice(updatedCourse.getPrice());
        course.setTitle(updatedCourse.getTitle());
        return courseRepository.save(course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourseById(@PathVariable String id){
        courseRepository.deleteById(id);
    }


}
