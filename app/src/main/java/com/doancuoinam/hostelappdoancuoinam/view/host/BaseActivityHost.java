package com.doancuoinam.hostelappdoancuoinam.view.host;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.home.HomeFragmentHost;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.ListFragment;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.profile.ProfileHost;
import com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.Message;
import com.doancuoinam.hostelappdoancuoinam.view.user.fragment.notification.Notifications;
import com.doancuoinam.hostelappdoancuoinam.view.user.fragment.profile.Profile;
import com.doancuoinam.hostelappdoancuoinam.view.user.fragment.search.Search;
import com.doancuoinam.hostelappdoancuoinam.view.user.profile.language.LanguageManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivityHost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_host);
        BottomNavigationView bottomNavigationView = findViewById(R.id.menu);
        LanguageManager.applySavedLanguage(BaseActivityHost.this);
        final Fragment homeFragment = new HomeFragmentHost();
        final Fragment listFragment = new ListFragment();
        final Fragment NotificationFragment = new Notifications();
        final Fragment Message = new Message();
        final Fragment Profile = new ProfileHost();

        loadFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.menu_Home:
                        selectedFragment = homeFragment;
                        break;
                    case R.id.add:
                        selectedFragment = listFragment;
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