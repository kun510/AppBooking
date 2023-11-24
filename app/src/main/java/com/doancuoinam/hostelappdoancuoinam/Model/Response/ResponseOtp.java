package com.doancuoinam.hostelappdoancuoinam.Model.Response;

public class ResponseOtp {
    private boolean success;
    private String otp;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
