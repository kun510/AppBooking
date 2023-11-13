package com.doancuoinam.hostelappdoancuoinam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.doancuoinam.hostelappdoancuoinam.view.fragment.home.HomeFragment;
import com.doancuoinam.hostelappdoancuoinam.view.fragment.notification.Notifications;
import com.doancuoinam.hostelappdoancuoinam.view.fragment.message.Message;
import com.doancuoinam.hostelappdoancuoinam.view.fragment.profile.Profile;
import com.doancuoinam.hostelappdoancuoinam.view.fragment.search.Search;
import com.doancuoinam.hostelappdoancuoinam.view.profile.language.Language;
import com.doancuoinam.hostelappdoancuoinam.view.profile.language.LanguageManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        BottomNavigationView bottomNavigationView = findViewById(R.id.menu);
        LanguageManager.applySavedLanguage(BaseActivity.this);
        final Fragment homeFragment = new HomeFragment();
        final Fragment searchFragment = new Search();
        final Fragment NotificationFragment = new Notifications();
        final Fragment Message = new Message();
       // final Fragment Message = new Map();
        final Fragment Profile = new Profile();

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
                    case R.id.menu_mess:
                        selectedFragment = Message;
                        break;
                    case R.id.menu_user:
                        selectedFragment = Profile;
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
    public long getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getLong("userId", 0);
    }
}