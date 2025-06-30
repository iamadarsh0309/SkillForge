package com.skillforge.skillforge.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "reviews")
public class Review {
    @Id
    private String id;

    @NotBlank
    private String courseId;
    @NotBlank
    private String userId;
    private String comment;
    @Min(1)
    @Max(5)
    private int rating;

    private Date reviewedAt = new Date();
}
