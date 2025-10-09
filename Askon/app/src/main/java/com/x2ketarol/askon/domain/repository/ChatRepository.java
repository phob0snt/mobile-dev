package com.x2ketarol.askon.domain.repository;

import com.x2ketarol.askon.domain.model.Message;
import java.util.List;

public interface ChatRepository {
    List<Message> getMessages(String userId, String expertId);
    Message sendMessage(String senderId, String receiverId, String text);
}
