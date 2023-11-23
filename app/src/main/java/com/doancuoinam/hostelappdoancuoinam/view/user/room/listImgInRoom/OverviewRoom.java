package com.doancuoinam.hostelappdoancuoinam.view.user.room.listImgInRoom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.BaseActivity;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ImgRoom;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.NotificationMessaging;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseAll;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseToken;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.view.user.map.GeocodingTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OverviewRoom extends AppCompatActivity implements OnMapReadyCallback {
    RecyclerView recyclerView;
    AdapterOverview adapterOverview;
    ProgressBar progressBar;
    ImageView imgRoomMain;
    String addressRoom,idRoom,numberStar,area,idHost;
    TextView numberStarRoom;
    Toolbar toolbar;
    MapView mMapView;
    Button btn_rent;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_room);
        mapping();
        getExtra();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));
        adapterOverview = new AdapterOverview();
        recyclerView.setAdapter(adapterOverview);
        NotificationRentLogDevice();
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        showProgressBar();
        Call<List<ImgRoom>> call = apiService.getImgInRoom(Long.parseLong(idRoom));
        call.enqueue(new Callback<List<ImgRoom>>() {
            @Override
            public void onResponse(Call<List<ImgRoom>> call, Response<List<ImgRoom>> response) {
                if (response.isSuccessful()) {
                    List<ImgRoom> imgRooms = response.body();
                    adapterOverview.AdapterOverviewList(imgRooms);
                    hideProgressBar();
                } else {
                    Toast.makeText(OverviewRoom.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                    hideProgressBar();
                }
            }

            @Override
            public void onFailure(Call<List<ImgRoom>> call, Throwable t) {
                Toast.makeText(OverviewRoom.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailureImgRoom: " + t.getMessage());
                hideProgressBar();
            }
        });
        initGoogleMap(savedInstanceState);
    }
    private void initGoogleMap(Bundle savedInstanceState){

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync((OnMapReadyCallback) this);
    }
    private void NotificationRentLogDevice(){
        btn_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Intent intent = getIntent();
                idHost = intent.getStringExtra("idHost");
                Call<ResponseToken> call = apiService.getToken(Long.parseLong(idHost));
                call.enqueue(new Callback<ResponseToken>() {
                    @Override
                    public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                        if (response.isSuccessful()) {
                            ResponseToken responseData = response.body();
                            if (responseData != null) {
                                Rent();
                                String token = responseData.getToken();
                                String title = "Rented Room";
                                String body = "User Rented Your Room";
                                String img = "image_url";
                                Map<String, String> data = new HashMap<>();
                                data.put("key1", "value1");
                                data.put("key2", "value2");
                                NotificationRent(token, title, body, img, data);
                                Log.d("TAG", "Token: " + token);
                               Intent intent = new Intent(OverviewRoom.this, BaseActivity.class);
                               startActivity(intent);
                            }
                        } else {
                            Log.d("TAG", "onResponseToken: " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseToken> call, Throwable t) {
                        Log.e("TAG", "onFailureToke: " + t.getMessage());
                    }
                });
            }
        });
    }
    private void NotificationRent(String token,String title,String body,String img,Map<String,String> data){
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
                    Toast.makeText(OverviewRoom.this, "" + responseAll, Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "onResponse Noti: " + responseAll.getMessage());
                } else {
                    Log.e("TAG", "onResponse Notielse: " + response.code());
                    Log.e("TAG", "onResponse Notifition: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseAll> call, Throwable t) {
                Toast.makeText(OverviewRoom.this, "Lỗi kết nối mạng hoặc máy chủ không phản hồi", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void Rent(){
        Intent intent = getIntent();
        idRoom = intent.getStringExtra("idRoom");
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        long userID = sharedPreferences.getLong("userId", 0);
        int numberPeople = 3;
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseAll> call = apiService.rent(Long.parseLong(idRoom),userID,numberPeople);
        call.enqueue(new Callback<ResponseAll>() {
            @Override
            public void onResponse(Call<ResponseAll> call, Response<ResponseAll> response) {
                if (response.isSuccessful()){
                    ResponseAll responseAll = response.body();
                    Toast.makeText(OverviewRoom.this, "rennt" + responseAll, Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("TAG", "onResponse Rent: " + errorBody);
                        Toast.makeText(OverviewRoom.this, "Error"+errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e("TAG", "onResponse Rent: Error reading error body"+e);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAll> call, Throwable t) {
                Log.e("TAG", "onFailure Rent: " + t.getMessage());
            }
        });
    }

    private void addMarker(GoogleMap map, LatLng position, String title) {
        map.addMarker(new MarkerOptions().position(position).title(title));
    }
    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        Intent intent = getIntent();
        addressRoom = intent.getStringExtra("addressRoom");
        area = intent.getStringExtra("area");
        String local = addressRoom + "," + area;
        if (ActivityCompat.checkSelfPermission(OverviewRoom.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(OverviewRoom.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        map.setMyLocationEnabled(true);
        GeocodingTask geocodingTask = new GeocodingTask(OverviewRoom.this, new GeocodingTask.GeocodingListener() {
            @Override
            public void onGeocodingResult(double latitude, double longitude) {
                Log.d("mapplist", "Latitude: " + latitude + ", Longitude: " + longitude);
                // addMarker(map,new LatLng(latitude,longitude),address);
                addMarker(map,new LatLng(latitude,longitude),local);

                map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
            }
            @Override
            public void onGeocodingFailed() {
                Log.e("mapplist", "Failed to get coordinates from address");
            }
        });
        geocodingTask.execute(local);
    }
    private void setToolbar(Toolbar toolbar,String name){
        setSupportActionBar(toolbar);
        SpannableString spannableString = new SpannableString(name);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(spannableString);
        toolbar.setNavigationIcon(R.drawable.backdetail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void mapping(){
        recyclerView = findViewById(R.id.listImgInRoom);
        progressBar = findViewById(R.id.progressBarImg);
        imgRoomMain = findViewById(R.id.imgRoomMain);
        toolbar = findViewById(R.id.toolbar);
        numberStarRoom = findViewById(R.id.numberStarRoom);
        mMapView = findViewById(R.id.user_list_map);
        btn_rent = findViewById(R.id.btn_rent);
    }
    private void getExtra(){
        Intent intent = getIntent();
        addressRoom = intent.getStringExtra("addressRoom");
        idRoom = intent.getStringExtra("idRoom");
        numberStar = intent.getStringExtra("numberStar");
        idHost = intent.getStringExtra("idHost");
        setToolbar(toolbar,addressRoom);
        numberStarRoom.setText(numberStar);
        String imageUrl = getIntent().getStringExtra("selected_image_url");
        Picasso.get().load(imageUrl).into(imgRoomMain);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }
    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}