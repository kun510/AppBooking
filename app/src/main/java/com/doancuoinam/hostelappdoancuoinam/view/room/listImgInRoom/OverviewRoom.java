package com.doancuoinam.hostelappdoancuoinam.view.room.listImgInRoom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ImgRoom;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.view.map.GeocodingTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OverviewRoom extends AppCompatActivity implements OnMapReadyCallback {
    RecyclerView recyclerView;
    AdapterOverview adapterOverview;
    ProgressBar progressBar;
    ImageView imgRoomMain;
    String addressRoom,idRoom,numberStar,area;
    TextView numberStarRoom;
    Toolbar toolbar;
    MapView mMapView;
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
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                float latitudeLocal = sharedPreferences.getFloat("latitude", 0);
                float longitudeLocal = sharedPreferences.getFloat("longitude", 0);
                map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitudeLocal, longitudeLocal)));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitudeLocal, longitudeLocal), 15));
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
    }
    private void getExtra(){
        Intent intent = getIntent();
        addressRoom = intent.getStringExtra("addressRoom");
        idRoom = intent.getStringExtra("idRoom");
        numberStar = intent.getStringExtra("numberStar");
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