package com.doancuoinam.hostelappdoancuoinam.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.doancuoinam.hostelappdoancuoinam.R;

import java.util.Timer;
import java.util.TimerTask;

public class LoaddingHome extends AppCompatActivity {

    ProgressBar progressBar;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadding_home);
        Load();
        Timer timenext = new Timer();
        timenext.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(LoaddingHome.this, ChooseLoginType.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
    public void Load(){
        progressBar = findViewById(R.id.progressBar);
        progressBar.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                count++;
                progressBar.setProgress(count);
            }
        };

    }
}