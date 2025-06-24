package com.skillforge.skillforge.repository;

import com.skillforge.skillforge.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course,String> {
    List<Course> findByTitleContainingIgnoreCase(String keyword);

}
