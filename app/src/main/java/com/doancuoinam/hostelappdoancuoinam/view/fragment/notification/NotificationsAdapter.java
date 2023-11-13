package com.doancuoinam.hostelappdoancuoinam.view.fragment.notification;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.NotificationApp;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Rent;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.profile.myroom.MyRoom;
import com.doancuoinam.hostelappdoancuoinam.view.profile.myroom.myRoomAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder>{
    private List<NotificationApp> notificationAppList;
    public void setNotificationAppList(List<NotificationApp> notificationApps) {
        this.notificationAppList = notificationApps;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public NotificationsAdapter.NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationsAdapter.NotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.NotificationsViewHolder holder, int position) {
        NotificationApp notificationApp = notificationAppList.get(position);
        holder.bind(notificationApp);
        holder.NotificationLiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getBindingAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(v.getContext(), MyRoom.class);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationAppList != null ? notificationAppList.size() : 0;
    }

    public class NotificationsViewHolder extends RecyclerView.ViewHolder {
        TextView contentNotification;
        ConstraintLayout NotificationLiner;

        public NotificationsViewHolder(@NonNull View itemView) {
            super(itemView);
            contentNotification = itemView.findViewById(R.id.contentNotification);
            NotificationLiner = itemView.findViewById(R.id.NotificationLiner);
        }

        void bind(NotificationApp notificationApp) {
           contentNotification.setText(notificationApp.getContent());
        }
    }
}
