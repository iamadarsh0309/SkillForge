package com.skillforge.skillforge.repository;

import com.skillforge.skillforge.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
