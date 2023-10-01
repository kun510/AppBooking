package com.doancuoiky.hostelkun.view.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doancuoiky.hostelkun.R;
import com.doancuoiky.hostelkun.view.BaseActivity;
import com.doancuoiky.hostelkun.view.home.Home;

public class OtpLogin extends AppCompatActivity {

    Button btn_login_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);
        btn_login_otp = findViewById(R.id.btn_login_otp);
        btn_login_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpLogin.this, BaseActivity.class);
                startActivity(intent);
            }
        });
    }
}