package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    List<MessageList> messageLists;
    Context context;

    public void  SetDataMessageAdapter(List<MessageList> messageLists, Context context) {
        this.messageLists = messageLists;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
        MessageList list = messageLists.get(position);
        holder.name.setText(list.getName());
        holder.lastMessage.setText(list.getLastMessage());
        if (list.getUnseenMessage() == 0){
            holder.unseen.setVisibility(View.GONE);
        }else {
            holder.unseen.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return messageLists != null ? messageLists.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profilePic;
        TextView name,lastMessage,unseen;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            name = itemView.findViewById(R.id.name);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            unseen = itemView.findViewById(R.id.unseen);
        }
    }
}
