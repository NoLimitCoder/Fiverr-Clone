package com.example.csci3130_w24_group20_quick_cash;

public class ChatMessage {
    private String senderName;
    private String messageContent;
    private long timestamp; // Added timestamp field

    public ChatMessage() {
        // Required empty constructor for Firebase
    }

    /**
     * Represents a chat message containing sender's name, message content, and timestamp.
     */


    /**
     * Constructs a new ChatMessage object with the specified sender name, message content, and timestamp.
     *
     * @param senderName     The name of the sender.
     * @param messageContent The content of the message.
     * @param timestamp      The timestamp of the message.
     */

    public ChatMessage(String senderName, String messageContent, long timestamp) {
        this.senderName = senderName;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}


