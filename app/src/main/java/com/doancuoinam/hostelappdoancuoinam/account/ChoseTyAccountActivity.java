package com.doancuoinam.hostelappdoancuoinam.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.test.ChooseLoginType;

public class ChoseTyAccountActivity extends AppCompatActivity {
    TextView Host,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_ty_account);
        Host = findViewById(R.id.Host);
        user = findViewById(R.id.user);

        Host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextHost = new Intent(ChoseTyAccountActivity.this, RegisterHost.class);
                startActivity(nextHost);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextUser = new Intent(ChoseTyAccountActivity.this, Register.class);
                startActivity(nextUser);
            }
        });
    }
}