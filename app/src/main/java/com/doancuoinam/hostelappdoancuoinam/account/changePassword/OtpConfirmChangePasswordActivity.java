package com.doancuoinam.hostelappdoancuoinam.account.changePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.R;

public class OtpConfirmChangePasswordActivity extends AppCompatActivity {
    EditText Input_Otp1,Input_Otp2,Input_Otp3,Input_Otp4,Input_Otp5,Input_Otp6;
    TextView some_id,resetOtp;
    Button confirmOtp;
    ProgressBar progress_otp;
    String otp1,otp2,otp3,otp4,otp5,otp6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_confirm_change_password);
        Mapping();
        progress_otp.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String otp = sharedPreferences.getString("otp", "");
        String email = sharedPreferences.getString("email","");
        some_id.setText(email);


        confirmOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetOtp();
                String allOtp = otp1+otp2+otp3+otp4+otp5+otp6;
                if (allOtp.equals(otp)){
                    Toast.makeText(OtpConfirmChangePasswordActivity.this, "Xác Nhận Thành Công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OtpConfirmChangePasswordActivity.this,ChangePasswordActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(OtpConfirmChangePasswordActivity.this, "Nhập sai mã OTP", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void GetOtp(){
        otp1 = Input_Otp1.getText().toString();
        otp2 = Input_Otp2.getText().toString();
        otp3 = Input_Otp3.getText().toString();
        otp4 = Input_Otp4.getText().toString();
        otp5 = Input_Otp5.getText().toString();
        otp6 = Input_Otp6.getText().toString();

    }

    private void Mapping(){
        Input_Otp1 = findViewById(R.id.Input_Otp1);
        Input_Otp2 = findViewById(R.id.Input_Otp2);
        Input_Otp3 = findViewById(R.id.Input_Otp3);
        Input_Otp4 = findViewById(R.id.Input_Otp4);
        Input_Otp5 = findViewById(R.id.Input_Otp5);
        Input_Otp6 = findViewById(R.id.Input_Otp6);
        some_id = findViewById(R.id.some_id);
        resetOtp = findViewById(R.id.resetOtp);
        confirmOtp = findViewById(R.id.confirmOtp);
        progress_otp = findViewById(R.id.progress_otp);
    }
}