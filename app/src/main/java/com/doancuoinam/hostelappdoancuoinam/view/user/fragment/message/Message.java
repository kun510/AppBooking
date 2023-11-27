package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Message extends Fragment {
    RecyclerView MessageRecyclerView;
    List<MessageList> messageLists = new ArrayList<>();
    DatabaseReference databaseReference;
    CircleImageView userProfilePic;
    MessageAdapter messageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        MessageRecyclerView = view.findViewById(R.id.MessageRecyclerView);
        userProfilePic = view.findViewById(R.id.userProfilePic);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userPhoneNumber = sharedPreferences.getString("userPhoneNumber", "");
        String userNameVF = sharedPreferences.getString("userNameVF", "");

        FirebaseApp.initializeApp(getContext());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        Toast.makeText(getActivity(), "User ID: " + userPhoneNumber, Toast.LENGTH_SHORT).show();
        MessageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        messageAdapter = new MessageAdapter();
        MessageRecyclerView.setAdapter(messageAdapter);

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("TAG", "dataa: " + snapshot.getValue());
                progressDialog.dismiss();
                messageLists.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d("TAG", "forr: " );
                    String getMobile = dataSnapshot.getKey();
                    Log.d("TAG", "getMobile: " + getMobile);

                        String getName = dataSnapshot.child("name").getValue(String.class);
                        String getEmail = dataSnapshot.child("email").getValue(String.class);
                    Log.d("TAG", "getName: " + getName);
                    Log.d("TAG", "getEmail: " + getEmail);
                        MessageList messageList = new MessageList(getName, getMobile, "", getEmail, 0);
                        messageLists.add(messageList);
                }
                messageAdapter.SetDataMessageAdapter(messageLists, getContext());
                Log.d("TAG", "onDataChange: "+ messageLists.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               // progressDialog.dismiss();
                Log.e("TAG", "onCancelled: " + error.getMessage(), error.toException());
            }
        });

        return view;
    }
}