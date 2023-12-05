package com.doancuoinam.hostelappdoancuoinam.changePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseAll;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseLogin;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.account.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText pass,confirmPass;
    Button btn_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        pass = findViewById(R.id.pass);
        confirmPass = findViewById(R.id.confirmPass);
        btn_change = findViewById(R.id.btn_change);

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = pass.getText().toString();
                String passwordConfirm = confirmPass.getText().toString();
                if (passwordConfirm.trim().isEmpty() || password.trim().isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, R.string.nhapdaydu, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!passwordConfirm.equals(password)){
                    Toast.makeText(ChangePasswordActivity.this,  R.string.nhaplai, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isStrongPassword(password)){
                    Toast.makeText(ChangePasswordActivity.this, R.string.nhapformat, Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                long userID = sharedPreferences.getLong("userId", 0);
                HandelChange(userID,password);
            }
        });
    }
    private boolean isStrongPassword(String password) {
        if (password.length() <= 6) {
            return false;
        }
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasSpecialCharacter = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (isSpecialCharacter(c)) {
                hasSpecialCharacter = true;
            }
        }
        return hasUppercase && hasLowercase && hasSpecialCharacter;
    }
    private boolean isSpecialCharacter(char c) {
        String specialCharacters = "@#$%^&+=!";
        return specialCharacters.contains(String.valueOf(c));
    }
    private void HandelChange(long idUser, String Password){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseAll> call = apiService.changePassword(Password,idUser);
        call.enqueue(new Callback<ResponseAll>() {
            @Override
            public void onResponse(Call<ResponseAll> call, Response<ResponseAll> response) {
                if (response.isSuccessful()) {
                    ResponseAll result = response.body();
                    if (result.isSuccess()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                        builder.setMessage("Đổi Mật Khẩu Thành Công. Đang đăng xuất sau:");
                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        new CountDownTimer(5000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                int secondsRemaining = (int) millisUntilFinished / 1000;
                                alertDialog.setMessage("Đang đăng xuất sau: " + secondsRemaining + " giây");
                            }
                            @Override
                            public void onFinish() {
                                alertDialog.dismiss();
                                finishAffinity();
                                System.exit(0);
                            }
                        }.start();

                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAll> call, Throwable t) {
                Log.e("TAG", "onFailure: ",t );
            }
        });
    }
}