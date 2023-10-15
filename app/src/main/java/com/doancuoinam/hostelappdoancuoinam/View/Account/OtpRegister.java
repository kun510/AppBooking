package com.doancuoinam.hostelappdoancuoinam.View.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.Request.RegisterRequest;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class OtpRegister extends AppCompatActivity {
    Button btn_login_otp;
    EditText Otp1,Otp2,Otp3,Otp4,Otp5,Otp6;
    TextView some_id,resetOtp;
    ProgressBar progress_otp;
    String  verificationId,phoneNumber,pass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_register);
        AnhXa();
        Intent intent = getIntent();
        progress_otp.setVisibility(View.INVISIBLE);
        phoneNumber = intent.getStringExtra("numberphone");
        verificationId = intent.getStringExtra("verificationId");
        pass = intent.getStringExtra("pass");
        some_id.setText(phoneNumber);
        btn_login_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_otp.setVisibility(View.VISIBLE);
                btn_login_otp.setVisibility(View.INVISIBLE);
                String phoneApi = phoneNumber;
                String passwordApi = pass;
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                RegisterRequest registerRequest = new RegisterRequest(phoneApi,passwordApi);
                Call<Response> call = apiService.register(registerRequest);
                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {
                            Response registerResponse = response.body();
                            if (registerResponse != null) {
                                String message = registerResponse.getMessage();
                                if (message != null) {
                                    if (message.equals("Register successfully")) {
                                        Toast.makeText(OtpRegister.this, "Register successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(OtpRegister.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (message.equals("User with the provided phone already exists.")) {
                                        Toast.makeText(OtpRegister.this, "User with the provided phone already exists." + message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(OtpRegister.this, "Register Fail " + message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(OtpRegister.this, "HTTP Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Toast.makeText(OtpRegister.this, "Error HTTP: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void AnhXa(){
        Otp1 = findViewById(R.id.Input_Otp1);
        Otp2 = findViewById(R.id.Input_Otp2);
        Otp3 = findViewById(R.id.Input_Otp3);
        Otp4 = findViewById(R.id.Input_Otp4);
        Otp5 = findViewById(R.id.Input_Otp5);
        Otp6 = findViewById(R.id.Input_Otp6);
        resetOtp = findViewById(R.id.resetOtp);
        progress_otp = findViewById(R.id.progress_otp);
        btn_login_otp = findViewById(R.id.btn_login_otp);
        some_id = findViewById(R.id.some_id);
    }
}