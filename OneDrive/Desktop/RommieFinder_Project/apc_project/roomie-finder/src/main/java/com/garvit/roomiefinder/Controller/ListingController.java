// src/main/java/com/garvit/roomiefinder/controller/ListingController.java
package com.garvit.roomiefinder.Controller;
//create listing plus show bridge bw fronted and backened
import com.garvit.roomiefinder.dto.CreateListingRequest;
import com.garvit.roomiefinder.model.Listing;
import com.garvit.roomiefinder.model.User;
import com.garvit.roomiefinder.repository.ListingRepository;
import com.garvit.roomiefinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ListingController {

    private static final Logger logger = LoggerFactory.getLogger(ListingController.class);

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    /**
     * Creates a new listing with a single image URL.
     * This is the definitive fix for the single-image feature.
     */
    @PostMapping
    @Transactional
    public ResponseEntity<Listing> createListing(@RequestBody CreateListingRequest createRequest) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + currentUsername));

        Listing newListing = new Listing();
        newListing.setTitle(createRequest.getTitle());
        newListing.setDescription(createRequest.getDescription());
        newListing.setAddress(createRequest.getAddress());
        newListing.setCity(createRequest.getCity());
        newListing.setRent(createRequest.getRent());
        newListing.setAvailableDate(createRequest.getAvailableDate());
        newListing.setUser(currentUser);

        // Directly set the single image URL from the request
        if (createRequest.getImageUrl() != null && !createRequest.getImageUrl().isBlank()) {
            newListing.setImageUrl(createRequest.getImageUrl().trim());
        }

        Listing savedListing = listingRepository.save(newListing);
        logger.info("CREATE SUCCESS - Listing [{}] created by [{}]", savedListing.getId(), currentUsername);
        return ResponseEntity.ok(savedListing);
    }

    /**
     * Gets all listings, with an optional filter by city.
     */
    @GetMapping
    public List<Listing> getAllListings(@RequestParam(required = false) String city) {
        if (city != null && !city.isEmpty()) {
            return listingRepository.findByCityIgnoreCase(city);
        }
        return listingRepository.findAll();
    }

    /**
     * Gets a single listing by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        return listingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a listing, but only if the requester is the owner.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteListing(@PathVariable Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return listingRepository.findById(id).map(listing -> {
            String ownerUsername = listing.getUser().getUsername();
            if (!ownerUsername.equals(currentUsername)) {
                logger.warn("DELETE FAILED - User [{}] is not the owner of listing [{}].", currentUsername, id);
                return new ResponseEntity<>("You are not authorized to delete this listing.", HttpStatus.FORBIDDEN);
            }
            listingRepository.delete(listing);
            logger.info("DELETE SUCCESS - Listing ID: [{}] deleted successfully.", id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

