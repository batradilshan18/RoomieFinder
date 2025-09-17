// src/main/java/com/garvit/roomiefinder/model/SavedListing.java
package com.garvit.roomiefinder.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SavedListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private Listing listing;
}
