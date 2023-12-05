package com.doancuoinam.hostelappdoancuoinam.view.profile.roomFavourite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.RoomFavourite;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomFavouriteActivity extends AppCompatActivity {

    RecyclerView recyclerRoomFavourite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_favourite);
        Toolbar toolbar = findViewById(R.id.toolbar);
        int nameTitle = R.string.favorite;
        setToolbar(toolbar,nameTitle);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        long userID = sharedPreferences.getLong("userId", 0);
        Toast.makeText(this, ""+userID, Toast.LENGTH_SHORT).show();
        recyclerRoomFavourite = findViewById(R.id.recyclerRoomFavourite);
        recyclerRoomFavourite.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        RoomFavouriteAdapter roomFavouriteAdapter = new RoomFavouriteAdapter();
        recyclerRoomFavourite.setAdapter(roomFavouriteAdapter);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<RoomFavourite>> call = apiService.getAllRoomFavourite(userID);

        call.enqueue(new Callback<List<RoomFavourite>>() {
            @Override
            public void onResponse(Call<List<RoomFavourite>> call, Response<List<RoomFavourite>> response) {
                if (response.isSuccessful()) {
                    List<RoomFavourite> rooms = response.body();
                    roomFavouriteAdapter.setRoomsFavouriteList(rooms);
                } else {
                    Toast.makeText(RoomFavouriteActivity.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RoomFavourite>> call, Throwable t) {
                Toast.makeText(RoomFavouriteActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailureRoomFavourite: " + t.getMessage());
            }
        });

    }
    private void setToolbar(Toolbar toolbar, int name){
        setSupportActionBar(toolbar);
        String title = getString(name);
        SpannableString spannableString = new SpannableString(title);
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
}