package com.doancuoinam.hostelappdoancuoinam.view.user.room.listImgInRoom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.doancuoinam.hostelappdoancuoinam.BaseActivity;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ImgRoom;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.NotificationMessaging;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseAll;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseToken;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.toast.ToastInterface;
import com.doancuoinam.hostelappdoancuoinam.view.host.addRoom.AddRoomByHostActivity;
import com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.searchMsg.SearchMessageActivity;
import com.doancuoinam.hostelappdoancuoinam.view.user.map.GeocodingTask;
import com.doancuoinam.hostelappdoancuoinam.view.user.rating.ListReviewInRoom.ListReviewInRoomActivity;
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
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;


public class OverviewRoom extends AppCompatActivity implements OnMapReadyCallback, ToastInterface {
    RecyclerView recyclerView;
    AdapterOverview adapterOverview;
    ProgressBar progressBar;
    ImageView imgRoomMain,backNe,review;
    String addressRoom,idRoom,numberStar,area,idHost,priceRoom;
    TextView numberStarRoom,addressList,tienDien,tienphong,tiendienphong,tiennuoc,songuoi;
    MapView mMapView;
    Button btn_rent;
    EditText number;
    String phoneHost;
    LottieAnimationView empty;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_detail_room);
        mapping();
        getExtra();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));
        adapterOverview = new AdapterOverview();
        recyclerView.setAdapter(adapterOverview);

        NotificationRentLogDevice();
        setToolbar();
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        showProgressBar();
        Call<List<ImgRoom>> call = apiService.getImgInRoom(Long.parseLong(idRoom));
        call.enqueue(new Callback<List<ImgRoom>>() {
            @Override
            public void onResponse(Call<List<ImgRoom>> call, Response<List<ImgRoom>> response) {
                if (response.isSuccessful()) {
                    List<ImgRoom> imgRooms = response.body();
                    if (imgRooms == null || imgRooms.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        empty.setVisibility(View.VISIBLE);
                    }else {
                        recyclerView.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.GONE);
                        adapterOverview.AdapterOverviewList(imgRooms);
                    }
                    hideProgressBar();
                } else {
                    Toast.makeText(OverviewRoom.this, "Dữ Liệu trống", Toast.LENGTH_SHORT).show();
                    hideProgressBar();
                }
            }

            @Override
            public void onFailure(Call<List<ImgRoom>> call, Throwable t) {
                Log.e("TAG", "onFailureImgRoom: " + t.getMessage());
                hideProgressBar();
            }
        });
        initGoogleMap(savedInstanceState);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String  idRoom = intent.getStringExtra("idRoom");
                String  numberStar = intent.getStringExtra("numberStar");
                String imageUrl = getIntent().getStringExtra("selected_image_url");
                Intent intentReview = new Intent(OverviewRoom.this, ListReviewInRoomActivity.class);
                intentReview.putExtra("idRoom",idRoom);
                intentReview.putExtra("numberStar",numberStar);
                intentReview.putExtra("selected_image_url",imageUrl);
                startActivity(intentReview);
            }
        });
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
                                Intent intent = getIntent();
                                String songuoine = intent.getStringExtra("songuoine");
                                String numberEmpty = number.getText().toString();
                                int PeopleReal = Integer.parseInt(songuoine);
                                int PeopleCheck = Integer.parseInt(numberEmpty);
                                if (numberEmpty.trim().isEmpty()){
                                    createCustomToast("Failed ☹️", "Điền Đẩy đủ", MotionToastStyle.ERROR);
                                    return;
                                }
                                if (PeopleCheck > PeopleReal){
                                    createCustomToast("Failed ☹️", "Điền thấp hơn số người tối đa", MotionToastStyle.ERROR);
                                    return;
                                }
                              int numberPeople = Integer.parseInt(numberEmpty) ;
                                Rent(responseData,numberPeople);
                                String token = responseData.getToken();
                                String title = "Rented Room";
                                String body = "User Rented Your Room";
                                String img = "image_url";
                                Map<String, String> data = new HashMap<>();
                                data.put("key1", "value1");
                                data.put("key2", "value2");
                                NotificationRent(token, title, body, img, data);
                                Log.d("TAG", "Token: " + token);


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
                    Toast.makeText(OverviewRoom.this, "thông báo thành công", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "onResponse Noti: " + responseAll.getMessage());
                } else {
                    Log.e("TAG", "onResponse Notielse: " + response.code());
                    Log.e("TAG", "onResponse Notifition: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseAll> call, Throwable t) {
              //  Toast.makeText(OverviewRoom.this, "Lỗi kết nối mạng hoặc máy chủ không phản hồi", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void Rent(ResponseToken responseData,int numberPeople ){
        Intent intent = getIntent();
        idRoom = intent.getStringExtra("idRoom");
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        long userID = sharedPreferences.getLong("userId", 0);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseAll> call = apiService.rent(Long.parseLong(idRoom),userID,numberPeople);
        call.enqueue(new Callback<ResponseAll>() {
            @Override
            public void onResponse(Call<ResponseAll> call, Response<ResponseAll> response) {
                if (response.isSuccessful()){
                    ResponseAll responseAll = response.body();
                    String token = responseData.getToken();
                    String title = "Rented Room";
                    String body = "User Rented Your Room";
                    String img = "image_url";
                    Map<String, String> data = new HashMap<>();
                    data.put("key1", "value1");
                    data.put("key2", "value2");
                    NotificationRent(token, title, body, img, data);
                    Log.d("TAG", "Token: " + token);
                    createCustomToast("Rent success 😍", "Hãy nhắn Tin với chủ trọ để trao đổi!", MotionToastStyle.SUCCESS);
                    nextChat();
                }else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("TAG", "onResponse Rent: " + errorBody);
                        createCustomToast("Failed ☹️", "Bạn đã đặt thuê phòng này rồi", MotionToastStyle.ERROR);
                    } catch (IOException e) {
                        Log.e("TAG", "onResponse Rent: Error reading error body");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAll> call, Throwable t) {
                Log.e("TAG", "onFailure Rent: " + t.getMessage());
                createCustomToast("Rent success 😍", "Hãy nhắn Tin với chủ trọ để trao đổi!", MotionToastStyle.SUCCESS);
                nextChat();
            }
        });
    }

    private void addMarker(GoogleMap map, LatLng position, String title) {
        map.addMarker(new MarkerOptions().position(position).title(title));
    }
    private void nextChat(){
        Intent intent1 = getIntent();
        phoneHost = intent1.getStringExtra("phoneHost");
        Intent intent = new Intent(OverviewRoom.this, SearchMessageActivity.class);
        intent.putExtra("phoneRent",phoneHost);
        startActivity(intent);
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
    private void setToolbar(){
        backNe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    private void mapping(){
        recyclerView = findViewById(R.id.recycer);
        progressBar = findViewById(R.id.progressBarImg);
        numberStarRoom = findViewById(R.id.star);
        mMapView = findViewById(R.id.user_list_map);
        btn_rent = findViewById(R.id.button);
        addressList = findViewById(R.id.addressList);
        tienDien = findViewById(R.id.textView10);
        tienphong = findViewById(R.id.tiendienne);
        backNe = findViewById(R.id.backNe);
        imgRoomMain= findViewById(R.id.imgRoomMain);
        number = findViewById(R.id.number);
        tiendienphong = findViewById(R.id.tienphong);
        tiennuoc = findViewById(R.id.tiennuoc);
        songuoi = findViewById(R.id.songuoi);
        empty = findViewById(R.id.empty);
        review = findViewById(R.id.imageView5);
    }
    private void getExtra(){
        Intent intent = getIntent();
        addressRoom = intent.getStringExtra("addressRoom");
        idRoom = intent.getStringExtra("idRoom");
        numberStar = intent.getStringExtra("numberStar");
        idHost = intent.getStringExtra("idHost");
        String a =  intent.getStringExtra("area");
        priceRoom = intent.getStringExtra("tienphong");
        String tiendienne =  intent.getStringExtra("tiendienne");
        String tiennuocne = intent.getStringExtra("tiennuocne");
        String songuoine = intent.getStringExtra("songuoine");
        phoneHost = intent.getStringExtra("phoneHost");
        Toast.makeText(this, "Phone" + phoneHost, Toast.LENGTH_SHORT).show();
        tiendienphong.setText(tiendienne);
        tiennuoc.setText(tiennuocne);
        songuoi.setText(songuoine);
        tienphong.setText(priceRoom);
        tienDien.setText(a);
        numberStarRoom.setText(numberStar);
        addressList.setText(addressRoom);
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


    @Override
    public void createCustomToast(String message, String description, MotionToastStyle style) {
        MotionToast.Companion.createToast(this, message, description, style, MotionToast.GRAVITY_CENTER, MotionToast.LONG_DURATION, ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular));
    }
}