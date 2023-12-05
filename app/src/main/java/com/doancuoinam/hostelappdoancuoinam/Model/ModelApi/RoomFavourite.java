package com.doancuoinam.hostelappdoancuoinam.Model.ModelApi;

import java.util.Date;

public class RoomFavourite {

    private int id;
    private Room room;
    private Users user;
    private Date day;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
