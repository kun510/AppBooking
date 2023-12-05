package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listRoom;

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

public class ListRoomAdapter extends RecyclerView.Adapter<ListRoomAdapter.ListRoomViewHolder> {
    List<Room> listRoom;
    public void setRooms(List<Room> rooms) {
        this.listRoom = rooms;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListRoomAdapter.ListRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_room, parent, false);
        return new ListRoomAdapter.ListRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRoomAdapter.ListRoomViewHolder holder, int position) {
        Room room = listRoom.get(position);
        holder.bind(room);
    }

    @Override
    public int getItemCount() {
        return listRoom != null ? listRoom.size() : 0;
    }

    public class ListRoomViewHolder extends RecyclerView.ViewHolder {
        TextView idRoom,khuVuc,danhGia;
        ImageView img;
        public ListRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            idRoom = itemView.findViewById(R.id.idRoom);
            khuVuc = itemView.findViewById(R.id.khuVuc);
            danhGia = itemView.findViewById(R.id.danhGia);
            img = itemView.findViewById(R.id.img);

        }
        void bind(Room room) {
            String imageUrl = room.getImg();
            Picasso.get().load(imageUrl).into(img);
            khuVuc.setText(room.getBoardingHostel().getArea());
            danhGia.setText(String.valueOf(room.getNumberOfStars()));
            idRoom.setText(String.valueOf(room.getId()));
        }
    }
}
