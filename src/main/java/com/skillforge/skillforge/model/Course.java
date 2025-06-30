package com.skillforge.skillforge.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Course {
    @Id
    private String id;

    private String title;
    private String description;
    private String mentorId; // User ID who created this course
    private double price;
    private String category;
    private int duration;

    private double averageRating = 0.0;

}
