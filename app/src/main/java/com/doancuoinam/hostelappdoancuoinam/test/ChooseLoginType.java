package com.doancuoinam.hostelappdoancuoinam.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.doancuoinam.hostelappdoancuoinam.R;

public class ChooseLoginType extends AppCompatActivity {
    LinearLayout Login_SMS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_type);
        Anhxa();

        Login_SMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLoginType.this, LoginSmS.class);
                startActivity(intent);
            }
        });
    }

    public void Anhxa(){
        Login_SMS = findViewById(R.id.Login_SMS);

    }
}
