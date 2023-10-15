package com.doancuoinam.hostelappdoancuoinam.Model.Request;

public class SearchRequest {
    String address ;
    String price;
    String area;
    String people;
    String type;

    public SearchRequest(String address, String price, String area, String people, String type) {
        this.address = address;
        this.price = price;
        this.area = area;
        this.people = people;
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
