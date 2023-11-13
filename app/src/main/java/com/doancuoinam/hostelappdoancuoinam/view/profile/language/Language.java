package com.doancuoinam.hostelappdoancuoinam.view.profile.language;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.doancuoinam.hostelappdoancuoinam.R;

public class Language extends AppCompatActivity {
    LinearLayout vietnam,japan,china,english;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        vietnam = findViewById(R.id.vietnam);
        japan = findViewById(R.id.nhatban);
        china = findViewById(R.id.trungquoc);
        english = findViewById(R.id.tienganh);

        vietnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor(vietnam);
                changeLanguage("vi");
            }
        });

        japan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor(japan);
                changeLanguage("ja");
            }
        });

        china.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor(china);
                changeLanguage("zh");
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor(english);
                changeLanguage("en");
            }
        });
    }
    private void changeColor(LinearLayout layout) {
        layout.setBackgroundColor(Color.GREEN);
    }
    private void changeLanguage(String languageCode) {
        LanguageManager.setLanguage(this, languageCode);
        Intent intent = new Intent(this, Language.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}