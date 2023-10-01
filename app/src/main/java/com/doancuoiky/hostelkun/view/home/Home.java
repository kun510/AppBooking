package com.doancuoiky.hostelkun.view.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.doancuoiky.hostelkun.R;
import com.doancuoiky.hostelkun.view.search.Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.menu);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_Home:
                        return true;
                    case R.id.menu_search:
                        startActivity(new Intent(Home.this, Search.class));
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