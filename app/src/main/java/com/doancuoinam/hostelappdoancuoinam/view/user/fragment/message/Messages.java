package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.empty.EmptyActivity;
import com.doancuoinam.hostelappdoancuoinam.toast.ToastInterface;
import com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.searchMsg.SearchMessageActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class Messages extends Fragment implements ToastInterface {
    RecyclerView MessageRecyclerView;
    List<MessageList> messageLists = new ArrayList<>();
    CircleImageView userProfilePic;
    MessageAdapter messageAdapter;
    DatabaseReference usersReference, chatReference;

    ImageView searchMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = database.getReference("messages");
        MessageRecyclerView = view.findViewById(R.id.MessageRecyclerView);
        userProfilePic = view.findViewById(R.id.userProfilePic);
        searchMsg = view.findViewById(R.id.searchMsg);
        searchMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchMessageActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("userPhoneNumber", "");

        MessageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        messageAdapter = new MessageAdapter();

        MessageRecyclerView.setAdapter(messageAdapter);

        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    MessageList lastMessage = null;
                    for (DataSnapshot receiverSnapshot : messageSnapshot.getChildren()) {
                        String receiverId = (String) receiverSnapshot.child("receiverId").getValue();
                        if (receiverId.equals(user)) {
                            String text = (String) receiverSnapshot.child("text").getValue();
                            String senderId = (String) receiverSnapshot.child("senderId").getValue();
                            lastMessage = new MessageList(senderId, text);
                        }
                    }
                    if (lastMessage != null) {
                        MessageList finalLastMessage = lastMessage;
                        messageLists.clear();
                        usersReference.child(lastMessage.getPhone()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                                if (userSnapshot.exists()) {
                                    String userName = userSnapshot.child("name").getValue(String.class);
                                    String userPhoneNumber = userSnapshot.getKey();
                                    String userAvt = userSnapshot.child("avt").getValue(String.class);
                                    MessageList currentUser = new MessageList(userName, userPhoneNumber, userAvt, finalLastMessage.getLastMessage());
                                    messageLists.add(currentUser);
                                    messageAdapter.setDataMessageAdapter(messageLists, getContext());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                createCustomToast("Failed ☹️", "Failed to retrieve user data", MotionToastStyle.ERROR);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });

        return view;
    }

    @Override
    public void createCustomToast(String message, String description, MotionToastStyle style) {
        MotionToast.Companion.createToast(getActivity(), message, description, style, MotionToast.GRAVITY_BOTTOM, MotionToast.LONG_DURATION, ResourcesCompat.getFont(getContext(), www.sanju.motiontoast.R.font.helvetica_regular));
    }
}
