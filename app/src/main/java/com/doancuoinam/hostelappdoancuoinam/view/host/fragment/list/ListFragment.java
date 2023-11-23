package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listBoardingHostel.ListBoardingHostel;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listUserRent.ListRent;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listRoomEmpty.ListRoomEmpty;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listRoom.ListRoom;


public class ListFragment extends Fragment {

    LinearLayout MyBoarding,MyRoomLayout,RentLayout,RoomEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_list, container, false);
        MyBoarding = view.findViewById(R.id.MyBoarding);
        MyRoomLayout = view.findViewById(R.id.MyRoomLayout);
        RentLayout = view.findViewById(R.id.RentLayout);
        RoomEmpty = view.findViewById(R.id.RoomEmpty);
        eventMyBoarding(MyBoarding);
        eventRoom(MyRoomLayout);
        eventRentLayout(RentLayout);
        eventRoomEmpty(RoomEmpty);
        return view;
    }

    public void eventRoom(LinearLayout layout){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListRoom listRoom = new ListRoom();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, listRoom);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }
    public void eventMyBoarding(LinearLayout layout){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListBoardingHostel listRoom = new ListBoardingHostel();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, listRoom);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }
    public void eventRoomEmpty(LinearLayout layout){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListRoomEmpty listRoom = new ListRoomEmpty();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, listRoom);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }
    public void eventRentLayout(LinearLayout layout){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListRent listRoom = new ListRent();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, listRoom);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }
}