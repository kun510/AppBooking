package com.doancuoinam.hostelappdoancuoinam.Model.ModelApi;

public class Rent {
    private long id;
    private Room room;
    private Users user;
    private String status;
    private int peopleInRoom;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPeopleInRoom() {
        return peopleInRoom;
    }

    public void setPeopleInRoom(int peopleInRoom) {
        this.peopleInRoom = peopleInRoom;
    }
}
