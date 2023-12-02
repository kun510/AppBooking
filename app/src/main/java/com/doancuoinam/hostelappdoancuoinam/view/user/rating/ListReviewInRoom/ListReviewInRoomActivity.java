package com.doancuoinam.hostelappdoancuoinam.view.user.rating.ListReviewInRoom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ImgRoom;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Review;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.empty.EmptyActivity;
import com.doancuoinam.hostelappdoancuoinam.view.user.room.listImgInRoom.AdapterOverview;
import com.doancuoinam.hostelappdoancuoinam.view.user.room.listImgInRoom.OverviewRoom;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListReviewInRoomActivity extends AppCompatActivity {
    ImageView imgRoom;
    TextView rating_number,total_rating;
    RatingBar ratingBar;
    SeekBar seekBar5,seekBar4,seekBar3,seekBar2,seekBar1;
    RecyclerView recyclerReview;
    listReviewAdapter listReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_review_in_room);
        Mapping();
        seekBar5.setProgress(15);
        seekBar4.setProgress(12);
        seekBar3.setProgress(9);
        seekBar2.setProgress(6);
        seekBar1.setProgress(3);
        recyclerReview.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));
        listReviewAdapter = new listReviewAdapter();
        recyclerReview.setAdapter(listReviewAdapter);
        Intent intent = getIntent();
        String  idRoom = intent.getStringExtra("idRoom");
        String  numberStar = intent.getStringExtra("numberStar");
        rating_number.setText(numberStar);
        String imageUrl = getIntent().getStringExtra("selected_image_url");
        Picasso.get().load(imageUrl).into(imgRoom);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        long idRoomLong = Long.parseLong(idRoom);
        ratingBar.setRating(Float.parseFloat(numberStar));
        Call<List<Review>> call = apiService.getReviewByRoom(idRoomLong);
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()){
                    List<Review> reviewListRooms = response.body();
                    if (reviewListRooms.isEmpty()){
                        Intent intent = new Intent(ListReviewInRoomActivity.this, EmptyActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        listReviewAdapter.setData(reviewListRooms);
                    }
                }
                else {
                    Toast.makeText(ListReviewInRoomActivity.this, "Dữ Liệu trống", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.e("TAG", "onFailureImgRoom: " + t.getMessage());
            }
        });
    }
    private void Mapping(){
        imgRoom = findViewById(R.id.imgRoom);
        rating_number = findViewById(R.id.rating_number);
        total_rating = findViewById(R.id.total_rating);
        recyclerReview = findViewById(R.id.recyclerReview);
        ratingBar = findViewById(R.id.ratingBar);
        seekBar5 = findViewById(R.id.seekBar5);
        seekBar4 = findViewById(R.id.seekBar4);
        seekBar3 = findViewById(R.id.seekBar3);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar1 = findViewById(R.id.seekBar1);
        seekBar5.setOnTouchListener((v, event) -> true);
        seekBar4.setOnTouchListener((v, event) -> true);
        seekBar3.setOnTouchListener((v, event) -> true);
        seekBar2.setOnTouchListener((v, event) -> true);
        seekBar1.setOnTouchListener((v, event) -> true);
    }
}