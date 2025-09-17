// src/main/java/com/garvit/roomiefinder/dto/UserProfileResponse.java
package com.garvit.roomiefinder.dto;

import com.garvit.roomiefinder.model.Listing;
import lombok.Data;
import java.util.List;

@Data
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String bio;

    // Preference fields
    private String university;
    private String cleanliness;
    private String socialHabits;
    private String sleepSchedule;
    private String diet;
    private String smoking;

    private List<Listing> listings;

    // The constructor now correctly expects all 12 arguments
    public UserProfileResponse(Long id, String username, String email, String fullName, String bio,
                               String university, String cleanliness, String socialHabits,
                               String sleepSchedule, String diet, String smoking,
                               List<Listing> listings) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.bio = bio;
        this.university = university;
        this.cleanliness = cleanliness;
        this.socialHabits = socialHabits;
        this.sleepSchedule = sleepSchedule;
        this.diet = diet;
        this.smoking = smoking;
        this.listings = listings;
    }
}

