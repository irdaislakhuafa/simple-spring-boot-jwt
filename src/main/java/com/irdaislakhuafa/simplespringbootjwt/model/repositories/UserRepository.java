package com.irdaislakhuafa.simplespringbootjwt.model.repositories;

import java.util.Optional;

import com.irdaislakhuafa.simplespringbootjwt.model.entities.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findByEmailIgnoreCase(String email);
}
