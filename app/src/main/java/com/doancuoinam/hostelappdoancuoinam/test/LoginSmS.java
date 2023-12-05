package com.doancuoinam.hostelappdoancuoinam.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginSmS extends AppCompatActivity {
 Button Btn_checkotp;
 EditText Input_PhoneNumber;

    private FirebaseAuth mAuth;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sm_s);

        Anhxa();
        Btn_checkotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Input_PhoneNumber.getText().toString().trim().isEmpty()){
                    Toast.makeText(LoginSmS.this, "Nhập đầy đủ", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Phonenumber =  Input_PhoneNumber.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                       "+84" + Phonenumber,60, TimeUnit.SECONDS,
                        LoginSmS.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.e("LoginSmS", "onVerificationFailed", e);
                                Toast.makeText(LoginSmS.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationId = s;
                                resendToken = forceResendingToken;
                                Intent intent = new Intent(LoginSmS.this, OtpPhone.class);
                                intent.putExtra("numberphone",Input_PhoneNumber.getText().toString());
                                intent.putExtra("verificationId",verificationId);
                                startActivity(intent);
                            }
                        }
                );



            }
        });
    }

    public void Anhxa(){
        Btn_checkotp = findViewById(R.id.Btn_checkotp);
        Input_PhoneNumber = findViewById(R.id.Input_PhoneNumber);
        mAuth = FirebaseAuth.getInstance();
    }
    public void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // OTP verification is successful, the user is signed in
                            Toast.makeText(LoginSmS.this, "Verification successful!", Toast.LENGTH_SHORT).show();
                        } else {
                            // OTP verification failed, show an error message
                            Toast.makeText(LoginSmS.this, "Verification failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}