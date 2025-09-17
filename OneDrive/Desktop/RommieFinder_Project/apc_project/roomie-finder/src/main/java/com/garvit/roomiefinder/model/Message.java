// src/main/java/com/example/roomiefinder/model/Message.java
package com.garvit.roomiefinder.model;
//prodile and dm
import com.garvit.roomiefinder.model.User;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    private String content;
    private LocalDateTime timestamp;
}