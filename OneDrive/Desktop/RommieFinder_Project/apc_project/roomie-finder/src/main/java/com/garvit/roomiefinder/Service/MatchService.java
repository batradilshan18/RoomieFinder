// src/main/java/com/garvit/roomiefinder/service/MatchService.java
package com.garvit.roomiefinder.Service;
//match prefernce
import com.garvit.roomiefinder.model.User;
import com.garvit.roomiefinder.repository.UserRepository;
import com.garvit.roomiefinder.service.UserMatch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final UserRepository userRepository;

    public List<UserMatch> findMatches(User currentUser) {
        List<User> allOtherUsers = userRepository.findByIdNot(currentUser.getId());
        List<UserMatch> matches = new ArrayList<>();

        // Apply the university filter first, as requested.
        List<User> potentialMatches = allOtherUsers;
        if (currentUser.getUniversity() != null && !currentUser.getUniversity().isBlank()) {
            potentialMatches = allOtherUsers.stream()
                    .filter(other -> currentUser.getUniversity().equalsIgnoreCase(other.getUniversity()))
                    .collect(Collectors.toList());
        }

        for (User otherUser : potentialMatches) {
            int score = calculateCompatibilityScore(currentUser, otherUser);
            // We can set a threshold, e.g., only show matches with a score > 0
            if (score > 0) {
                matches.add(new UserMatch(otherUser, score));
            }
        }

        // Sort the matches by score in descending order
        matches.sort(Comparator.comparingInt(UserMatch::getScore).reversed());

        return matches;
    }

    private int calculateCompatibilityScore(User user1, User user2) {
        int score = 0;
        // The total number of categories we are comparing
        final int TOTAL_CATEGORIES = 5;

        if (isMatch(user1.getCleanliness(), user2.getCleanliness())) score++;
        if (isMatch(user1.getSocialHabits(), user2.getSocialHabits())) score++;
        if (isMatch(user1.getSleepSchedule(), user2.getSleepSchedule())) score++;
        if (isMatch(user1.getDiet(), user2.getDiet())) score++;
        if (isMatch(user1.getSmoking(), user2.getSmoking())) score++;

        // Return score as a percentage
        return (score * 100) / TOTAL_CATEGORIES;
    }

    private boolean isMatch(String pref1, String pref2) {
        // A simple check: if both users have set a preference and they are the same, it's a match.
        return pref1 != null && !pref1.isBlank() && pref1.equalsIgnoreCase(pref2);
    }
}
