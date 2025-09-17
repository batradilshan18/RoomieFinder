package com.garvit.roomiefinder.model;
//create and show
import lombok.Data;
// Corrected import statement
import jakarta.persistence.*;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User reviewer; // The one writing the review

    @ManyToOne
    private User reviewed; // The one being reviewed

    private int rating; // e.g., 1 to 5
    private String comment;
}