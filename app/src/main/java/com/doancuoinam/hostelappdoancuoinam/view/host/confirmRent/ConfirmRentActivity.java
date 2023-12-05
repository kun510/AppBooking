package com.doancuoinam.hostelappdoancuoinam.view.host.confirmRent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Rent;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmRentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    confirmAdapter confirmAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_rent);
        recyclerView = findViewById(R.id.listRent);
        progressBar = findViewById(R.id.progressBar);
        confirmAdapter = new confirmAdapter();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(confirmAdapter);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long hostId = sharedPreferences.getLong("userId", 0);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Rent>> call = apiService.getRentByHostIdConfirm(hostId);
        call.enqueue(new Callback<List<Rent>>() {
            @Override
            public void onResponse(Call<List<Rent>> call, Response<List<Rent>> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    List<Rent> rentList = response.body();
                    confirmAdapter.setDataRentListConfirm(ConfirmRentActivity.this,ConfirmRentActivity.this,rentList);
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ConfirmRentActivity.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Rent>> call, Throwable t) {
                Log.e("TAG", "onFailureListRoom: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}