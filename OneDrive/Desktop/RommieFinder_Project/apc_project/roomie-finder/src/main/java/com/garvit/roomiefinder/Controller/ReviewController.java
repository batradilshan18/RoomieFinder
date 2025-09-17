package com.garvit.roomiefinder.Controller;

import com.garvit.roomiefinder.model.Review;
import com.garvit.roomiefinder.model.User;
import com.garvit.roomiefinder.repository.ReviewRepository;
import com.garvit.roomiefinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    @Autowired private ReviewRepository reviewRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public List<Review> getReviewsForUser(@PathVariable Long userId) {
        return reviewRepository.findByReviewedId(userId);
    }

    @PostMapping("/user/{reviewedUserId}")
    public ResponseEntity<Review> createReview(@PathVariable Long reviewedUserId, @RequestBody Review review) {
        String reviewerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User reviewer = userRepository.findByUsername(reviewerUsername).orElseThrow(() -> new RuntimeException("Reviewer not found"));
        User reviewed = userRepository.findById(reviewedUserId).orElseThrow(() -> new RuntimeException("Reviewed user not found"));

        review.setReviewer(reviewer);
        review.setReviewed(reviewed);
        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.ok(savedReview);
    }
}