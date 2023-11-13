package com.doancuoinam.hostelappdoancuoinam.view.fragment.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.profile.Help;
import com.doancuoinam.hostelappdoancuoinam.view.profile.language.Language;
import com.doancuoinam.hostelappdoancuoinam.view.profile.myroom.MyRoom;
import com.doancuoinam.hostelappdoancuoinam.view.profile.roomFavourite.RoomFavouriteActivity;
import com.doancuoinam.hostelappdoancuoinam.view.profile.SettingProfile;

public class Profile extends Fragment {
    LinearLayout roomFavouriteLayout,MyRoomLayout,HelpLayout,LanguageLayout,SettingLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        AnhXa(view);
        setEvent();
        return view;
    }

    private void AnhXa(View view){
        roomFavouriteLayout = view.findViewById(R.id.roomFavouriteLayout);
        MyRoomLayout = view.findViewById(R.id.MyRoomLayout);
        HelpLayout = view.findViewById(R.id.HelpLayout);
        LanguageLayout = view.findViewById(R.id.LanguageLayout);
        SettingLayout = view.findViewById(R.id.SettingLayout);
    }
    private void setEvent(){
        roomFavouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity(), RoomFavouriteActivity.class);
                startActivity(intent);
            }
        });
        MyRoomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity(), MyRoom.class);
                startActivity(intent);
            }
        });
        HelpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity(), Help.class);
                startActivity(intent);
            }
        });
        LanguageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity(), Language.class);
                startActivity(intent);
            }
        });
        SettingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity(), SettingProfile.class);
                startActivity(intent);
            }
        });
    }
}