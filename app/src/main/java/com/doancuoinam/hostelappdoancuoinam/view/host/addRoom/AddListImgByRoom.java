package com.doancuoinam.hostelappdoancuoinam.view.host.addRoom;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class AddListImgByRoom extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MAX_IMAGES_DISPLAY = 5;
    private List<String> imagePaths = new ArrayList<>();
    private LinearLayout imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list_img_by_room);
        Button pickImageButton = findViewById(R.id.pickImageButton);
        Button uploadButton = findViewById(R.id.uploadButton);
        imageContainer = findViewById(R.id.imageContainer);

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendApiRequest();
            }
        });
    }

    private void openGallery() {
        if (imagePaths.size() >= MAX_IMAGES_DISPLAY) {
            Toast.makeText(this, "You can only select up to 5 images", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri uri = clipData.getItemAt(i).getUri();
                    String imagePath = getPathFromUri(uri);
                    if (imagePath != null && !imagePath.isEmpty()) {
                        imagePaths.add(imagePath);
                    } else {
                        Log.e("Error", "Invalid imagePath at index " + i);
                    }
                }
            } else if (data.getData() != null) {
                Uri uri = data.getData();
                String imagePath = getPathFromUri(uri);
                if (imagePath != null && !imagePath.isEmpty()) {
                    imagePaths.add(imagePath);
                } else {
                    Log.e("Error", "Invalid imagePath for single selection");
                }
            }

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
        imageContainer.removeAllViews();
        for (int i = 0; i < imagePaths.size() ; i++) {
            String imagePath = imagePaths.get(i);
            LinearLayout imageLayout = new LinearLayout(this);
            imageLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            imageLayout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));

            imageView.setImageURI(Uri.parse("file://" + imagePath));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


            ImageButton deleteButton = new ImageButton(this);
            deleteButton.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f));
            deleteButton.setImageResource(R.drawable.baseline_delete_24);
            final int position = i;
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteImage(position);
                }
            });

            imageLayout.addView(imageView);
            imageLayout.addView(deleteButton);
            imageContainer.addView(imageLayout);
         }

    }
    private void deleteImage(int position) {
        if (position >= 0 && position < imagePaths.size()) {
            imagePaths.remove(position);
            displayImages();
        }
    }
    private void sendApiRequest() {
       // long idRoomValue = 3;
     //   long hostIdValue = 1;
        Intent intent = getIntent();
        String idRoomValue = intent.getStringExtra("idRoom");
        String hostIdValue = intent.getStringExtra("idHost");
        RequestBody idRoom = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idRoomValue));
        RequestBody hostId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(hostIdValue));

        List<MultipartBody.Part> images = new ArrayList<>();
        Log.d("ImagePaths", "Number of images: " + imagePaths.size());

        if (imagePaths.size() > 0) {
            for (int i = 0; i < imagePaths.size(); i++) {
                String imagePath = imagePaths.get(i);
                if (imagePath != null && !imagePath.isEmpty()) {
                    File file = new File(imagePath);
                    if (file.exists() && file.canRead()) {
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image" + (i + 1), file.getName(), requestFile);
                        images.add(imagePart);
                        Toast.makeText(this, "File added successfully" + i, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error: File does not exist or cannot be read", Toast.LENGTH_SHORT).show();
                        Log.e("Error", "File does not exist or cannot be read: " + imagePath);
                    }
                } else {
                    Toast.makeText(this, "Error: Invalid imagePath", Toast.LENGTH_SHORT).show();
                    Log.e("Error", "Invalid imagePath: " + imagePath);
                }
            }
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<Response> call = apiService.addImgRoom(idRoom, hostId,
                    images.size() > 0 ? images.get(0) : null,
                    images.size() > 1 ? images.get(1) : null,
                    images.size() > 2 ? images.get(2) : null,
                    images.size() > 3 ? images.get(3) : null,
                    images.size() > 4 ? images.get(4) : null);

            call.enqueue(new Callback<Response>() {

                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful()) {
                        Log.d("API", "Success");
                        Toast.makeText(AddListImgByRoom.this, "Success", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        try {
                            String errorBodyString = response.errorBody().string();
                            Log.e("API", "Error Body: " + errorBodyString);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.e("API", "Failure: " + t.getMessage());
                }
            });

        } else {
            Log.e("API", "Error: No images to upload");
            Toast.makeText(this, "Error: No images to upload", Toast.LENGTH_SHORT).show();
        }
    }
}
