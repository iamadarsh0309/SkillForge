package com.skillforge.skillforge.repository;

import com.skillforge.skillforge.model.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EnrollmentRepository extends MongoRepository<Enrollment,String> {

    List<Enrollment> findByUserId(String userId);
    List<Enrollment> findByCourseId(String courseId);
}
