// src/main/java/com/example/roomiefinder/dto/SignUpRequest.java
package com.garvit.roomiefinder.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    private String fullName;
}