package com.doancuoinam.hostelappdoancuoinam.view.room.roomList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.view.room.roomListSearch.RoomAdapter;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RoomListByBoarding extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterListRoomByBoarding roomAdapter;
    ProgressBar progressBar;
    String nameBoarding,idBoarding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list_by_boarding);
        Toolbar toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBarList);
        recyclerView = findViewById(R.id.recyclerRoomDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(RoomListByBoarding.this,RecyclerView.VERTICAL, false));
        roomAdapter = new AdapterListRoomByBoarding();
        recyclerView.setAdapter(roomAdapter);
        Intent intent = getIntent();
        nameBoarding = intent.getStringExtra("nameboarding");
        idBoarding = intent.getStringExtra("idBoarding");
        setToolbar(toolbar,nameBoarding);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Room>> call = apiService.allRoomByBoarding(Long.parseLong(idBoarding));
        showProgressBar();
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    List<Room> rooms = response.body();
                    roomAdapter.setRoomsList(rooms);

                } else {
                    Toast.makeText(RoomListByBoarding.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(RoomListByBoarding.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailureRoomaaaaa: " + t.getMessage());
                hideProgressBar();
            }
        });
    }
    private void setToolbar(Toolbar toolbar,String name){
        setSupportActionBar(toolbar);
        SpannableString spannableString = new SpannableString(name);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(spannableString);
        toolbar.setNavigationIcon(R.drawable.backdetail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}