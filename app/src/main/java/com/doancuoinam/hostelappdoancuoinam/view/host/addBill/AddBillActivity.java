package com.doancuoinam.hostelappdoancuoinam.view.host.addBill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseAll;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddBillActivity extends AppCompatActivity {
    LinearLayout btnInsert;
    TextView idRoomRent,name_rent;
    EditText electric_hotel,costsIncurred;
    String idRentGet,nameRentGet,idRoomRentGet;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        Mapping();
        progressBar = findViewById(R.id.progressBar);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long hostId = sharedPreferences.getLong("userId", 0);
        Intent intent = getIntent();
        idRentGet = intent.getStringExtra("idRent");
        nameRentGet = intent.getStringExtra("nameRent");
        idRoomRentGet = intent.getStringExtra("idRoomRent");
        long RentId = Long.parseLong(idRentGet);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int electric = Integer.parseInt(electric_hotel.getText().toString());
                int costsIn = Integer.parseInt(costsIncurred.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<ResponseAll> call = apiService.addBill(electric,costsIn,RentId,hostId);
                call.enqueue(new Callback<ResponseAll>() {
                    @Override
                    public void onResponse(Call<ResponseAll> call, Response<ResponseAll> response) {
                        if (response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            onBackPressed();
                            Toast.makeText(AddBillActivity.this, "Thêm Hoá đơn Thành Công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AddBillActivity.this, "Thêm Hoá đơn Bị lỗi", Toast.LENGTH_SHORT).show();
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
                    public void onFailure(Call<ResponseAll> call, Throwable t) {
                        Log.e("API Call", t.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }

    private void Mapping(){
        btnInsert = findViewById(R.id.insert);
        idRoomRent = findViewById(R.id.idRoomRent);
        name_rent = findViewById(R.id.name_rent);
        electric_hotel = findViewById(R.id.electric_hotel);
        costsIncurred = findViewById(R.id.costsIncurred);
    }
}