package com.doancuoinam.hostelappdoancuoinam.View.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.doancuoinam.hostelappdoancuoinam.R;

public class ScreenIntro1 extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_intro1);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        progressBar.setMax(100);
        final Handler handler = new Handler();
        handler.postDelayed(() -> loadNextScreen(), 6000);
    }
    private void loadNextScreen() {
        Intent intent = new Intent(ScreenIntro1.this, ScreenIntro2.class);
        startActivity(intent);
        finish();
    }
}
