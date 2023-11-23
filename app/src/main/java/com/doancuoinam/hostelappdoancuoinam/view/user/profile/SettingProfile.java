package com.doancuoinam.hostelappdoancuoinam.view.user.profile;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class SettingProfile extends AppCompatActivity {
    LinearLayout editProfile,changePass,TurnNoti,about,privacy,terms;
    ShapeableImageView avt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);
        avt = findViewById(R.id.avtProfile);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String imageUrl = sharedPreferences.getString("avt", "");
        Picasso.get().load(imageUrl).into(avt);
    }
}