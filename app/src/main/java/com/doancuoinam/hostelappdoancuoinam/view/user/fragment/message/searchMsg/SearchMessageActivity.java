package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.searchMsg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.toast.ToastInterface;
import com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.MessageList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class SearchMessageActivity extends AppCompatActivity  implements ToastInterface {
    EditText editSearchMsg;
    RecyclerView searchRecyclerView;
    ImageView back,SearchMsg;
    DatabaseReference usersReference;
    searchAdapter messageAdapter;
    List<MessageList> messageLists = new ArrayList<>();
    String phoneHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_message);
        editSearchMsg = findViewById(R.id.editSearchMsg);
        searchRecyclerView = findViewById(R.id.searchRecyclerView);
        SearchMsg = findViewById(R.id.SearchMsg);
        back = findViewById(R.id.back);
        Intent intent = getIntent();
        phoneHost = intent.getStringExtra("phoneRent");
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        messageAdapter = new searchAdapter();
        editSearchMsg.setText(phoneHost);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String PhoneSend = sharedPreferences.getString("userPhoneNumber", "");
        searchRecyclerView.setAdapter(messageAdapter);
        back.setOnClickListener(view -> onBackPressed());
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userName = userSnapshot.child("name").getValue(String.class);
                        String userPhoneNumber = userSnapshot.getKey();
                        String userAvt = userSnapshot.child("avt").getValue(String.class);
                        if (!userPhoneNumber.equals(PhoneSend)){
                            MessageList currentUser = new MessageList(userName, userPhoneNumber, userAvt);
                            messageLists.add(currentUser);
                        }
                    }
                    messageAdapter.setDataMessageAdapter(messageLists, SearchMessageActivity.this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                createCustomToast("Failed ☹️", "Failed to retrieve user data", MotionToastStyle.ERROR);
              //  Toast.makeText(SearchMessageActivity.this, "Failed to retrieve user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        SearchMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterUsersOne(phoneHost);
            }
        });
        editSearchMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterUsers(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void filterUsers(String searchText) {
        List<MessageList> filteredList = new ArrayList<>();
        for (MessageList user : messageLists) {
            if (containsAllCharacters(user.getPhone(), searchText)) {
                filteredList.add(user);
            }
        }

        messageAdapter.setDataMessageAdapter(filteredList, SearchMessageActivity.this);
    }

    private boolean containsAllCharacters(String input, String searchText) {
        for (char character : searchText.toCharArray()) {
            if (input.indexOf(character) == -1) {
                return false;
            }
        }
        return true;
    }

    private void filterUsersOne(String searchText) {
        List<MessageList> filteredList = new ArrayList<>();
        for (MessageList user : messageLists) {
            if (user.getPhone().contains(searchText)) {
                filteredList.add(user);
            }
        }
        messageAdapter.setDataMessageAdapter(filteredList, SearchMessageActivity.this);
    }
    @Override
    public void createCustomToast(String message, String description, MotionToastStyle style) {
        MotionToast.Companion.createToast(this, message, description, style, MotionToast.GRAVITY_BOTTOM, MotionToast.LONG_DURATION, ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular));
    }
}
