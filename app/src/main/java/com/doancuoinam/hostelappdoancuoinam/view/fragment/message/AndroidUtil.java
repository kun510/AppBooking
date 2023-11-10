package com.doancuoinam.hostelappdoancuoinam.view.fragment.message;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Users;

public class AndroidUtil {
    public static  void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static void passUserModelAsIntent(Intent intent, Users model){
        intent.putExtra("username",model.getName());
        intent.putExtra("phone",model.getPhone());
        intent.putExtra("userId",model.getId());
        intent.putExtra("fcmToken",model.getToken_device());

    }

    public static Users getUserModelFromIntent(Intent intent){
        Users userModel = new Users();
        userModel.setName(intent.getStringExtra("username"));
        userModel.setPhone(intent.getStringExtra("phone"));
        userModel.setId(Long.valueOf(intent.getStringExtra("userId")));
        userModel.setToken_device(intent.getStringExtra("fcmToken"));
        return userModel;
    }

    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }
}
