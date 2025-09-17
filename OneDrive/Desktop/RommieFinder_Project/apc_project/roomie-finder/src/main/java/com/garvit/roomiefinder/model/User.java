// src/main/java/com/garvit/roomiefinder/model/User.java
package com.garvit.roomiefinder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "listings")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String fullName;
    private String username;
    private String email;

    @JsonIgnore // Never expose password
    private String password;

    private String bio;

    // Roommate matching fields
    private String university;
    private String cleanliness;
    private String socialHabits;
    private String sleepSchedule;
    private String diet;
    private String smoking;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<Listing> listings = new ArrayList<>();
}
