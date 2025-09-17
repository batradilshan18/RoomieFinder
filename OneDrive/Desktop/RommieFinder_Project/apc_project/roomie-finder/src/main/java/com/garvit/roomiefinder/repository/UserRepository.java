// src/main/java/com/garvit/roomiefinder/repository/UserRepository.java
package com.garvit.roomiefinder.repository;

import com.garvit.roomiefinder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Import the List class
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    // --- THIS IS THE NEW METHOD REQUIRED BY THE MATCHING SERVICE ---
    // It finds all users whose ID is not the one provided.
    List<User> findByIdNot(Long userId);
}
