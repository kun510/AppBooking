package com.doancuoinam.hostelappdoancuoinam.view.map;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
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


public class Map extends Fragment implements OnMapReadyCallback{
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mMapView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = view.findViewById(R.id.user_list_map);
        initGoogleMap(savedInstanceState);
        return view;
    }
    private void initGoogleMap(Bundle savedInstanceState){

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync((OnMapReadyCallback) this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        map.setMyLocationEnabled(true);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Room>> call = apiService.getRoomMap();
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    List<Room> rooms = response.body();
                    for (Room room : rooms) {
                        String address = room.getBoardingHostel().getAddress();
                        String area = room.getBoardingHostel().getArea();
                        String geoCo = address + "," + area;
                        String imageUrl = room.getImg();
                        String price = room.getPrice();
                        String numberRoom = room.getNumberRoom();
                        try {
                            GeocodingTask geocodingTask = new GeocodingTask(getContext(), new GeocodingTask.GeocodingListener() {
                                @Override
                                public void onGeocodingResult(double latitude, double longitude) {
                                    Log.d("mapplist", "Latitude: " + latitude + ", Longitude: " + longitude);
                                   // addMarker(map,new LatLng(latitude,longitude),address);
                                    addMarker(map,new LatLng(latitude,longitude),price,numberRoom,address,imageUrl,80,80);
                                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
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
                            geocodingTask.execute(geoCo);
                        }catch (Exception e) {
                            Log.e("mapplist", "Exception during geocoding: " + e.getMessage());
                        }


                    }
                } else {
                    Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailureListRoomHot: " + t.getMessage());
            }
        });
    }
//    private void addMarker(GoogleMap map, LatLng position, String title,String price,String numberRoom) {
//        map.addMarker(new MarkerOptions().position(position).title(title+price+numberRoom));
//    }

    //hien thi hinh anh
    private void addMarker(GoogleMap map, LatLng position,String price,String numberRoom ,String title, Object markerData,
                           int targetWidth, int targetHeight) {
        if (markerData instanceof String) {
            String imageUrl = (String) markerData;
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(resizedBitmap);
                    addMarker(map, position," Price: "+price," Number Room: "+numberRoom ,title, bitmapDescriptor,targetWidth,targetHeight);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Log.e("mapplist", "Failed to load image: " + e.getMessage());
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            };
            Picasso.get().load(imageUrl).into(target);
        } else if (markerData instanceof BitmapDescriptor) {
            map.addMarker(new MarkerOptions().position(position).title(title+price+numberRoom).icon((BitmapDescriptor) markerData));
        }
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