package com.doancuoinam.hostelappdoancuoinam.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpPhone extends AppCompatActivity {

    private  EditText Otp1,Otp2,Otp3,Otp4,Otp5,Otp6;
    TextView phone_otp;
    String numberphone = "";
    String verificationId = "";
    ImageView back;
    Button Btn_checkotp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_phone);

        Anhxa();
        numberphone = getIntent().getStringExtra("numberphone");
        phone_otp.setText(numberphone);
        verificationId = getIntent().getStringExtra("verificationId");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpPhone.this, LoginSmS.class);
                startActivity(intent);
            }
        });
        Btn_checkotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeOtp1 =Otp1.getText().toString();
                String codeOtp2 =Otp2.getText().toString();
                String codeOtp3 =Otp3.getText().toString();
                String codeOtp4 =Otp4.getText().toString();
                String codeOtp5 =Otp5.getText().toString();
                String codeOtp6 =Otp6.getText().toString();
                if(codeOtp1.trim().isEmpty()||codeOtp2.trim().isEmpty()||codeOtp3.trim().isEmpty()||codeOtp4.trim().isEmpty()
                ||codeOtp5.trim().isEmpty()||codeOtp6.trim().isEmpty()){
                    Toast.makeText(OtpPhone.this, "Điền Đầy Đủ", Toast.LENGTH_SHORT).show();
                }
                String code = codeOtp1 + codeOtp2  + codeOtp3 + codeOtp4 + codeOtp5 + codeOtp6;
                if (verificationId != null){
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential( verificationId, code);
                    FirebaseAuth.getInstance().setLanguageCode("Vi");
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(OtpPhone.this, LoginSmS.class);
                                Toast.makeText(OtpPhone.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(OtpPhone.this, "Otp sai", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        setupOTP();


    }

    private void Anhxa(){
        Otp1 = findViewById(R.id.Input_Otp1);
        Otp2 = findViewById(R.id.Input_Otp2);
        Otp3 = findViewById(R.id.Input_Otp3);
        Otp4 = findViewById(R.id.Input_Otp4);
        Otp5 = findViewById(R.id.Input_Otp5);
        Otp6 = findViewById(R.id.Input_Otp6);
        phone_otp = findViewById(R.id.phone_otp);
        back = findViewById(R.id.back);
        Btn_checkotp = findViewById(R.id.Btn_checkotp);

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