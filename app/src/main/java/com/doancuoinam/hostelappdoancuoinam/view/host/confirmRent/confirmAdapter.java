package com.doancuoinam.hostelappdoancuoinam.view.host.confirmRent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.BaseActivity;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Boarding_host;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.NotificationMessaging;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Rent;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseAll;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseToken;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.view.user.room.listImgInRoom.OverviewRoom;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class confirmAdapter extends RecyclerView.Adapter<confirmAdapter.ViewHolder> {
    List<Rent> rentList;
    Context context;
    public void setDataRentListConfirm(Context context, List<Rent> rentList){
        this.context = context;
        this.rentList = rentList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public confirmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_rent, parent, false);
        return new confirmAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull confirmAdapter.ViewHolder holder, int position) {
        Rent rent = rentList.get(position);
        holder.onBind(rent);
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evenAccept(rent,rent.getRoom().getId(),rent.getId());
            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evenCancel(rent,rent.getRoom().getId(),rent.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return rentList != null ? rentList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idRoom,name_rent_confirm,phone_rent;
        ImageView accept,cancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idRoom = itemView.findViewById(R.id.idRoom);
            name_rent_confirm = itemView.findViewById(R.id.name_rent_confirm);
            phone_rent = itemView.findViewById(R.id.phone_rent);
            accept = itemView.findViewById(R.id.accept);
            cancel = itemView.findViewById(R.id.cancel);
        }
        void onBind(Rent rent){
            idRoom.setText(String.valueOf(rent.getRoom().getId()));
            name_rent_confirm.setText(rent.getUser().getName());
            phone_rent.setText(rent.getUser().getPhone());
        }
    }
    public void evenAccept(Rent rent, long idRoom,long IdRent){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseAll> call = apiService.addUserInRoomMobile(idRoom,IdRent);
        call.enqueue(new Callback<ResponseAll>() {
            @Override
            public void onResponse(Call<ResponseAll> call, Response<ResponseAll> response) {
                if (response.isSuccessful()) {
                    ResponseAll responseData = response.body();
                    Toast.makeText(context, "Chấp Thuận Thành Công", Toast.LENGTH_SHORT).show();
                    NotificationRentLogDeviceOk(rent);
                    Intent intent = ((Activity) context).getIntent();
                    ((Activity) context).finish();
                    ((Activity) context).startActivity(intent);
                } else {
                    Toast.makeText(context, "Status update failed", Toast.LENGTH_SHORT).show();
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.getString("message");

                        Toast.makeText(context, "Status update failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "api logg: " + errorMessage);
                        Log.d("TAG", "api logg: " + errorBody);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        Log.d("TAG", "api loggdđ: " + e);
                        Toast.makeText(context, "Failed to process error response", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAll> call, Throwable t) {
                Toast.makeText(context, "lỗi mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void evenCancel(Rent rent,long idRoom,long IdRent){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseAll> call = apiService.cancelUserInRoomMobile(idRoom,IdRent);
        call.enqueue(new Callback<ResponseAll>() {
            @Override
            public void onResponse(Call<ResponseAll> call, Response<ResponseAll> response) {
                if (response.isSuccessful()){
                    ResponseAll responseData = response.body();
                    Toast.makeText(context, "Huỷ Thành Công", Toast.LENGTH_SHORT).show();
                    NotificationRentLogDeviceCancel(rent);
                    Intent intent = ((Activity) context).getIntent();
                    ((Activity) context).finish();
                    ((Activity) context).startActivity(intent);

                }else {
                    Toast.makeText(context, "Status update failed", Toast.LENGTH_SHORT).show();
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String errorMessage = jsonObject.getString("message");
                        //Toast.makeText(context, "Status update failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "api logg: " + errorMessage);
                        Log.d("TAG", "api logg: " + errorBody);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        Log.d("TAG", "api loggdđ: " + e);
                        Toast.makeText(context, "Failed to process error response", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAll> call, Throwable t) {
                Toast.makeText(context, "lỗi mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void NotificationRentLogDeviceOk(Rent rent){
        String TokenUser =  rent.getUser().getToken_device();
        String title = "Confirm Rent";
        String body = "User Accept Rent";
        String img = "image_url";
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");
        NotificationRent(TokenUser, title, body, img, data);
        Log.d("TAG", "Token: " + TokenUser);
    }
    private void NotificationRentLogDeviceCancel(Rent rent){
        String TokenUser =  rent.getUser().getToken_device();
        String title = "Confirm Rent";
        String body = "User Cancel Rent";
        String img = "image_url";
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");
        NotificationRent(TokenUser, title, body, img, data);
        Log.d("TAG", "Token: " + TokenUser);
    }

    private void NotificationRent(String token, String title, String body, String img, Map<String,String> data){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        NotificationMessaging notificationMessaging = new NotificationMessaging(token,title,body,img,data);
        Call<ResponseAll> call = apiService.sendNotification(notificationMessaging);
        call.enqueue(new Callback<ResponseAll>() {
            @Override
            public void onResponse(Call<ResponseAll> call, Response<ResponseAll> response) {
//                ResponseAll responseAll = response.body();
//                Toast.makeText(OverviewRoom.this,"" + responseAll, Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    ResponseAll responseAll = response.body();
                    Toast.makeText(context, "" + responseAll, Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "onResponse Noti: " + responseAll.getMessage());
                } else {
                    Log.e("TAG", "onResponse Notielse: " + response.code());
                    Log.e("TAG", "onResponse Notifition: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseAll> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối mạng hoặc máy chủ không phản hồi", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
