package com.doancuoinam.hostelappdoancuoinam.view.profile.myroom;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Rent;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.RoomFavourite;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.view.profile.roomFavourite.RoomFavouriteActivity;
import com.doancuoinam.hostelappdoancuoinam.view.profile.roomFavourite.RoomFavouriteAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRoom extends AppCompatActivity {
    RecyclerView recyclerMyRoom;
    ProgressBar progressBarList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_room);
        Toolbar toolbar = findViewById(R.id.toolbar);
        int nameTitle = R.string.MyRoom;
        setToolbar(toolbar,nameTitle);
        recyclerMyRoom = findViewById(R.id.recyclerMyRoom);
        progressBarList = findViewById(R.id.progressBarList);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        long userID = sharedPreferences.getLong("userId", 0);
        recyclerMyRoom.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        myRoomAdapter myRoomAdapter = new myRoomAdapter();
        recyclerMyRoom.setAdapter(myRoomAdapter);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        showProgressBar();
        Call<List<Rent>> call = apiService.getMyRoom(userID);
        call.enqueue(new Callback<List<Rent>>() {
            @Override
            public void onResponse(Call<List<Rent>> call, Response<List<Rent>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Rent> myRooms = response.body();
                    myRoomAdapter.setMyRooms(myRooms);
                    hideProgressBar();
                } else {
                    Toast.makeText(MyRoom.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                    hideProgressBar();
                }
            }

            @Override
            public void onFailure(Call<List<Rent>> call, Throwable t) {
                Toast.makeText(MyRoom.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailureRoomFavourite: " + t.getMessage());
                hideProgressBar();
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
    private void showProgressBar() {
        progressBarList.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar() {
        progressBarList.setVisibility(View.GONE);
    }
}