package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.MessageModel;
import com.doancuoinam.hostelappdoancuoinam.view.user.room.listImgInRoom.AdapterOverview;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder>  {
    List<MessageModel> messages;
    Context context;
    public void setData(List<MessageModel> messages,Context context){
        this.messages = messages;
        this.context = context;
    }
    @NonNull
    @Override
    public ChatAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatAdapter.MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MessageViewHolder holder, int position) {
        MessageModel message = messages.get(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userPhoneNumber = sharedPreferences.getString("userPhoneNumber", "");
        if (message.getSenderId().equals(userPhoneNumber)) {
            holder.left_chat_layout.setVisibility(View.GONE);
            holder.right_chat_layout.setVisibility(View.VISIBLE);
            holder.right_chat_textview.setText(message.getText());

        } else {
            holder.right_chat_layout.setVisibility(View.GONE);
            holder.left_chat_layout.setVisibility(View.VISIBLE);
            holder.left_chat_textview.setText(message.getText());
        }
    }

    @Override
    public int getItemCount() {
        return messages != null ? messages.size() : 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView right_chat_textview,left_chat_textview;
        LinearLayout right_chat_layout,left_chat_layout;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            right_chat_textview = itemView.findViewById(R.id.right_chat_textview);
            left_chat_textview = itemView.findViewById(R.id.left_chat_textview);
            right_chat_layout = itemView.findViewById(R.id.right_chat_layout);
            left_chat_layout = itemView.findViewById(R.id.left_chat_layout);
        }
    }
}
