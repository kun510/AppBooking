package com.doancuoinam.hostelappdoancuoinam.view.fragment.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;


import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Boarding_host;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.view.map.Map;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView,recyclerHot;
    HomeAdapterNew roomAdapter;
    HomeAdapterHot homeAdapterHot;
    ProgressBar progressBar,progressBarHot;
    TextView location,mapHome;
    Location lastLocation;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //lay id
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        long userId = sharedPreferences.getLong("userId", 0);
//        Toast.makeText(getActivity(), "User ID: " + userId, Toast.LENGTH_SHORT).show();
        recyclerView = view.findViewById(R.id.recyclerPlaces);
        mapHome = view.findViewById(R.id.mapHome);
        progressBar = view.findViewById(R.id.progressBar);
        progressBarHot = view.findViewById(R.id.progressBarHot);
        location = view.findViewById(R.id.location);
        mapHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map mapFragment = new Map();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, mapFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLocationAndDisplay();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false));
        roomAdapter = new HomeAdapterNew();
        recyclerView.setAdapter(roomAdapter);
        recyclerHot = view.findViewById(R.id.recyclerHot);
        recyclerHot.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        homeAdapterHot = new HomeAdapterHot();
        recyclerHot.setAdapter(homeAdapterHot);
        showProgressBar();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String Area = sharedPreferences.getString("selectedItem", "");
        location.setText(Area);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Boarding_host>> call = apiService.getAllBoarding(Area);

        call.enqueue(new Callback<List<Boarding_host>>() {
            @Override
            public void onResponse(Call<List<Boarding_host>> call, Response<List<Boarding_host>> response) {
                if (response.isSuccessful()) {
                    List<Boarding_host> rooms = response.body();
                    roomAdapter.setRooms(rooms);
                } else {
                    Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Boarding_host>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailureListRoom: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
        Call<List<Room>> callHot = apiService.getAllRoomsHot();
        callHot.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    List<Room> rooms = response.body();
                    homeAdapterHot.setRoomsHot(rooms);
                } else {
                    Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
                progressBarHot.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailureListRoomHot: " + t.getMessage());
                progressBarHot.setVisibility(View.GONE);
            }
        });
        return view;
    }
    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        progressBarHot.setVisibility(View.VISIBLE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getLocationAndDisplay();
            }
        }
    }
    private void getLocationAndDisplay() {
//        Location mlocation = new Location("dummy");
//        mlocation.setLatitude(37.7749);
//        mlocation.setLongitude(-122.4194);
//
//        location.setText("Latitude: " + mlocation.getLatitude() + "\nLongitude: " + mlocation.getLongitude());

        if (lastLocation != null) {
            location.setText("Latitude: " + lastLocation.getLatitude() + "\nLongitude: " + lastLocation.getLongitude());
        }
        else {
            Toast.makeText(getContext(), "dien thoai gà ", Toast.LENGTH_SHORT).show();
        }
    }
}