package com.doancuoinam.hostelappdoancuoinam.view.fragment.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Boarding_host;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.NotificationApp;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notifications extends Fragment {
 RecyclerView recyclerNotification;
 NotificationsAdapter notificationsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", 0);
        recyclerNotification = view.findViewById(R.id.recyclerNotification);
        recyclerNotification.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        notificationsAdapter = new NotificationsAdapter();
        recyclerNotification.setAdapter(notificationsAdapter);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<NotificationApp>> call = apiService.allNotificationReceiver(userId);
        call.enqueue(new Callback<List<NotificationApp>>() {
            @Override
            public void onResponse(Call<List<NotificationApp>> call, Response<List<NotificationApp>> response) {
                if (response.isSuccessful()) {
                    List<NotificationApp> notificationApps = response.body();
                    notificationsAdapter.setNotificationAppList(notificationApps);
                } else {
                    Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NotificationApp>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailureListRoom: " + t.getMessage());
            }
        });
        return view;
    }
}