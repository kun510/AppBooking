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
import com.doancuoinam.hostelappdoancuoinam.view.host.addRoom.AddRoomByHostActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListRoom extends Fragment {
    RecyclerView listRoom;
    ListRoomAdapter listRoomAdapter;
    ProgressBar progressBar;
    ImageView addRoomByHost;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_room, container, false);
        listRoom = view.findViewById(R.id.listRoom);
        listRoom.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));
        listRoomAdapter = new ListRoomAdapter();
        listRoom.setAdapter(listRoomAdapter);
        progressBar = view.findViewById(R.id.progressBar);
        addRoomByHost = view.findViewById(R.id.addRoomByHost);
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", 0);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Room>> call = apiService.getRoomByHost(userId);
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    List<Room> rooms = response.body();
                    Log.d("TAG", "API Call: " + rooms.get(0).getId());
                    listRoomAdapter.setRooms(rooms);
                    progressBar.setVisibility(View.GONE);
                    Log.e("API Call", "ok");
                } else {
                    Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    Log.e("API Call", "Faileddd");
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API Call", "Failed", t);
            }
        });
        eventClickAddRoom();
        return view;
    }

    public void eventClickAddRoom(){
        addRoomByHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddRoomByHostActivity.class);
                getContext().startActivity(intent);
            }
        });
    }
}