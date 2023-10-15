package com.doancuoinam.hostelappdoancuoinam.View.Fragment.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapterNew extends RecyclerView.Adapter<HomeAdapterNew.HomeViewHolder>{
    private List<Room> rooms;

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HomeAdapterNew.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_places_near, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapterNew.HomeViewHolder holder, int position) {
        Room room = rooms.get(position);
        holder.bind(room);
    }

    @Override
    public int getItemCount() {
        return rooms != null ? rooms.size() : 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRoomNear;
        TextView txtRoomNear,numberStar;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoomNear = itemView.findViewById(R.id.imgRoomNear);
            txtRoomNear = itemView.findViewById(R.id.txtRoomNear);
            numberStar = itemView.findViewById(R.id.numberStar);
        }
        void bind(Room room) {
            String imageUrl = room.getImg();
           Picasso.get().load(imageUrl).into(imgRoomNear);
            txtRoomNear.setText(room.getAddress());
            numberStar.setText(String.valueOf(room.getNumberOfStars()));

        }
    }
}
