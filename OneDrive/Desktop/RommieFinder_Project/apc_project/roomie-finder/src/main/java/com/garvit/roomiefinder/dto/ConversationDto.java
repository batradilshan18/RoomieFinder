// src/main/java/com/garvit/roomiefinder/dto/ConversationDto.java
package com.garvit.roomiefinder.dto;
//own profile and dm
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConversationDto {
    private Long userId;
    private String username;
    private String lastMessage;
    private LocalDateTime lastMessageTimestamp;
}
