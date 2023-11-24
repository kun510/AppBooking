package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listBoardingHostel;

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

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Boarding_host;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ListandCoutRoom;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.view.host.addBoarding.AddBoardingActivity;
import com.doancuoinam.hostelappdoancuoinam.view.host.addRoom.AddRoomByHostActivity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListBoardingHostel extends Fragment {
    RecyclerView recyclerView;
    ListBoardingAdapter boardingHostelAdapter;
    ProgressBar progressBar;
    ImageView addBoardingByHost;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_boarding_hostel, container, false);
        recyclerView = view.findViewById(R.id.listBoarding);
        progressBar = view.findViewById(R.id.progressBar);
        addBoardingByHost = view.findViewById(R.id.addBoardingByHost);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));
        boardingHostelAdapter = new ListBoardingAdapter();
        recyclerView.setAdapter(boardingHostelAdapter);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", 0);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Boarding_host>> call = apiService.getRoomInBoarding(userId);
        call.enqueue(new Callback<List<Boarding_host>>() {
            @Override
            public void onResponse(Call<List<Boarding_host>> call, Response<List<Boarding_host>> response) {
                if (response.isSuccessful()){
                    List<Boarding_host> roomList = response.body();
                    boardingHostelAdapter.setDataBoardingHostel(getContext(),roomList);
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
            public void onFailure(Call<List<Boarding_host>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API Call", "Failed", t);
            }
        });
        event();
        return view;
    }
    public void event(){
        addBoardingByHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddBoardingActivity.class);
                getContext().startActivity(intent);
            }
        });
    }
}