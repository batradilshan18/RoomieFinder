// src/main/java/com/garvit/roomiefinder/model/ListingImage.java
package com.garvit.roomiefinder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor // Lombok annotation to create a no-argument constructor
public class ListingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This annotation ensures the database can store very long image URLs without error.
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id")
    @JsonIgnore // Prevents infinite loops during JSON serialization
    @ToString.Exclude // Prevents infinite loops in Lombok's toString method
    private Listing listing;

    // A convenient constructor for use in the controller
    public ListingImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

