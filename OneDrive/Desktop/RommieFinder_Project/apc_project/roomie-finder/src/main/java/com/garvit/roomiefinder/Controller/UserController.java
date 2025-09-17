// src/main/java/com/garvit/roomiefinder/controller/UserController.java
package com.garvit.roomiefinder.Controller;
//profile and authentication
import com.garvit.roomiefinder.dto.UserProfileResponse;
import com.garvit.roomiefinder.dto.UserUpdateRequest;
import com.garvit.roomiefinder.model.Listing;
import com.garvit.roomiefinder.model.User;
import com.garvit.roomiefinder.repository.ListingRepository;
import com.garvit.roomiefinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + currentUsername));

        List<Listing> userListings = listingRepository.findByUserId(currentUser.getId());

        // This constructor call now includes all 12 required arguments, matching the DTO.
        UserProfileResponse profileResponse = new UserProfileResponse(
                currentUser.getId(),
                currentUser.getUsername(),
                currentUser.getEmail(),
                currentUser.getFullName(),
                currentUser.getBio(),
                currentUser.getUniversity(),
                currentUser.getCleanliness(),
                currentUser.getSocialHabits(),
                currentUser.getSleepSchedule(),
                currentUser.getDiet(),
                currentUser.getSmoking(),
                userListings
        );

        return ResponseEntity.ok(profileResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserUpdateRequest updateRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        currentUser.setFullName(updateRequest.getFullName());
        currentUser.setBio(updateRequest.getBio());
        userRepository.save(currentUser);

        return ResponseEntity.ok("Profile updated successfully.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfileById(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        List<Listing> userListings = listingRepository.findByUserId(user.getId());

        // The constructor call is also updated here for public profiles, matching the DTO.
        UserProfileResponse profileResponse = new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getBio(),
                user.getUniversity(),
                user.getCleanliness(),
                user.getSocialHabits(),
                user.getSleepSchedule(),
                user.getDiet(),
                user.getSmoking(),
                userListings
        );

        return ResponseEntity.ok(profileResponse);
    }
}

