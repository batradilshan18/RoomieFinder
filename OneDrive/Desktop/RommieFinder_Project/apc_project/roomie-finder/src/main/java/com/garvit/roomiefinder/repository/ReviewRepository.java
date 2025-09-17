package com.garvit.roomiefinder.repository;
//create and show
import com.garvit.roomiefinder.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewedId(Long userId);
}