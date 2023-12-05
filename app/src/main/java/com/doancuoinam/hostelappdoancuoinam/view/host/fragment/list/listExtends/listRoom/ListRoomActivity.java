package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listRoom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.empty.EmptyActivity;
import com.doancuoinam.hostelappdoancuoinam.view.host.addRoom.AddRoomByHostActivity;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listRoomEmpty.ListRoomAdapter;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRoomActivity extends AppCompatActivity {
    RecyclerView listRoom;
    ListRoomAdapter listRoomAdapter;
    ProgressBar progressBar;
    ImageView addRoomByHost;
    String boardingId;
    LottieAnimationView empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room);
        listRoom = findViewById(R.id.listRoom);
        addRoomByHost = findViewById(R.id.addRoomByHost);
        empty = findViewById(R.id.empty);
        listRoom.setLayoutManager(new LinearLayoutManager(ListRoomActivity.this,RecyclerView.VERTICAL, false));
        listRoomAdapter = new ListRoomAdapter();
        listRoom.setAdapter(listRoomAdapter);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", 0);
        Intent intent = getIntent();
        boardingId =  intent.getStringExtra("BoardingId");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Room>> call = apiService.AllRoomByHost(userId,Long.parseLong(boardingId));
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    List<Room> rooms = response.body();
                    Log.d("TAG", "API Call: " + rooms.get(0).getId());
                    progressBar.setVisibility(View.GONE);
                    Log.e("API Call", "ok");
                    if (rooms.isEmpty()){
                        listRoom.setVisibility(View.GONE);
                        empty.setVisibility(View.VISIBLE);
                    }else {
                        listRoom.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.GONE);
                        listRoomAdapter.setRooms(rooms,ListRoomActivity.this);
                    }
                } else {
                    listRoom.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("API Call", errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("API Call", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ListRoomActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API Call", "Failed", t);
            }
        });
        addRoomByHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ListRoomActivity.this, AddRoomByHostActivity.class);
                intent1.putExtra("boardingId",boardingId);
                startActivity(intent1);
            }
        });
    }
}