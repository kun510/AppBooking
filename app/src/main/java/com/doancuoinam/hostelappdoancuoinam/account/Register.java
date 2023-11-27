package com.doancuoinam.hostelappdoancuoinam.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.MemoryData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {
    TextView doHave;
    Button btn_register;
    EditText phone,pass,confirmPass,email,nameUser;
    private FirebaseAuth mAuth;
    String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hostelappdoancuoinam-default-rtdb.firebaseio.com/");
    ProgressBar progress_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
        progress_otp.setVisibility(View.INVISIBLE);
        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        doHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_otp.setVisibility(View.VISIBLE);
                btn_register.setVisibility(View.INVISIBLE);
                String numberPhone = phone.getText().toString();
                String password = pass.getText().toString();
                String confirm = confirmPass.getText().toString();

                if (numberPhone.trim().isEmpty() || password.trim().isEmpty()) {
                    Toast.makeText(Register.this, R.string.nhapdaydu, Toast.LENGTH_SHORT).show();
                    progress_otp.setVisibility(View.INVISIBLE);
                    btn_register.setVisibility(View.VISIBLE);
                    return;
                }
                if(!isStrongPassword(password)){
                    Toast.makeText(Register.this, R.string.nhapformat, Toast.LENGTH_SHORT).show();
                    progress_otp.setVisibility(View.INVISIBLE);
                    btn_register.setVisibility(View.VISIBLE);
                    return;
                }
                if (!confirm.equals(password)) {
                    Toast.makeText(Register.this, R.string.nhaplai, Toast.LENGTH_SHORT).show();
                    progress_otp.setVisibility(View.INVISIBLE);
                    btn_register.setVisibility(View.VISIBLE);
                    return;
                }

//                Intent intent = new Intent(Register.this, OtpRegister.class);
//                intent.putExtra("numberphone",numberPhone);
//                intent.putExtra("verificationId",mVerificationId);
//                intent.putExtra("pass",password);
//                startActivity(intent);
                onClickVerifyPhoneNumber(numberPhone);
            }
        });
    }
    private void AnhXa(){
        doHave = findViewById(R.id.doHave);
        btn_register = findViewById(R.id.btn_register);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        confirmPass = findViewById(R.id.confirm);
        mAuth = FirebaseAuth.getInstance();
        progress_otp = findViewById(R.id.progress_otp);
        email = findViewById(R.id.email);
        nameUser = findViewById(R.id.nameUser);
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
    public void onClickVerifyPhoneNumber(String numberPhone){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(numberPhone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setForceResendingToken(resendToken)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Log.d("TAG", "onVerificationCompleted: " + phoneAuthCredential);
                                progress_otp.setVisibility(View.GONE);
                                btn_register.setVisibility(View.INVISIBLE);
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.e("LoginSmS", "onVerificationFailed", e);
                                progress_otp.setVisibility(View.INVISIBLE);
                                btn_register.setVisibility(View.VISIBLE);
                                Toast.makeText(Register.this, "Verification FailedD", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                String numberPhone = phone.getText().toString();
                                String password = pass.getText().toString();
                                String confirm = confirmPass.getText().toString();
                                String emailUser = email.getText().toString();
                                String nameUsers = nameUser.getText().toString();
                                progress_otp.setVisibility(View.GONE);
                                btn_register.setVisibility(View.INVISIBLE);
                                mVerificationId = verificationId;
                                resendToken = forceResendingToken;
                                if (confirm.equals(password)){
//                                    Intent intent = new Intent(Register.this, OtpRegister.class);
//                                    intent.putExtra("numberphone",numberPhone);
//                                    intent.putExtra("verificationId",mVerificationId);
//                                    intent.putExtra("pass",password);
//                                    startActivity(intent);
                                    saveDataFirebase(numberPhone,emailUser,nameUsers,password,mVerificationId);
                                }
                                else {
                                    Toast.makeText(Register.this, R.string.nhaplai, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    public void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Verification successful!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Register.this, "Verification failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void saveDataFirebase(String phone,String email,String name,String password  ,String mVerificationId){
        if (!MemoryData.getData(this).isEmpty()){
            Intent intent = new Intent(Register.this, OtpRegister.class);
            intent.putExtra("numberphone",MemoryData.getData(this));
            intent.putExtra("email","");
            intent.putExtra("name",MemoryData.getName(this));
            intent.putExtra("verificationId",mVerificationId);
            intent.putExtra("pass",password);
            startActivity(intent);
            finish();
        }
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        DatabaseReference userReference = databaseReference.child(phone);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                if (snapshot.child("users").hasChild(phone)){
                    Toast.makeText(Register.this, "Mobile already exists", Toast.LENGTH_SHORT).show();
                }else {
                    userReference.child("email").setValue(email);
                    userReference.child("name").setValue(name);
                    MemoryData.saveData(phone,Register.this);
                    MemoryData.saveName(name,Register.this);
                    Toast.makeText(Register.this, "Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, OtpRegister.class);
                    intent.putExtra("numberphone",phone);
                    intent.putExtra("email",email);
                    intent.putExtra("name",name);
                    intent.putExtra("verificationId",mVerificationId);
                    intent.putExtra("pass",password);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Log.e("FirebaseError", "Database operation canceled", error.toException());
            }
        });
    }
}