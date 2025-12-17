package com.x2ketarol.askon.domain.usecases;

import com.x2ketarol.askon.domain.model.Message;
import com.x2ketarol.askon.domain.repository.ChatRepository;

public class SendMessageUseCase {
    private final ChatRepository repository;

    public SendMessageUseCase(ChatRepository repository) {
        this.repository = repository;
    }

    public Message execute(String senderId, String receiverId, String text) {
        return repository.sendMessage(senderId, receiverId, text);
    }
}
