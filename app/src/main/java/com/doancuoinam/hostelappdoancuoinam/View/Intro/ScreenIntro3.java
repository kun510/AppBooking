package com.doancuoinam.hostelappdoancuoinam.View.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.doancuoinam.hostelappdoancuoinam.R;

public class ScreenIntro3 extends AppCompatActivity {
    ImageView intro1,intro2,intro3;
    Button btn_intro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_intro3);
        View[] views = UtilsIntro.AnhXa(this);
        UtilsIntro utils = new UtilsIntro();
        utils.ListenClick(views, this);
        btn_intro = findViewById(R.id.btn_intro);
        btn_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenIntro3.this, ScreenIntro4.class);
                startActivity(intent);
                finish();
            }
        });
    }
}