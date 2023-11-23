package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listBoardingHostel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ListandCoutRoom;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListBoardingHostel extends Fragment {
    RecyclerView recyclerView;
    ListBoardingHostelAdapter boardingHostelAdapter;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_boarding_hostel, container, false);
        recyclerView = view.findViewById(R.id.listBoarding);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));
        boardingHostelAdapter = new ListBoardingHostelAdapter();
        recyclerView.setAdapter(boardingHostelAdapter);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", 0);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<ListandCoutRoom>> call = apiService.getRoomEmptyByBoarding(userId);
        call.enqueue(new Callback<List<ListandCoutRoom>>() {
            @Override
            public void onResponse(Call<List<ListandCoutRoom>> call, Response<List<ListandCoutRoom>> response) {
                if (response.isSuccessful()){
                    List<ListandCoutRoom> roomList = response.body();
                    boardingHostelAdapter.setDataBoardingHostel(roomList);
                    progressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    Log.e("API Call", "Faileddd");
                }
            }

            @Override
            public void onFailure(Call<List<ListandCoutRoom>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API Call", "Failed", t);
            }
        });
        return view;
    }
}