package com.doancuoinam.hostelappdoancuoinam.Model.Request;

public class AddBoarding {
    private String address;
    private String area;

    public AddBoarding(String address, String area) {
        this.address = address;
        this.area = area;
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
}
