package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message;

public class MessageList {
    private String name;
    private String phone;
    private String avt;
    private String lastMessage;
    private int unseenMessage;


    public MessageList(String phone, String lastMessage) {
        this.phone = phone;
        this.lastMessage = lastMessage;

    }

    public MessageList(String name, String phone, String avt) {
        this.name = name;
        this.phone = phone;
        this.avt = avt;
    }



    public MessageList(String userName, String userPhoneNumber, String userAvt, String lastMessage) {
        this.name = userName;
        this.phone = userPhoneNumber;
        this.avt = userAvt;
        this.lastMessage = lastMessage;
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

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
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
