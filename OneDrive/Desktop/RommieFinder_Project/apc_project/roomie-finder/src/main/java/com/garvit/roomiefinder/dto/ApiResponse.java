// src/main/java/com/example/roomiefinder/dto/ApiResponse.java
package com.garvit.roomiefinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private Boolean success;
    private String message;
}