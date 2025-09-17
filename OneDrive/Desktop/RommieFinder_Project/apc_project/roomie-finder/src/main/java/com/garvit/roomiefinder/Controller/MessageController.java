// src/main/java/com/garvit/roomiefinder/controller/MessageController.java
package com.garvit.roomiefinder.Controller;

import com.garvit.roomiefinder.dto.ConversationDto;
import com.garvit.roomiefinder.dto.MessageDto;
import com.garvit.roomiefinder.dto.SendMessageRequest;
import com.garvit.roomiefinder.model.Message;
import com.garvit.roomiefinder.model.User;
import com.garvit.roomiefinder.repository.MessageRepository;
import com.garvit.roomiefinder.repository.UserRepository;
import com.garvit.roomiefinder.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationDto>> getConversations() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(messageService.getConversations(currentUser));
    }

    @GetMapping("/conversation/{otherUserId}")
    public ResponseEntity<List<MessageDto>> getConversation(@PathVariable Long otherUserId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        List<Message> messages = messageRepository.findConversationBetweenUsers(currentUser.getId(), otherUserId);
        List<MessageDto> messageDtos = messages.stream().map(this::convertToDto).collect(Collectors.toList());

        return ResponseEntity.ok(messageDtos);
    }

    @PostMapping("/send")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody SendMessageRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User sender = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Sender not found"));
        User recipient = userRepository.findById(request.getRecipientId()).orElseThrow(() -> new RuntimeException("Recipient not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(request.getContent());
        message.setTimestamp(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.ok(convertToDto(savedMessage));
    }

    private MessageDto convertToDto(Message message) {
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setSenderId(message.getSender().getId());
        dto.setSenderUsername(message.getSender().getUsername());
        dto.setRecipientId(message.getRecipient().getId());
        dto.setRecipientUsername(message.getRecipient().getUsername());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());
        return dto;
    }
}
