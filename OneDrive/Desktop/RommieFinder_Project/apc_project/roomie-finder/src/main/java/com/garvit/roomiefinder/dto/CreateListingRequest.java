// src/main/java/com/garvit/roomiefinder/dto/CreateListingRequest.java
package com.garvit.roomiefinder.dto;
//create and show
import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateListingRequest {
    private String title;
    private String description;
    private String address;
    private String city;
    private Double rent;
    private LocalDate availableDate;
    // We now only need one field for the single image URL
    private String imageUrl;
}

