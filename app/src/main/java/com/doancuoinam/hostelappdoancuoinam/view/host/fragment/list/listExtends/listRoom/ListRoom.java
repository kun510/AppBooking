package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listRoom;

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

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.empty.EmptyActivity;
import com.doancuoinam.hostelappdoancuoinam.view.host.addRoom.AddRoomByHostActivity;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listRoomEmpty.ListRoomAdapter;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;


public class ListRoom extends Fragment {
    RecyclerView listRoom;
    ListRoomAdapter listRoomAdapter;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_room, container, false);
        listRoom = view.findViewById(R.id.listRoom);
        listRoom.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));
        listRoomAdapter = new ListRoomAdapter();
        listRoom.setAdapter(listRoomAdapter);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", 0);
        long boardingId = 1;
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Room>> call = apiService.AllRoomByHost(userId,boardingId);
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    List<Room> rooms = response.body();
                    Log.d("TAG", "API Call: " + rooms.get(0).getId());
                    progressBar.setVisibility(View.GONE);
                    Log.e("API Call", "ok");
                    if (rooms.isEmpty()){
                        Intent intent = new Intent(getContext(), EmptyActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }else {
                        listRoomAdapter.setRooms(rooms,getContext());
                    }
                } else {
                    Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<List<Room>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API Call", "Failed", t);
            }
        });
        return view;
    }

}