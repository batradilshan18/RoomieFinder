// src/main/java/com/garvit/roomiefinder/repository/MessageRepository.java
package com.garvit.roomiefinder.repository;
//profile and dm
import com.garvit.roomiefinder.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :userId1 AND m.recipient.id = :userId2) OR (m.sender.id = :userId2 AND m.recipient.id = :userId1) ORDER BY m.timestamp ASC")
    List<Message> findConversationBetweenUsers(Long userId1, Long userId2);

    List<Message> findBySenderIdOrRecipientIdOrderByTimestampDesc(Long senderId, Long recipientId);
}
