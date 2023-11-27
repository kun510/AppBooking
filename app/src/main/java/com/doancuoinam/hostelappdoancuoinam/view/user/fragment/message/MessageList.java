package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message;

public class MessageList {
    private String name;
    private String phone;
    private String lastMessage;
    private String email;
    private int unseenMessage;

    public MessageList(String name, String phone, String lastMessage,String email ,int unseenMessage) {
        this.name = name;
        this.phone = phone;
        this.lastMessage = lastMessage;
        this.email = email;
        this.unseenMessage = unseenMessage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String profilePic) {
        email = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getUnseenMessage() {
        return unseenMessage;
    }

    public void setUnseenMessage(int unseenMessage) {
        this.unseenMessage = unseenMessage;
    }
}
