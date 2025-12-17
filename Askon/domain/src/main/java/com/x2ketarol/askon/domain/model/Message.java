package com.x2ketarol.askon.domain.model;

public class Message {
    private String id;
    private String senderId;
    private String receiverId;
    private String text;
    private String timestamp;

    public Message(String id, String senderId, String receiverId, String text, String timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
