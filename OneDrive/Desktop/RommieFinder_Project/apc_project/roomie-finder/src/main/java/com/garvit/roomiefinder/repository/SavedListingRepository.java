// src/main/java/com/garvit/roomiefinder/repository/SavedListingRepository.java
package com.garvit.roomiefinder.repository;

import com.garvit.roomiefinder.model.SavedListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SavedListingRepository extends JpaRepository<SavedListing, Long> {
    Optional<SavedListing> findByUserIdAndListingId(Long userId, Long listingId);
    List<SavedListing> findByUserId(Long userId);
}
