package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.home.home_extends;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Boarding_host;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ListandCoutRoom;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartNumberRoomEmpty extends Fragment {
    PieChart chartListRoomEmpty;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_number_room_empty, container, false);
        chartListRoomEmpty = view.findViewById(R.id.chartListUser);
        progressBar = view.findViewById(R.id.progressBarL);

        showProgressBar();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", 0);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<ListandCoutRoom>> call = apiService.getRoomEmptyByBoarding(userId);
        call.enqueue(new Callback<List<ListandCoutRoom>>() {
            @Override
            public void onResponse(Call<List<ListandCoutRoom>> call, Response<List<ListandCoutRoom>> response) {
                if (response.isSuccessful()){
                    List<ListandCoutRoom> roomList = response.body();
                    ArrayList<PieEntry> countInRoom = new ArrayList<>();
                    for (ListandCoutRoom room : roomList) {
                        long roomId = room.getBoardingHost().getId();
                        if (roomId > 0){
                            countInRoom.add(new PieEntry(room.getCount(),"Dãy trọ: "+String.valueOf(roomId)));
                        }else {
                            Toast.makeText(getContext(), "null id", Toast.LENGTH_SHORT).show();
                        }
                    }
                    PieDataSet pieDataSet = new PieDataSet(countInRoom,"Dãy trọ");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);

                    PieData pieData = new PieData(pieDataSet);
                    pieData.setValueFormatter(new DefaultValueFormatter(0));
                    chartListRoomEmpty.setData(pieData);
                    chartListRoomEmpty.getDescription().setEnabled(false);
                    chartListRoomEmpty.setCenterText("Số phòng trống");
                    chartListRoomEmpty.animate();
                    chartListRoomEmpty.invalidate();
                    hideProgressBar();
                }else {
                    Toast.makeText(getContext(), "Error fetching data from API", Toast.LENGTH_SHORT).show();
                    hideProgressBar();
                }

            }

            @Override
            public void onFailure(Call<List<ListandCoutRoom>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgressBar();
            }
        });
        return view;
    }
    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}