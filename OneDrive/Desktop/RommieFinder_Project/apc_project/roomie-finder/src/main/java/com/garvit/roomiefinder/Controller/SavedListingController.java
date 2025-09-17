// src/main/java/com/garvit/roomiefinder/controller/SavedListingController.java
package com.garvit.roomiefinder.Controller;

import com.garvit.roomiefinder.model.Listing;
import com.garvit.roomiefinder.model.SavedListing;
import com.garvit.roomiefinder.model.User;
import com.garvit.roomiefinder.repository.ListingRepository;
import com.garvit.roomiefinder.repository.SavedListingRepository;
import com.garvit.roomiefinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/saved-listings")
@CrossOrigin(origins = "http://localhost:3000")
public class SavedListingController {

    @Autowired
    private SavedListingRepository savedListingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ListingRepository listingRepository;

    @GetMapping
    public ResponseEntity<List<Listing>> getSavedListings() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        List<Listing> listings = savedListingRepository.findByUserId(user.getId())
                .stream()
                .map(SavedListing::getListing)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listings);
    }

    @PostMapping("/{listingId}")
    public ResponseEntity<?> saveListing(@PathVariable Long listingId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new RuntimeException("Listing not found"));

        // Avoid duplicates
        if (savedListingRepository.findByUserIdAndListingId(user.getId(), listing.getId()).isPresent()) {
            return ResponseEntity.badRequest().body("Listing already saved.");
        }

        SavedListing savedListing = new SavedListing();
        savedListing.setUser(user);
        savedListing.setListing(listing);
        savedListingRepository.save(savedListing);

        return ResponseEntity.ok("Listing saved successfully.");
    }

    @DeleteMapping("/{listingId}")
    public ResponseEntity<?> unsaveListing(@PathVariable Long listingId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        SavedListing savedListing = savedListingRepository.findByUserIdAndListingId(user.getId(), listingId)
                .orElseThrow(() -> new RuntimeException("Saved listing not found"));

        savedListingRepository.delete(savedListing);
        return ResponseEntity.ok("Listing unsaved successfully.");
    }

    @GetMapping("/status/{listingId}")
    public ResponseEntity<Boolean> getSaveStatus(@PathVariable Long listingId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        boolean isSaved = savedListingRepository.findByUserIdAndListingId(user.getId(), listingId).isPresent();
        return ResponseEntity.ok(isSaved);
    }
}
