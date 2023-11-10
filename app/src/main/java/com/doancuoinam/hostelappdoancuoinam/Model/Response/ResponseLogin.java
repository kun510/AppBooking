package com.doancuoinam.hostelappdoancuoinam.Model.Response;

public class ResponseLogin {
    private boolean success;
    private String message;
    private long userId;
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
