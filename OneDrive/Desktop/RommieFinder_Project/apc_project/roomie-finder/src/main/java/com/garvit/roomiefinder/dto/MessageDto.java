
package com.garvit.roomiefinder.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Long id;
    private Long senderId;
    private String senderUsername;
    private Long recipientId;
    private String recipientUsername;
    private String content;
    private LocalDateTime timestamp;
}
