// src/main/java/com/garvit/roomiefinder/dto/UserPreferencesDto.java
package com.garvit.roomiefinder.dto;
//match prefernce
import lombok.Data;

@Data
public class UserPreferencesDto {
    private String university;
    private String cleanliness;
    private String socialHabits;
    private String sleepSchedule;
    private String diet;
    private String smoking;
}
