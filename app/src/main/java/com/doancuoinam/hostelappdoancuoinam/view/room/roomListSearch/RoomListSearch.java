package com.doancuoinam.hostelappdoancuoinam.view.room.roomListSearch;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomListSearch extends AppCompatActivity {
    RecyclerView recyclerView;
    RoomAdapter roomAdapter;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBarList);
        Intent intent = getIntent();
        String receivedText = intent.getStringExtra("area");
        String receivedTextType = intent.getStringExtra("type");
        String receivedTextGuests = intent.getStringExtra("guests");
        String receivedTextPrice = intent.getStringExtra("price");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            if(receivedText.isEmpty()&&receivedTextType.isEmpty()&&receivedTextGuests.isEmpty()){
                SpannableString spannableString = new SpannableString(receivedTextPrice);
                spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                getSupportActionBar().setTitle(spannableString);

            }
            else if(receivedText.isEmpty()&&receivedTextType.isEmpty()&&receivedTextPrice.isEmpty()){
                SpannableString spannableString = new SpannableString(receivedTextGuests);
                spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                getSupportActionBar().setTitle(spannableString);
            }
            else if(receivedText.isEmpty()&&receivedTextPrice.isEmpty()&&receivedTextGuests.isEmpty()){
                SpannableString spannableString = new SpannableString(receivedTextType);
                spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                getSupportActionBar().setTitle(spannableString);
            }
           else if(receivedTextType.isEmpty()&&receivedTextType.isEmpty()&&receivedTextGuests.isEmpty()){
                SpannableString spannableString = new SpannableString(receivedText);
                spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                getSupportActionBar().setTitle(spannableString);
            }
           else {
                SpannableString spannableString = new SpannableString(receivedText);
                spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                getSupportActionBar().setTitle(spannableString);
            }

        }
        toolbar.setNavigationIcon(R.drawable.backdetail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = findViewById(R.id.recyclerRoomDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(RoomListSearch.this,RecyclerView.VERTICAL, false));
        roomAdapter = new RoomAdapter();
        recyclerView.setAdapter(roomAdapter);
        showProgressBar();
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Room>> call = apiService.getAllRoomsSearch(receivedText, receivedTextPrice
                ,receivedTextGuests, receivedTextPrice,receivedTextType);
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    List<Room> rooms = response.body();
                    roomAdapter.setRoomsList(rooms);

                } else {
                    Toast.makeText(RoomListSearch.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(RoomListSearch.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailureRoomaaaaa: " + t.getMessage());
                hideProgressBar();
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