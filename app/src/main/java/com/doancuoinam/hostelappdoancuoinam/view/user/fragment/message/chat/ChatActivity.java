package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.chat;

import static java.sql.Types.TIMESTAMP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.MessageModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    CircleImageView avt;
    TextView userName,online;
    ImageView backBtn,btnSend;
    EditText sendEdt;
    String Name;
    DatabaseReference messagesReference;
    RecyclerView lstMess;
    ChatAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Mapping();
        Intent intent = getIntent();
        Name = intent.getStringExtra("name");
        String getPhone = intent.getStringExtra("mobile");
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String PhoneSend = sharedPreferences.getString("userPhoneNumber", "");
        userName.setText(Name);
        messagesReference = FirebaseDatabase.getInstance().getReference().child("messages");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setupRecyclerView();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Msg = sendEdt.getText().toString();
                sendMessage(PhoneSend, getPhone, Msg);
                sendEdt.setText("");

            }
        });
        retrieveAndDisplayMessages(PhoneSend, getPhone);
    }
    private void retrieveAndDisplayMessages(String senderId, String receiverId) {
        String conversationId = generateConversationId(senderId, receiverId);
        DatabaseReference conversationReference = messagesReference.child(conversationId);

        conversationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<MessageModel> messages = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageModel message = snapshot.getValue(MessageModel.class);
                    if (message != null) {
                        messages.add(message);
                    }
                }
                messageAdapter.setData(messages, ChatActivity.this);
                lstMess.scrollToPosition(messageAdapter.getItemCount() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               // Toast.makeText(ChatActivity.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        lstMess = findViewById(R.id.lstMess);
        lstMess.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        messageAdapter = new ChatAdapter();
        lstMess.setAdapter(messageAdapter);
    }
    private void sendMessage(String senderId, String receiverId, String messageText) {
        String conversationId = generateConversationId(senderId, receiverId);
        DatabaseReference conversationReference = messagesReference.child(conversationId);
        String messageId = conversationReference.push().getKey();
        DatabaseReference messageReference = conversationReference.child(messageId);
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("senderId", senderId);
        messageData.put("receiverId", receiverId);
        messageData.put("text", messageText);
        messageData.put("timestamp", ServerValue.TIMESTAMP);
        messageReference.setValue(messageData);

        List<MessageModel> currentMessages = messageAdapter.messages;
        if (currentMessages == null) {
            currentMessages = new ArrayList<>();
        }
        currentMessages.add(new MessageModel(senderId,receiverId,messageText,TIMESTAMP));
        messageAdapter.setData(currentMessages, this);
        lstMess.scrollToPosition(messageAdapter.getItemCount() - 1);
    }

        private String generateConversationId(String userId1, String userId2) {
        List<String> userIds = Arrays.asList(userId1, userId2);
        Collections.sort(userIds);
        return TextUtils.join("_", userIds);
    }


    public void Mapping(){
        avt = findViewById(R.id.Avt);
        userName = findViewById(R.id.userName);
        online = findViewById(R.id.online);
        backBtn = findViewById(R.id.backBtn);
        btnSend = findViewById(R.id.btnSend);
        sendEdt = findViewById(R.id.sendEdt);
    }

}