package com.x2ketarol.askon.data.repository;

import com.x2ketarol.askon.domain.model.Message;
import com.x2ketarol.askon.domain.repository.ChatRepository;
import java.util.ArrayList;
import java.util.List;

public class ChatRepositoryImpl implements ChatRepository {
    private List<Message> messages = new ArrayList<>();

    public ChatRepositoryImpl() {
        messages.add(new Message("1", "1", "2", "Hello, I need help with plumbing", "10:30"));
        messages.add(new Message("2", "2", "1", "Sure! What's the issue?", "10:32"));
        messages.add(new Message("3", "1", "2", "My sink is leaking", "10:33"));
    }

    @Override
    public List<Message> getMessages(String userId, String expertId) {
        return new ArrayList<>(messages);
    }

    @Override
    public Message sendMessage(String senderId, String receiverId, String text) {
        String id = String.valueOf(messages.size() + 1);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        Message newMessage = new Message(id, senderId, receiverId, text, timestamp);
        messages.add(newMessage);
        return newMessage;
    }
}
