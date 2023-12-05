package com.doancuoinam.hostelappdoancuoinam.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.Request.LoginRequest;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.Response;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseLogin;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.toast.ToastInterface;
import com.doancuoinam.hostelappdoancuoinam.view.host.BaseActivityHost;
import com.doancuoinam.hostelappdoancuoinam.view.user.intro.ChoseAreaActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class Login extends AppCompatActivity implements ToastInterface {
    EditText phone,pass;
    TextView fogot;
    Button btn_login;
    String tokenD;
    FirebaseAuth auth;
    public static final String ChannelID = "default_channel_id" ;
    private boolean mLocationPermissionGranted = false;
    public static final int ERROR_DIALOG_REQUEST = 9001;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
    public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9003;
    private FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createChannelNotification();
        btn_login = findViewById(R.id.btn_login);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtPhone = phone.getText().toString();
                String txtPass = pass.getText().toString();
                if (txtPhone.trim().isEmpty() || txtPass.trim().isEmpty()){
                    createCustomToast("Failed ☹️", "điền đầy đủ!", MotionToastStyle.ERROR);
                    return;
                }
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userPhoneNumber", txtPhone);
                editor.apply();
                loginUser(txtPhone,txtPass);
            }
        });
    }
    private void getLastKnownLocation() {
        Log.d("TAG", "getLastKnownLocation: called.");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                    Log.d("TAG", "onComplete: latitude: " + geoPoint.getLatitude());
                    Log.d("TAG", "onComplete: longitude: " + geoPoint.getLongitude());
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putFloat("latitude", (float) geoPoint.getLatitude());
                    editor.putFloat("longitude",(float)   geoPoint.getLongitude());
                    editor.apply();
                }
            }
        });

    }
    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkMapServices()){
            if(mLocationPermissionGranted){
                getLastKnownLocation();
            }
            else{
                getLocationPermission();
            }
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getLastKnownLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        Log.d("TAG", "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Login.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d("TAG", "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d("TAG", "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Login.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    getLastKnownLocation();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){

                }
                else{
                    getLocationPermission();
                }
            }
        }

    }
    private void loginUser(String phoneNumber, String password) {
        LoginRequest loginRequest = new LoginRequest(phoneNumber, password);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseLogin> call = apiService.login(loginRequest);

        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, retrofit2.Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    ResponseLogin loginResponse = response.body();
                    String message = loginResponse.getMessage();
                    long userId = loginResponse.getUserId();
                    String emailUserPut = loginResponse.getEmail();
                    FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
                        saveTokenToDatabase(userId, token);
                         tokenD = token;
                        Log.d("TAG", "onResponse: "+ token);
                    });

                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("userId", userId);
                    editor.putString("email",emailUserPut);
                    editor.apply();

                    switch (message) {
                        case "Admin":
                            createCustomToast("success 😍", "You are Admin!", MotionToastStyle.SUCCESS);
                            break;
                        case "Host":
                            createCustomToast("success 😍", "Login Completed successfully!", MotionToastStyle.SUCCESS);
                            startActivity(new Intent(Login.this, BaseActivityHost.class));
                            break;
                        case "User":
                            createCustomToast("success 😍", "Login Completed successfully!", MotionToastStyle.SUCCESS);
                            startActivity(new Intent(Login.this, ChoseAreaActivity.class));
                            break;
                        case "Wait for confirmation":
                            createCustomToast("Chờ Xác Nhận", "", MotionToastStyle.WARNING);
                            break;
                        case "User is banned":
                            Toast.makeText(Login.this, "tài khoản của bạn đang bị hạn chế", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, BaseActivityHost.class));
                            break;
                        default:
                            createCustomToast("Failed ☹️", "Unknown role or error!", MotionToastStyle.ERROR);
                    }
                } else {
                    createCustomToast("Failed ☹️", "Check username or password!", MotionToastStyle.ERROR);
                }
            }
            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e("cHcww", "onFailure: "+ t.getMessage()  );
               // Toast.makeText(Login.this, "Lỗi kết nối mạng hoặc máy chủ không phản hồi", Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void saveTokenToDatabase(long userId, String token){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Response> call = apiService.addToken(token,userId);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    Response result = response.body();
                    if (result != null && result.isSuccess()) {

                        Toast.makeText(Login.this, "token OK", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, "token fail", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "fail query", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                if (t instanceof IOException) {
                    //  Log.e("cHcww", "onFailure: "+ t.getMessage()  );
                    Toast.makeText(Login.this, "Lỗi kết nối mạng hoặc máy chủ không phản hồi", Toast.LENGTH_SHORT).show();
                } else {
                   // Toast.makeText(Login.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void createChannelNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(ChannelID,"default_channel_id",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
    }

    @Override
    public void createCustomToast(String message, String description, MotionToastStyle style) {
        MotionToast.Companion.createToast(this, message, description, style, MotionToast.GRAVITY_BOTTOM, MotionToast.LONG_DURATION, ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular));
    }
}