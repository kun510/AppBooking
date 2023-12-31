package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.profile;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Users;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.chatSupport.ChatSupportActivity;
import com.doancuoinam.hostelappdoancuoinam.view.user.profile.Help;
import com.doancuoinam.hostelappdoancuoinam.view.user.profile.language.Language;
import com.doancuoinam.hostelappdoancuoinam.view.user.profile.myroom.MyRoom;
import com.doancuoinam.hostelappdoancuoinam.view.user.profile.roomFavourite.RoomFavouriteActivity;
import com.doancuoinam.hostelappdoancuoinam.view.user.profile.SettingProfile;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Fragment {
    LinearLayout roomFavouriteLayout,MyRoomLayout,HelpLayout,LanguageLayout,SettingLayout;
    SwipeRefreshLayout swipeRefreshLayout ;
    ShapeableImageView avt;
    TextView nameUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Mapping(view);
        setEvent();
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        long userID = sharedPreferences.getLong("userId", 0);
        Call<List<Users>> call = apiService.getUserCurrent(userID);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.isSuccessful()) {
                    List<Users> userList = response.body();
                   for (Users users : userList){
                       String imageUrl = users.getImg();
                       String nameUserPut = users.getName();
                       Picasso.get().load(imageUrl).into(avt);
                       SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       editor.putString("avt", imageUrl);
                       editor.putString("name",nameUserPut);
                       editor.apply();
                       String name = users.getName();
                       nameUser.setText(name);
                   }
                } else {
                    Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailureListRoom: " + t.getMessage());
            }
        });
        avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  openGallery();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;

    }

    private void Mapping(View view){
        roomFavouriteLayout = view.findViewById(R.id.roomFavouriteLayout);
        MyRoomLayout = view.findViewById(R.id.MyRoomLayout);
        HelpLayout = view.findViewById(R.id.HelpLayout);
        LanguageLayout = view.findViewById(R.id.LanguageLayout);
        SettingLayout = view.findViewById(R.id.SettingLayout);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        avt = view.findViewById(R.id.avtProfile);
        nameUser = view.findViewById(R.id.nameUser);
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
                Intent intent = new Intent (getActivity(), ChatSupportActivity.class);
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