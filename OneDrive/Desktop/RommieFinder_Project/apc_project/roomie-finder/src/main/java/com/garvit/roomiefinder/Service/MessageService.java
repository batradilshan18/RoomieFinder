// src/main/java/com/garvit/roomiefinder/service/MessageService.java
package com.garvit.roomiefinder.Service;
// dm and profile
import com.garvit.roomiefinder.dto.ConversationDto;
import com.garvit.roomiefinder.model.Message;
import com.garvit.roomiefinder.model.User;
import com.garvit.roomiefinder.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<ConversationDto> getConversations(User currentUser) {
        List<Message> messages = messageRepository.findBySenderIdOrRecipientIdOrderByTimestampDesc(currentUser.getId(), currentUser.getId());

        Map<Long, ConversationDto> conversationMap = new LinkedHashMap<>();

        for (Message message : messages) {
            User otherUser = message.getSender().getId().equals(currentUser.getId()) ? message.getRecipient() : message.getSender();

            conversationMap.computeIfAbsent(otherUser.getId(), userId -> {
                ConversationDto dto = new ConversationDto();
                dto.setUserId(otherUser.getId());
                dto.setUsername(otherUser.getUsername());
                dto.setLastMessage(message.getContent());
                dto.setLastMessageTimestamp(message.getTimestamp());
                return dto;
            });
        }
        return new ArrayList<>(conversationMap.values());
    }
}
