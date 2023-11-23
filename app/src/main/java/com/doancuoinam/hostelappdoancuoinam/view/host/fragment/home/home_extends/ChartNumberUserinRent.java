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

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ListandCoutRoom;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Rent;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChartNumberUserinRent extends Fragment {
    BarChart barChartUserRent;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_number_userin_rent, container, false);
        barChartUserRent = view.findViewById(R.id.chartListUserRent);
        progressBar = view.findViewById(R.id.progressBarL);

        showProgressBar();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", 0);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Rent>> call = apiService.getUserInRent(userId);
        call.enqueue(new Callback<List<Rent>>() {
            @Override
            public void onResponse(Call<List<Rent>> call, Response<List<Rent>> response) {
                if (response.isSuccessful()){
                    List<Rent> rentList = response.body();
                    ArrayList<BarEntry> countUserInRent = new ArrayList<>();
                    for (Rent rent : rentList) {
                        countUserInRent.add(new BarEntry(Float.valueOf(rent.getRoom().getId()),Float.valueOf(rent.getPeopleInRoom())));
                    }
                    BarDataSet barDataSet = new BarDataSet(countUserInRent,"số người trong phòng");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(16f);

                    BarData barData = new BarData(barDataSet);
                    barData.setValueFormatter(new DefaultValueFormatter(0));
                    YAxis yAxis = barChartUserRent.getAxisLeft();
                    yAxis.setAxisMinimum(1f);
                    yAxis.setAxisMaximum(10f);
                    barChartUserRent.setFitBars(true);
                    barChartUserRent.setData(barData);
                    barChartUserRent.getDescription().setText("Số người");
                    barChartUserRent.animateY(2000);
                    barChartUserRent.invalidate();
                    hideProgressBar();
                }else {
                    Toast.makeText(getContext(), "Error fetching data from API", Toast.LENGTH_SHORT).show();
                    hideProgressBar();
                }
            }

            @Override
            public void onFailure(Call<List<Rent>> call, Throwable t) {
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