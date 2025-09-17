// src/main/java/com/garvit/roomiefinder/controller/MatchController.java
package com.garvit.roomiefinder.Controller;

//match prefernces
import com.garvit.roomiefinder.dto.UserPreferencesDto;
import com.garvit.roomiefinder.model.User;
import com.garvit.roomiefinder.repository.UserRepository;
import com.garvit.roomiefinder.Service.MatchService;
import com.garvit.roomiefinder.service.UserMatch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class MatchController {

    private final UserRepository userRepository;
    private final MatchService matchService;

    @PutMapping("/preferences")
    public ResponseEntity<?> updateUserPreferences(@RequestBody UserPreferencesDto preferencesDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update user fields from the DTO
        currentUser.setUniversity(preferencesDto.getUniversity());
        currentUser.setCleanliness(preferencesDto.getCleanliness());
        currentUser.setSocialHabits(preferencesDto.getSocialHabits());
        currentUser.setSleepSchedule(preferencesDto.getSleepSchedule());
        currentUser.setDiet(preferencesDto.getDiet());
        currentUser.setSmoking(preferencesDto.getSmoking());

        userRepository.save(currentUser);
        return ResponseEntity.ok("Preferences updated successfully.");
    }

    @GetMapping
    public ResponseEntity<List<UserMatch>> getMatches() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // --- THIS IS THE FIX ---
        // By explicitly declaring the type of the list here, we help the Java
        // compiler and the IDE correctly resolve the "Incompatible types" error.
        List<UserMatch> matches = matchService.findMatches(currentUser);

        return ResponseEntity.ok(matches);
    }
}

