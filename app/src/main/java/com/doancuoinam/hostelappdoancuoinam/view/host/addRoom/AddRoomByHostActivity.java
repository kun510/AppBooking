package com.doancuoinam.hostelappdoancuoinam.view.host.addRoom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.Request.AddBoarding;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseAll;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.toast.ToastInterface;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listRoom.ListRoom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class AddRoomByHostActivity extends AppCompatActivity implements ToastInterface {
     ImageView btnAddImg, imgInsert;
     Spinner spinner;
    String listType[]={"Type","Hostel", "Motel","Apartment"};
    private   EditText description,numberRoom,ElectricBill,WaterBill,price,people;
     LinearLayout btnInsert;
    private static final int PICK_IMAGE_REQUEST = 1;
     String imagePath;
     ProgressBar progressBar;
     String idBoardingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room_by_host);
        Mapping();
        Intent intent = getIntent();
        idBoardingIntent = intent.getStringExtra("boardingId");
        long boardingId = Long.parseLong(idBoardingIntent);
        Toast.makeText(this, "aaa: "+boardingId, Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listType
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                handleInsert();
            }
        });

    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            imagePath = getPathFromUri(uri);
            imgInsert.setImageURI(Uri.parse("file://" + imagePath));

            displayImages();
        }
    }

    private String getPathFromUri(Uri uri) {
        if (uri == null) {
            return null;
        }

        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            if (inputStream != null) {
                String fileName = getFileName(uri);
                File file = createTempFile(fileName);
                if (file != null) {
                    try (OutputStream outputStream = new FileOutputStream(file)) {
                        byte[] buffer = new byte[4 * 1024];
                        int read;
                        while ((read = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, read);
                        }
                        outputStream.flush();
                        return file.getAbsolutePath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (columnIndex != -1) {
                        result = cursor.getString(columnIndex);
                    }
                }
            }
        }

        if (result == null) {
            result = uri.getLastPathSegment();
        }

        return result;
    }

    private File createTempFile(String fileName) {
        try {
            File file = File.createTempFile("prefix", fileName);
            file.deleteOnExit();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void displayImages() {
        imgInsert.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }
    private void handleInsert() {
        String selectedType = spinner.getSelectedItem().toString();
        String descriptionRoom = description.getText().toString();
        String numberRoomInRoom =numberRoom.getText().toString();
        String ElectricBillRoom = ElectricBill.getText().toString();
        String WaterBillRoom = WaterBill.getText().toString();
        String priceRoom = price.getText().toString();
        String peopleRoom = people.getText().toString();
        if (selectedType.trim().isEmpty() || descriptionRoom.trim().isEmpty() ||
                numberRoomInRoom.trim().isEmpty() || ElectricBillRoom.trim().isEmpty() ||
                WaterBillRoom.trim().isEmpty() || priceRoom.trim().isEmpty() || peopleRoom.trim().isEmpty()){
            createCustomToast("Failed ☹️", "điền đầy đủ", MotionToastStyle.ERROR);
            return;
        }
        MultipartBody.Part descriptionPart = MultipartBody.Part.createFormData("description", descriptionRoom);
        MultipartBody.Part numberRoomPart = MultipartBody.Part.createFormData("numberRoom", numberRoomInRoom);
        MultipartBody.Part electricBillPart = MultipartBody.Part.createFormData("ElectricBill", ElectricBillRoom);
        MultipartBody.Part waterBillPart = MultipartBody.Part.createFormData("WaterBill", WaterBillRoom);
        MultipartBody.Part pricePart = MultipartBody.Part.createFormData("price", priceRoom);
        MultipartBody.Part peoplePart = MultipartBody.Part.createFormData("people", peopleRoom);
        MultipartBody.Part selectedTypePart = MultipartBody.Part.createFormData("type", selectedType);

        Intent intent = getIntent();
        idBoardingIntent = intent.getStringExtra("boardingId");
        long boardingId = Long.parseLong(idBoardingIntent);
        Toast.makeText(this, "id trojj"+boardingId, Toast.LENGTH_SHORT).show();
        Uri imageUri = Uri.parse("file://" + imagePath);
        MultipartBody.Part imagePart = prepareImageFile(imageUri);
        if (imagePart == null){
            createCustomToast("Failed ☹️", "điền đầy đủ", MotionToastStyle.ERROR);
            return;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long hostId = sharedPreferences.getLong("userId", 0);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<ResponseAll> call = apiService.addRoom(
                descriptionPart,
                numberRoomPart,
                electricBillPart,
                waterBillPart,
                pricePart,
                peoplePart,
                selectedTypePart,
                boardingId,
                hostId,imagePart
        );

        call.enqueue(new Callback<ResponseAll>() {
            @Override
            public void onResponse(Call<ResponseAll> call, Response<ResponseAll> response) {
                if (response.isSuccessful()) {
                    ResponseAll result = response.body();
                    Log.d("TAG", "onResponse: "+ result);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddRoomByHostActivity.this, "Thêm phòng thành công", Toast.LENGTH_SHORT).show();
                    createCustomToast("success 😍", "Thêm Phòng Thành Công!", MotionToastStyle.SUCCESS);
                    onBackPressed();

                } else {
                    try {
                        String errorBodyString = response.errorBody().string();
                        Log.e("TAG", "Error Response Body: " + errorBodyString);
                        createCustomToast("Fail", "Lỗi !"+ errorBodyString, MotionToastStyle.ERROR);
                        progressBar.setVisibility(View.GONE);
                    } catch (IOException e) {
                        Log.e("TAG", "Error Response error response body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAll> call, Throwable t) {
                Log.e("TAG", "Call Api Fail: ", t);
                createCustomToast("Failed ☹️", "Lỗi Mạng", MotionToastStyle.ERROR);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private MultipartBody.Part prepareImageFile(Uri uri) {
        File file = new File(uri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("image", file.getName(), requestFile);
    }
    private void Mapping(){
        description = findViewById(R.id.description);
        numberRoom = findViewById(R.id.numberRoom);
        ElectricBill = findViewById(R.id.ElectricBill);
        WaterBill = findViewById(R.id.WaterBill);
        price = findViewById(R.id.price);
        people = findViewById(R.id.people);
        btnAddImg = findViewById(R.id.addImg);
        imgInsert = findViewById(R.id.imgInsert);
        spinner = findViewById(R.id.spinner);
        btnInsert = findViewById(R.id.insert);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void createCustomToast(String message, String description, MotionToastStyle style) {
        MotionToast.Companion.createToast(this, message, description, style, MotionToast.GRAVITY_BOTTOM, MotionToast.LONG_DURATION, ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular));
    }
}