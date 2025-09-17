// src/main/java/com/garvit/roomiefinder/repository/ListingImageRepository.java
package com.garvit.roomiefinder.repository;

import com.garvit.roomiefinder.model.ListingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingImageRepository extends JpaRepository<ListingImage, Long> {
    // Spring Data JPA will automatically provide all the necessary methods (save, findById, etc.)
    // No custom methods are needed here for our current features.
}

