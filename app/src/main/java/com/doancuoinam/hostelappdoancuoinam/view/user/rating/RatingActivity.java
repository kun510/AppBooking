package com.doancuoinam.hostelappdoancuoinam.view.user.rating;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ReviewRq;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity {
    TextView titleRate, resultRate;
    ImageView emoji;
    EditText cmt;
    Button button;
    RatingBar rateStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        titleRate = findViewById(R.id.rating_title);
        resultRate = findViewById(R.id.rating_result);
        emoji = findViewById(R.id.emoji);
        button = findViewById(R.id.btn_Submit);
        cmt = findViewById(R.id.cmt);
        Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        long userID = sharedPreferences.getLong("userId", 0);
        String idRoom = intent.getStringExtra("idMyroom");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RatingActivity.this, "Thank You! for the Feedback ", Toast.LENGTH_LONG).show();
                ReviewRq reviewRq = new ReviewRq();
                String feedback = cmt.getText().toString();
                float sao = rateStars.getRating();
                reviewRq.setEvaluate(feedback);
                reviewRq.setNumberOfStars(sao);
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<String> call = apiService.review(reviewRq,Long.parseLong(idRoom),userID);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RatingActivity.this, "Thank You! for the Feedback", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Log.e("TAG", "onResponse: Error - " + response.code() + ": " + response.message());
                            Toast.makeText(RatingActivity.this, "Bạn đã đánh giá rồi", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("TAG", "onFailure: "+ t.getMessage());
                        onBackPressed();
                    }
                });
            }
        });
        rateStars = findViewById(R.id.rateStars);
        rateStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating >= 0.5 && rating < 1.5) {
                    emoji.setImageResource(R.drawable.one);
                    resultRate.setText(R.string.Bad);
                    cmt.setVisibility(View.VISIBLE);
                    cmt.getText().toString();
                } else if (rating >= 1.5 && rating < 2.5) {
                    emoji.setImageResource(R.drawable.two);
                    resultRate.setText(R.string.Not_Bad);
                    cmt.setVisibility(View.VISIBLE);
                    cmt.getText().toString();
                } else if (rating >= 2.5 && rating < 3.5) {
                    emoji.setImageResource(R.drawable.three);
                    resultRate.setText(R.string.Nice);
                    cmt.setVisibility(View.VISIBLE);
                    cmt.getText().toString();
                } else if (rating >= 3.5 && rating < 4.5) {
                    emoji.setImageResource(R.drawable.four);
                    resultRate.setText(R.string.Good);
                    cmt.setVisibility(View.GONE);
                    cmt.setText(R.string.Good);
                } else if (rating >= 4.5 && rating <= 5.0) {
                    emoji.setImageResource(R.drawable.five);
                    resultRate.setText(R.string.Awesome);
                    cmt.setVisibility(View.GONE);
                    cmt.setText(R.string.Awesome);
                } else {
                    Toast.makeText(getApplicationContext(), "No Point", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}