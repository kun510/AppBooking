package com.doancuoinam.hostelappdoancuoinam.view.user.room.roomListSearch;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBarList);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String receivedText = intent.getStringExtra("area");
        String receivedTextType = intent.getStringExtra("type");
        String receivedTextGuests = intent.getStringExtra("guests");
        String receivedTextPrice = intent.getStringExtra("price");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            String titleText = determineTitle(receivedText, receivedTextType, receivedTextGuests, receivedTextPrice);
            SpannableString spannableString = new SpannableString(titleText);
            spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            getSupportActionBar().setTitle(spannableString);
        }

        setupToolbar(toolbar);
        setupRecyclerView();
        showProgressBar();
        fetchRoomData(receivedText, receivedTextType, receivedTextGuests, receivedTextPrice);
    }

    private String determineTitle(String area, String type, String guests, String price) {
        if ("Type".equals(type) && "Area".equals(area)) {
            if (price.isEmpty() && guests.isEmpty()) {
                return type.isEmpty() ? area : type;
            } else {
                return price.isEmpty() ? guests : price;
            }
        }
        return getResources().getString(R.string.btnselect);
    }

    private void setupToolbar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.backdetail);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerRoomDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(RoomListSearch.this, RecyclerView.VERTICAL, false));
        roomAdapter = new RoomAdapter();
        recyclerView.setAdapter(roomAdapter);
    }

    private void fetchRoomData(String area, String type, String guests, String price) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Integer numericPrice = null;

        if ("Type".equals(type) && "Area".equals(area)) {
            area = "";
            type = "";
        }

        try {
            if (price != null && !price.isEmpty()) {
                numericPrice = Integer.parseInt(price);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Call<List<Room>> call = apiService.getAllRoomsSearch(numericPrice, area, guests, type);
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