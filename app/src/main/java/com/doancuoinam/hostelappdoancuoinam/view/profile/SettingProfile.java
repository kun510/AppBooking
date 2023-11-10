package com.doancuoinam.hostelappdoancuoinam.view.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.doancuoinam.hostelappdoancuoinam.R;

public class SettingProfile extends AppCompatActivity {
    LinearLayout editProfile,changePass,TurnNoti,about,privacy,terms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);
    }
}