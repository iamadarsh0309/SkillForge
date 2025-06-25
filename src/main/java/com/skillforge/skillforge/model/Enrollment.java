package com.skillforge.skillforge.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "enrollments")
@Data
@NoArgsConstructor
public class Enrollment {

    @Id
    private String id;
    private String userId;
    private String courseId;

    private int progress = 0;
    private boolean completed = false;
    private Date enrolledAt = new Date();

    public void setProgress(int progress){
        this.progress = progress;
        this.completed = (progress == 100);
    }

}

