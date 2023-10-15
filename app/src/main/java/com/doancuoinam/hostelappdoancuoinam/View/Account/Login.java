package com.doancuoinam.hostelappdoancuoinam.View.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.BaseActivity;
import com.doancuoinam.hostelappdoancuoinam.Model.Request.LoginRequest;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.Response;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;

public class Login extends AppCompatActivity {
    EditText phone,pass;
    TextView fogot;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtPhone = phone.getText().toString();
                String txtPass = pass.getText().toString();
                loginUser(txtPhone,txtPass);
            }
        });
    }
    private void loginUser(String phoneNumber, String password) {
        LoginRequest loginRequest = new LoginRequest(phoneNumber, password);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Response> call = apiService.login(loginRequest);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    Response loginResponse = response.body();
                    String message = loginResponse.getMessage();
                    if (message.equals("Admin")) {
                        Toast.makeText(Login.this, "Admin", Toast.LENGTH_SHORT).show();
                    } else if (message.equals("Host")) {
                        Toast.makeText(Login.this, "Host", Toast.LENGTH_SHORT).show();
                    } else if (message.equals("User")) {
                        Intent intent = new Intent(Login.this, BaseActivity.class);
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Unknown role or error", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                if (t instanceof IOException) {
                  //  Log.e("cHcww", "onFailure: "+ t.getMessage()  );
                    Toast.makeText(Login.this, "Lỗi kết nối mạng hoặc máy chủ không phản hồi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                  //  Log.e("cHcww", "onFailure: "+ t.getMessage());
                    if (t.getMessage() != null) {
                        Toast.makeText(Login.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, "Lỗi không xác định", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

}