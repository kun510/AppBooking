package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.home.home_extends.ChartNumberRoomEmpty;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.home.home_extends.ChartNumberRoomInBoardingHostel;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.home.home_extends.ChartNumberUserinRent;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class HomeFragmentHost extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_host, container, false);
        ChartNumberRoomEmpty chartNumberRoomEmpty = new ChartNumberRoomEmpty();
        addFragment(chartNumberRoomEmpty,  R.id.container1);
        ChartNumberUserinRent chartNumberUserinRent = new ChartNumberUserinRent();
        addFragment(chartNumberUserinRent,  R.id.container2);
        ChartNumberRoomInBoardingHostel chartNumberRoomInBoardingHostel = new ChartNumberRoomInBoardingHostel();
        addFragment(chartNumberRoomInBoardingHostel,  R.id.container3);
        return view;
    }
    private void addFragment(Fragment fragment,  int containerId) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}