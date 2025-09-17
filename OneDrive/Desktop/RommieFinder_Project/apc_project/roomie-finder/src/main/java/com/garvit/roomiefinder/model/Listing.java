// src/main/java/com/garvit/roomiefinder/model/Listing.java
package com.garvit.roomiefinder.model;
//create and show
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String address;
    private String city;
    private double rent;
    private LocalDate availableDate;

    @Column(columnDefinition = "TEXT")
    private String imageUrl; // A single field for the image

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "listings", "password"})
    private User user;
}

