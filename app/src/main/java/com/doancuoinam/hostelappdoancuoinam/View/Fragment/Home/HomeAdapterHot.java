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

public class HomeAdapterHot extends RecyclerView.Adapter<HomeAdapterHot.HomeViewHolder> {
    private List<Room> rooms;
    public void setRoomsHot(List<Room> rooms) {
        this.rooms = rooms;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeAdapterHot.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pleaces_hot, parent, false);
        return new HomeAdapterHot.HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapterHot.HomeViewHolder holder, int position) {
        Room room = rooms.get(position);
        holder.bind(room);
    }

    @Override
    public int getItemCount() {
        return rooms != null ? rooms.size() : 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRoomHot;
        TextView addressRoomHot,numberStarHot,areaHot,peopleRoom,typeRoom,priceRoomHot;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoomHot = itemView.findViewById(R.id.imgRoomHot);
            addressRoomHot = itemView.findViewById(R.id.addressRoomHot);
            numberStarHot = itemView.findViewById(R.id.numberStarHot);
            areaHot = itemView.findViewById(R.id.areaHot);
            peopleRoom = itemView.findViewById(R.id.peopleRoom);
            typeRoom = itemView.findViewById(R.id.typeRoom);
            priceRoomHot = itemView.findViewById(R.id.priceRoomHot);

        }
        void bind(Room room) {
            String imageUrl = room.getImg();
            Picasso.get().load(imageUrl).into(imgRoomHot);
            addressRoomHot.setText(room.getAddress());
            numberStarHot.setText(String.valueOf(room.getNumberOfStars()));
            areaHot.setText(String.valueOf(room.getArea()));
            peopleRoom.setText(String.valueOf(room.getPeople())+" People");
            typeRoom.setText(String.valueOf(room.getType()));
            priceRoomHot.setText("$"+String.valueOf(room.getPrice()));
        }
    }
}
