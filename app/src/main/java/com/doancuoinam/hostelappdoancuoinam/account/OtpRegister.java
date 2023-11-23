package com.doancuoinam.hostelappdoancuoinam.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

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
                getOtp();
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

    private void getOtp() {
        String  codeOtp1 = Otp1.getText().toString();
        String  codeOtp2 = Otp2.getText().toString();
        String  codeOtp3 = Otp3.getText().toString();
        String  codeOtp4 = Otp4.getText().toString();
        String  codeOtp5 = Otp5.getText().toString();
        String  codeOtp6 = Otp6.getText().toString();
        if (codeOtp1.trim().isEmpty() || codeOtp2.trim().isEmpty() || codeOtp3.trim().isEmpty() || codeOtp4.trim().isEmpty()
                || codeOtp5.trim().isEmpty() || codeOtp6.trim().isEmpty()) {
            Toast.makeText(OtpRegister.this, "Điền Đầy Đủ", Toast.LENGTH_SHORT).show();
        }
        String code = codeOtp1 + codeOtp2 + codeOtp3 + codeOtp4 + codeOtp5 + codeOtp6;
        if (verificationId != null) {
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code);
            FirebaseAuth.getInstance().setLanguageCode("Vi");
            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
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
                    } else {
                        Toast.makeText(OtpRegister.this, "Otp sai", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        setupOTP();
    }
    private void setupOTP(){
        Otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    Otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    Otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    Otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    Otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    Otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Otp6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    Otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}