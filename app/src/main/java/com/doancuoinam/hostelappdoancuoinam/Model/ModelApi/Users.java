package com.doancuoinam.hostelappdoancuoinam.Model.ModelApi;

public class Users {
    private Long id;
    private String phone;
    private String password;
    private String address;
    private String email;
    private String img;
    private String name;
    private String confirmation_status;
    private String token_device;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfirmation_status() {
        return confirmation_status;
    }

    public void setConfirmation_status(String confirmation_status) {
        this.confirmation_status = confirmation_status;
    }

    public String getToken_device() {
        return token_device;
    }

    public void setToken_device(String token_device) {
        this.token_device = token_device;
    }
}