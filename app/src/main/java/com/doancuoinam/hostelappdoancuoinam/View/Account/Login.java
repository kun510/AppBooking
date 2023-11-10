package com.doancuoinam.hostelappdoancuoinam.view.account;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.BaseActivity;
import com.doancuoinam.hostelappdoancuoinam.Model.Request.LoginRequest;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.Response;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseLogin;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.view.intro.ChoseAreaActivity;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;

public class Login extends AppCompatActivity {
    EditText phone,pass;
    TextView fogot;
    Button btn_login;
    public static final String ChannelID = "default_channel_id" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createChannelNotification();
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
        Call<ResponseLogin> call = apiService.login(loginRequest);

        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, retrofit2.Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    ResponseLogin loginResponse = response.body();
                    String message = loginResponse.getMessage();
                    long userId = loginResponse.getUserId();
                    FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
                        saveTokenToDatabase(userId, token);
                        Log.d("TAG", "onResponse: "+ token);
                    });
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("userId", userId);
                    editor.apply();
                    if (message.equals("Admin")) {
                        Toast.makeText(Login.this, "Admin", Toast.LENGTH_SHORT).show();
                    } else if (message.equals("Host")) {
                        Toast.makeText(Login.this, "Host", Toast.LENGTH_SHORT).show();
                    } else if (message.equals("User")) {
                        Intent intent = new Intent(Login.this, ChoseAreaActivity.class);
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
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
              //  Log.e("cHcww", "onFailure: "+ t.getMessage()  );
                Toast.makeText(Login.this, "Lỗi kết nối mạng hoặc máy chủ không phản hồi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveTokenToDatabase(long userId, String token){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Response> call = apiService.addToken(token,userId);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    Response result = response.body();
                    if (result != null && result.isSuccess()) {
                        Toast.makeText(Login.this, "token OK", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, "token fail", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "fail query", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                if (t instanceof IOException) {
                    //  Log.e("cHcww", "onFailure: "+ t.getMessage()  );
                    Toast.makeText(Login.this, "Lỗi kết nối mạng hoặc máy chủ không phản hồi", Toast.LENGTH_SHORT).show();
                } else {
                   // Toast.makeText(Login.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void createChannelNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(ChannelID,"default_channel_id",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
    }
}