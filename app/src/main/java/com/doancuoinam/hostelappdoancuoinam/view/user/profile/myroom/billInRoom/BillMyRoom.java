package com.doancuoinam.hostelappdoancuoinam.view.user.profile.myroom.billInRoom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.TotalBill;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.empty.EmptyActivity;
import com.doancuoinam.hostelappdoancuoinam.toast.ToastInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class BillMyRoom extends AppCompatActivity implements ToastInterface {
    RecyclerView recyclerView;
    AdapterBill adapterBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_my_room);
        recyclerView = findViewById(R.id.listBill);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        adapterBill = new AdapterBill();
        int nameTitle = R.string.billmyroom;
        setToolbar(toolbar,nameTitle);
        recyclerView.setAdapter(adapterBill);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        long userID = sharedPreferences.getLong("userId", 0);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<TotalBill>> call = apiService.getAllBill(userID);
        call.enqueue(new Callback<List<TotalBill>>() {
            @Override
            public void onResponse(Call<List<TotalBill>> call, Response<List<TotalBill>> response) {
                if (response.isSuccessful()) {
                    List<TotalBill> totalBills = response.body();
                    if (totalBills.isEmpty()){
                        Intent intent = new Intent(BillMyRoom.this, EmptyActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        adapterBill.setDataBill(totalBills);
                    }
                } else {
                    //null
                    createCustomToast("Bạn Chưa có Hoá đơn!", "", MotionToastStyle.INFO);
                }
            }

            @Override
            public void onFailure(Call<List<TotalBill>> call, Throwable t) {
                Toast.makeText(BillMyRoom.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setToolbar(Toolbar toolbar,int name){
        setSupportActionBar(toolbar);
        String title = getString(name);
        SpannableString spannableString = new SpannableString(title);
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

    @Override
    public void createCustomToast(String message, String description, MotionToastStyle style) {
        MotionToast.Companion.createToast(this, message, description, style, MotionToast.GRAVITY_BOTTOM, MotionToast.LONG_DURATION, ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular));
    }
}