package com.doancuoinam.hostelappdoancuoinam.Model.ModelApi;

public class Boarding_host {
    private Long id;
    private String address;
    private String area;
    private String img;
    private int numberRoom;
    private float numberOfStars;

    public int getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(int numberRoom) {
        this.numberRoom = numberRoom;
    }

    public float getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(float numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    private String status;
    private Users user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
