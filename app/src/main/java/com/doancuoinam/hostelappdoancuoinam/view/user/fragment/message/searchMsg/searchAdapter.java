package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.searchMsg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.MessageAdapter;
import com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.MessageList;
import com.doancuoinam.hostelappdoancuoinam.view.user.fragment.message.chat.ChatActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.MyViewHolder> {
    List<MessageList> messageLists;
    Context context;

    public void setDataMessageAdapter(List<MessageList> messageLists, Context context) {
        this.messageLists = messageLists;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public searchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new searchAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,null));
    }

    @Override
    public void onBindViewHolder(@NonNull searchAdapter.MyViewHolder holder, int position) {
        MessageList list = messageLists.get(position);
        String avt = list.getAvt();
        Picasso.get().load(avt).into(holder.profilePic);
        holder.name.setText(list.getPhone());
        holder.lastMessage.setText(list.getName());
        String nameUser = list.getName();
        String phoneUser = list.getPhone();
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                intent.putExtra("name",nameUser);
                intent.putExtra("mobile",phoneUser);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageLists != null ? messageLists.size() : 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profilePic;
        TextView name,lastMessage,unseen;
        LinearLayout rootLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            name = itemView.findViewById(R.id.name);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            unseen = itemView.findViewById(R.id.unseen);
            rootLayout = itemView.findViewById(R.id.rootLayout);
        }
    }
}
