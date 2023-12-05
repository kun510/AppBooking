package com.doancuoinam.hostelappdoancuoinam.view.user.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.account.ChoseTyAccountActivity;
import com.doancuoinam.hostelappdoancuoinam.account.Register;

public class ScreenIntro4 extends AppCompatActivity {
    ImageView intro1,intro2,intro3;
    Button btn_intro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_intro4);
        View[] views = UtilsIntro.AnhXa(this);
        UtilsIntro utils = new UtilsIntro();
        utils.ListenClick(views, this);
        btn_intro = findViewById(R.id.btn_intro);
        btn_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenIntro4.this, ChoseTyAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}