package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.profile;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Users;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseOtp;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.changePassword.OtpConfirmChangePasswordActivity;
import com.doancuoinam.hostelappdoancuoinam.chatSupport.ChatSupportActivity;
import com.doancuoinam.hostelappdoancuoinam.updateUser.UpdateUser;
import com.doancuoinam.hostelappdoancuoinam.view.user.profile.Help;
import com.doancuoinam.hostelappdoancuoinam.view.user.profile.SettingProfile;
import com.doancuoinam.hostelappdoancuoinam.view.user.profile.language.Language;
import com.doancuoinam.hostelappdoancuoinam.view.user.profile.myroom.MyRoom;
import com.doancuoinam.hostelappdoancuoinam.view.user.profile.roomFavourite.RoomFavouriteActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileHost extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout ;
    ShapeableImageView avt;
    TextView nameUser;
    LinearLayout editProfile,changePass,TurnNoti,about,help;
    ProgressBar progress_otp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_host, container, false);
        Mapping(view);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        long userID = sharedPreferences.getLong("userId", 0);
        Call<List<Users>> call = apiService.getUserCurrent(userID);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.isSuccessful()) {
                    List<Users> userList = response.body();
                    for (Users users : userList) {
                        String imageUrl = users.getImg();
                        String nameUserPut = users.getName();
                        Picasso.get().load(imageUrl).into(avt);
                        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("avt", imageUrl);
                        editor.putString("name", nameUserPut);
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
        eventChangePass();
        eventClick();
        return view;
    }
    private void Mapping(View view){
        editProfile = view.findViewById(R.id.editProfile);
        changePass = view.findViewById(R.id.changePass);
        avt = view.findViewById(R.id.avtProfile);
        nameUser = view.findViewById(R.id.nameUser);
        progress_otp = view.findViewById(R.id.progress_otp);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        help = view.findViewById(R.id.help);
    }
    private void eventChangePass(){
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress_otp.setVisibility(View.VISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận thay đổi mật khẩu");
                builder.setMessage("Bạn có chắc chắn muốn thay đổi mật khẩu không?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        String email = sharedPreferences.getString("email","");
                        ApiService apiService = ApiClient.getClient().create(ApiService.class);
                        Call<ResponseOtp> call = apiService.sendMailChangePassword(email);
                        call.enqueue(new Callback<ResponseOtp>() {
                            @Override
                            public void onResponse(Call<ResponseOtp> call, Response<ResponseOtp> response) {
                                if (response.isSuccessful()) {
                                    ResponseOtp responseOtp = response.body();
                                    if (responseOtp.isSuccess()) {
                                        String Otp =  responseOtp.getOtp();
                                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("otp", Otp);
                                        editor.apply();
                                        Intent intent = new Intent(getContext(), OtpConfirmChangePasswordActivity.class);
                                        getContext().startActivity(intent);
                                        progress_otp.setVisibility(View.GONE);
                                    } else {
                                        progress_otp.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "sendMail Fail", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progress_otp.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "Lỗi Mail", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseOtp> call, Throwable t) {
                                Log.d("TAG", "senMail: " + t);
                                progress_otp.setVisibility(View.GONE);

                            }
                        });

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    private void eventClick(){
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpdateUser.class);
                getContext().startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatSupportActivity.class);
                getContext().startActivity(intent);
            }
        });
    }
}