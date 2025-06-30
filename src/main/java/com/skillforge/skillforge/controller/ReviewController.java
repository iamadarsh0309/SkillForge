package com.skillforge.skillforge.controller;

import com.skillforge.skillforge.model.AuthUser;
import com.skillforge.skillforge.model.Course;
import com.skillforge.skillforge.model.Review;
import com.skillforge.skillforge.repository.AuthUserRepository;
import com.skillforge.skillforge.repository.CourseRepository;
import com.skillforge.skillforge.repository.EnrollmentRepository;
import com.skillforge.skillforge.repository.ReviewRepository;
import com.skillforge.skillforge.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("api/reviews")
public class ReviewController {
        @Autowired
        private ReviewRepository reviewRepository;

        @Autowired
        private AuthUserRepository authUserRepository;

        @Autowired
        private EnrollmentRepository enrollmentRepository;

        @Autowired
        private CourseRepository courseRepository;

        @Autowired
        private JwtUtil jwtUtil;

        @PostMapping
    public ResponseEntity<?> postReview(@RequestBody @Valid Review review , @RequestHeader("Authorization") String authHeader){
            String email = jwtUtil.extractEmail(authHeader.substring(7));
            Optional<AuthUser> user = authUserRepository.findByEmail(email);
            if(user.isEmpty()) return ResponseEntity.status(401).body("Unauthorized");

            boolean enrolled= enrollmentRepository.findByUserId(user.get().getId()).stream()
                    .anyMatch(e->e.getCourseId().equals(review.getCourseId()));

            if(!enrolled)
                return ResponseEntity.status(403).body("You must be enrolled to review this course");

            review.setUserId(user.get().getId());
            reviewRepository.save(review);

            List<Review> courseReviews = reviewRepository.findByCourseId(review.getCourseId());
            double average = courseReviews.stream().mapToInt(Review::getRating).average().orElse(0.0);

            Course course = courseRepository.findById(review.getCourseId()).orElseThrow(()->
                    new RuntimeException("Course not found"));

            course.setAverageRating(average);
            courseRepository.save(course);

            return ResponseEntity.ok("review submitted");

        }

        @GetMapping("/course/{courseId}")
    public List<Review> getReviewsForCourse(@PathVariable String courseId){
            return reviewRepository.findByCourseId(courseId);
        }

        @GetMapping
    public List<Review> getAllReviews(){
            return reviewRepository.findAll();
        }

}
