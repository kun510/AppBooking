package com.doancuoiky.hostelkun.view.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.doancuoiky.hostelkun.R;
import com.doancuoiky.hostelkun.view.home.Home;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Search extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bottomNavigationView = findViewById(R.id.menu);
        bottomNavigationView.setSelectedItemId(R.id.menu_search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_Home:
                        startActivity(new Intent(Search.this, Home.class));
                        return true;
                    case R.id.menu_search:
                        return true;
                    case R.id.menu_mess:
                        return true;
                    case R.id.menu_notification:
                        return true;
                    case R.id.menu_user:
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}