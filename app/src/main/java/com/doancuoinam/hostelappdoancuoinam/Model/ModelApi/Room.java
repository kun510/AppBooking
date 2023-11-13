package com.doancuoinam.hostelappdoancuoinam.Model.ModelApi;


public class Room {
    private int id;
    private String address;
    private String area;
    private String description;
    private String img;
    private String  numberOfStars;
    private String  numberRoom;
    private String status;
    private int electricBill;
    private int waterBill;
    private String  price;
    private String  people;
    private String type;
    private Boarding_host boardingHostel;

    private Users users;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Boarding_host getBoardingHostel() {
        return boardingHostel;
    }

    public void setBoardingHostel(Boarding_host boardingHostel) {
        this.boardingHostel = boardingHostel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(String numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    public String getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(String numberRoom) {
        this.numberRoom = numberRoom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getElectricBill() {
        return electricBill;
    }

    public void setElectricBill(int electricBill) {
        this.electricBill = electricBill;
    }

    public int getWaterBill() {
        return waterBill;
    }

    public void setWaterBill(int waterBill) {
        this.waterBill = waterBill;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
