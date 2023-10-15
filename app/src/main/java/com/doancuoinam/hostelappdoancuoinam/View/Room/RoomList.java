package com.doancuoinam.hostelappdoancuoinam.View.Room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.doancuoinam.hostelappdoancuoinam.Model.Request.SearchRequest;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomList extends AppCompatActivity {
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
            SpannableString spannableString = new SpannableString(receivedText);
            spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            getSupportActionBar().setTitle(spannableString);
        }
        toolbar.setNavigationIcon(R.drawable.backdetail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = findViewById(R.id.recyclerRoomDetail);
        roomAdapter = new RoomAdapter();
        recyclerView.setAdapter(roomAdapter);
        showProgressBar();
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        SearchRequest searchRequest = new SearchRequest(receivedText, receivedTextPrice
                ,receivedTextGuests, receivedTextPrice,receivedTextType);
        Call<List<Room>> call = apiService.getAllRoomsSearch(receivedText, receivedTextPrice
                ,receivedTextGuests, receivedTextPrice,receivedTextType);
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    List<Room> rooms = response.body();
                    roomAdapter.setRoomsList(rooms);
                } else {
                    Toast.makeText(RoomList.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(RoomList.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
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