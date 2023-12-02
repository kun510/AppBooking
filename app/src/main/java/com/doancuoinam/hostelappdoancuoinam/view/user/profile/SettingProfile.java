package com.doancuoinam.hostelappdoancuoinam.view.user.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseOtp;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.account.Login;
import com.doancuoinam.hostelappdoancuoinam.account.changePassword.OtpConfirmChangePasswordActivity;
import com.doancuoinam.hostelappdoancuoinam.updateUser.UpdateUser;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingProfile extends AppCompatActivity {
    LinearLayout editProfile,changePass,TurnNoti,about,privacy,terms,logout;
    ShapeableImageView avt;
    TextView nameUser;
    ProgressBar progress_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);
        editProfile = findViewById(R.id.editProfile);
        changePass = findViewById(R.id.changePass);
        avt = findViewById(R.id.avtProfile);
        nameUser = findViewById(R.id.nameUser);
        logout = findViewById(R.id.logout);
        progress_otp = findViewById(R.id.progress_otp);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String imageUrl = sharedPreferences.getString("avt", "");
        Picasso.get().load(imageUrl).into(avt);
        String name = sharedPreferences.getString("name","");
        nameUser.setText(name);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingProfile.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingProfile.this, UpdateUser.class);
                startActivity(intent);
            }
        });
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress_otp.setVisibility(View.VISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingProfile.this);
                builder.setTitle("Xác nhận thay đổi mật khẩu");
                builder.setMessage("Bạn có chắc chắn muốn thay đổi mật khẩu không?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = sharedPreferences.getString("email","");
                        ApiService apiService = ApiClient.getClient().create(ApiService.class);
                        Call<ResponseOtp> call = apiService.sendMailChangePassword(email);
                        call.enqueue(new Callback<ResponseOtp>() {
                            @Override
                            public void onResponse(Call<ResponseOtp> call, Response<ResponseOtp> response) {
                                if (response.isSuccessful()) {
                                    ResponseOtp responseOtp = response.body();
                                    if (responseOtp.isSuccess()) {
                                        String Otp =  responseOtp.getOtp();
                                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("otp", Otp);
                                        editor.apply();
                                        Intent intent = new Intent(SettingProfile.this, OtpConfirmChangePasswordActivity.class);
                                        startActivity(intent);
                                        progress_otp.setVisibility(View.GONE);
                                    } else {
                                        progress_otp.setVisibility(View.GONE);
                                        Toast.makeText(SettingProfile.this, "sendMail Fail", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progress_otp.setVisibility(View.GONE);
                                    Toast.makeText(SettingProfile.this, "Lỗi Mail", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseOtp> call, Throwable t) {
                                Log.d("TAG", "senMail: " + t);
                                progress_otp.setVisibility(View.GONE);

                            }
                        });

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}