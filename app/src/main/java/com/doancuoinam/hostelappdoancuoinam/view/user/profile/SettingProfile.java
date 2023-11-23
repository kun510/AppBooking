package com.doancuoinam.hostelappdoancuoinam.view.user.profile;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.updateUser.UpdateUser;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class SettingProfile extends AppCompatActivity {
    LinearLayout editProfile,changePass,TurnNoti,about,privacy,terms;
    ShapeableImageView avt;
    TextView nameUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);
        editProfile = findViewById(R.id.editProfile);
        avt = findViewById(R.id.avtProfile);
        nameUser = findViewById(R.id.nameUser);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String imageUrl = sharedPreferences.getString("avt", "");
        Picasso.get().load(imageUrl).into(avt);
        String name = sharedPreferences.getString("name","");
        nameUser.setText(name);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingProfile.this, UpdateUser.class);
                startActivity(intent);
            }
        });
    }
}