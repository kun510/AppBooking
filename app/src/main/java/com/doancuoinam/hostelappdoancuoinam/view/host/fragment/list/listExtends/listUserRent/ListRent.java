package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listUserRent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Rent;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.view.host.addBill.AddBillActivity;
import com.doancuoinam.hostelappdoancuoinam.view.host.confirmRent.ConfirmRentActivity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListRent extends Fragment {
     RecyclerView recyclerView;
     ProgressBar progressBar;
    AdapterUserRent listUserRentAdapter;
     ImageView addRent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_rent, container, false);
        recyclerView = view.findViewById(R.id.listRent);
        progressBar = view.findViewById(R.id.progressBar);
        addRent = view.findViewById(R.id.addRent);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));
        listUserRentAdapter = new AdapterUserRent();
        recyclerView.setAdapter(listUserRentAdapter);
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", 0);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Rent>> call = apiService.getUserInRent(userId);
        call.enqueue(new Callback<List<Rent>>() {
            @Override
            public void onResponse(Call<List<Rent>> call, Response<List<Rent>> response) {
                if (response.isSuccessful()){
                    List<Rent> rentList = response.body();
                    listUserRentAdapter.setDataRentList(getContext(),rentList);
                    progressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("API Call", errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Rent>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API Call", "Failed", t);
            }
        });
        event();
        return view;
    }
    private void event(){
        addRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ConfirmRentActivity.class);
                getContext().startActivity(intent);
            }
        });
    }
}