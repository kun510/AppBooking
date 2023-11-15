package com.doancuoinam.hostelappdoancuoinam.view.profile.myroom.overviewMyyRoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.profile.myroom.billInRoom.BillMyRoom;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class ChoseOverviewMyRoom extends AppCompatActivity {
    LinearLayout BillMyRoomLayout;
    ShapeableImageView avt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_overview_my_room);
        BillMyRoomLayout = findViewById(R.id.BillMyRoomLayout);
        avt = findViewById(R.id.avtProfile);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String imageUrl = sharedPreferences.getString("avt", "");
        Picasso.get().load(imageUrl).into(avt);
        BillMyRoomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoseOverviewMyRoom.this, BillMyRoom.class);
                startActivity(intent);
            }
        });
    }
}