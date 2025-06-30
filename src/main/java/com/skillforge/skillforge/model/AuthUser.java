package com.skillforge.skillforge.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auth_users")
@Data
@NoArgsConstructor
public class AuthUser {
    @Id
    private String id;

    private String email;
    private String password;
    private UserRole role;
}
