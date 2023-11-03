package com.doancuoinam.hostelappdoancuoinam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.doancuoinam.hostelappdoancuoinam.View.Fragment.Home.HomeFragment;
import com.doancuoinam.hostelappdoancuoinam.View.Fragment.Notifications;
import com.doancuoinam.hostelappdoancuoinam.View.Fragment.Search.Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        BottomNavigationView bottomNavigationView = findViewById(R.id.menu);
        final Fragment homeFragment = new HomeFragment();
        final Fragment searchFragment = new Search();
        final Fragment NotificationFragment = new Notifications();


        loadFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.menu_Home:
                        selectedFragment = homeFragment;
                        break;
                    case R.id.menu_search:
                        selectedFragment = searchFragment;
                        break;
                    case R.id.menu_notification:
                        selectedFragment = NotificationFragment;
                        break;
                }
                loadFragment(selectedFragment);
                return true;
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}