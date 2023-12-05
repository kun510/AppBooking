package com.doancuoinam.hostelappdoancuoinam.view.profile.language;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.fragment.profile.Profile;

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
        Toolbar toolbar = findViewById(R.id.toolbar);

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
        int nameTitle = R.string.Language;
        setToolbar(toolbar,nameTitle);
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
    private void setToolbar(Toolbar toolbar, int name){
        setSupportActionBar(toolbar);
        String title = getString(name);
        SpannableString spannableString = new SpannableString(title);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(spannableString);
        toolbar.setNavigationIcon(R.drawable.backdetail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewFragment();
            }
        });

    }
    private void startNewFragment() {
        Profile newFragment = new Profile();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}