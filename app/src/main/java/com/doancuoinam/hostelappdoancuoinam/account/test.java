package com.doancuoinam.hostelappdoancuoinam.account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.doancuoinam.hostelappdoancuoinam.view.user.map.GeocodingTask;
import com.doancuoinam.hostelappdoancuoinam.R;

public class test extends AppCompatActivity {
EditText txtAdrr;
    Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        txtAdrr = findViewById(R.id.txtAdrr);
        ok = findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = txtAdrr.getText().toString();
                GeocodingTask geocodingTask = new GeocodingTask(test.this, new GeocodingTask.GeocodingListener() {
                    @Override
                    public void onGeocodingResult(double latitude, double longitude) {
                        Log.d("GeocodingResult", "Latitude: " + latitude + ", Longitude: " + longitude);
                    }

                    @Override
                    public void onGeocodingFailed() {
                        Log.e("GeocodingResult", "Failed to get coordinates from address");
                    }
                });
                geocodingTask.execute(a);
            }
        });
    }
}