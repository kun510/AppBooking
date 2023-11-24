package com.doancuoinam.hostelappdoancuoinam.updateUser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Users;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseAll;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.view.host.addRoom.AddRoomByHostActivity;

import java.io.ByteArrayOutputStream;
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

public class UpdateUser extends AppCompatActivity {
   private EditText address,name,email;
   private ImageView imgInsert,btnAddImg;
    LinearLayout update;
    ProgressBar progressBar;
    private static final int PICK_IMAGE_REQUEST = 1;
    String imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        Mapping();

        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
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
        String addressUser = address.getText().toString();
        String nameUser = name.getText().toString();
        String emailsUser = email.getText().toString();

        MultipartBody.Part addressPart = MultipartBody.Part.createFormData("address", addressUser);
        MultipartBody.Part emailPart = MultipartBody.Part.createFormData("email", emailsUser);
        MultipartBody.Part namePart = MultipartBody.Part.createFormData("name", nameUser);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", 0);

        Uri imageUri = Uri.parse("file://" + imagePath);
        MultipartBody.Part imagePart = prepareImageFile(imageUri);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Users> call = apiService.updateUser(
                userId,addressPart,emailPart,namePart,imagePart
        );
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()){
                    Users updatedUser = response.body();
                    Log.d("TAG", "onResponse: "+ updatedUser);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(UpdateUser.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    },2000);
                } else {
                    Toast.makeText(UpdateUser.this, "Cập nhật bị lỗi", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.e("TAG", "Call Api Fail: ", t);
                Toast.makeText(UpdateUser.this,"Lỗi Mạng", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private MultipartBody.Part prepareImageFile(Uri uri) {
        File file = new File(uri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("image1", file.getName(), requestFile);
    }
    private void Mapping(){
        address = findViewById(R.id.address);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        imgInsert = findViewById(R.id.imgInsert);
        btnAddImg = findViewById(R.id.addImg);
        update = findViewById(R.id.update);
        progressBar = findViewById(R.id.progressBar);
    }
}