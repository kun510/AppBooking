package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message;

public class MessageModel {
    private String senderId;
    private String receiverId;
    private String text;
    private long timestamp;

    public MessageModel(String senderId,String receiverId, String text, long timestamp) {
        this.senderId = senderId;
        this.text = text;
        this.receiverId = receiverId;
        this.timestamp = timestamp;
    }

    public MessageModel() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
