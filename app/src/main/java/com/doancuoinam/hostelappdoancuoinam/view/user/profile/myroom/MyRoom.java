package com.doancuoinam.hostelappdoancuoinam.view.user.profile.myroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.empty.EmptyActivity;
import com.doancuoinam.hostelappdoancuoinam.toast.ToastInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class MyRoom extends AppCompatActivity implements ToastInterface {
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
                        hideProgressBar();
                        if (myRooms.isEmpty()){
                            Intent intent = new Intent(MyRoom.this, EmptyActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            myRoomAdapter.setMyRooms(myRooms,MyRoom.this);
                        }
                } else {
                    Intent intent = new Intent(MyRoom.this, EmptyActivity.class);
                    startActivity(intent);
                    createCustomToast("Bạn Chưa Thuê Phòng!", "", MotionToastStyle.INFO);
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

    @Override
    public void createCustomToast(String message, String description, MotionToastStyle style) {
        MotionToast.Companion.createToast(this, message, description, style, MotionToast.GRAVITY_BOTTOM, MotionToast.LONG_DURATION, ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular));
    }
}