package com.doancuoinam.hostelappdoancuoinam.Model.ModelApi;

import java.util.Date;

public class NotificationApp {
    private Long id;
    private Users user_id_sender;
    private String content;
    private Date time;
    private Users user_id_receiver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser_id_sender() {
        return user_id_sender;
    }

    public void setUser_id_sender(Users user_id_sender) {
        this.user_id_sender = user_id_sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Users getUser_id_receiver() {
        return user_id_receiver;
    }

    public void setUser_id_receiver(Users user_id_receiver) {
        this.user_id_receiver = user_id_receiver;
    }
}
