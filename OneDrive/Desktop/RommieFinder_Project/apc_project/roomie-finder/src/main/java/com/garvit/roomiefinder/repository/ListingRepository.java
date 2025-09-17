// src/main/java/com/garvit/roomiefinder/repository/ListingRepository.java
package com.garvit.roomiefinder.repository;
//create and show
import com.garvit.roomiefinder.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    // With the simplified single-image model, we no longer need the complex @EntityGraph annotations.
    // Spring Data JPA can automatically implement these methods for us.

    /**
     * Finds all listings in a specific city, ignoring case.
     */
    List<Listing> findByCityIgnoreCase(String city);

    /**
     * Finds all listings created by a specific user.
     */
    List<Listing> findByUserId(Long userId);
}

