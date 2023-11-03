package com.doancuoinam.hostelappdoancuoinam.View.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder>{
    private List<Room> rooms;
    public void setRoomsList(List<Room> rooms) {
        this.rooms = rooms;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoomAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new RoomAdapter.RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.RoomViewHolder holder, int position) {
        Room room = rooms.get(position);
        holder.bind(room);
        holder.btn_selectRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getBindingAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    String message = "Nút được nhấn ở vị trí: " + clickedPosition;
                    Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms != null ? rooms.size() : 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRoom;
        TextView priceRoom,numberPeople,status,numberRoom,descriptionRoom,areaRoom,addrRoom;
        Button btn_selectRoom;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoom = itemView.findViewById(R.id.imgRoom);
            priceRoom = itemView.findViewById(R.id.priceRoom);
            numberPeople = itemView.findViewById(R.id.numberPeople);
            status = itemView.findViewById(R.id.status);
            numberRoom = itemView.findViewById(R.id.numberRoom);
            descriptionRoom = itemView.findViewById(R.id.descriptionRoom);
            areaRoom = itemView.findViewById(R.id.areaRoom);
            addrRoom = itemView.findViewById(R.id.addRoom);
            btn_selectRoom = itemView.findViewById(R.id.btn_selectRoom);
        }
        void bind(Room room) {
            String imageUrl = room.getImg();
            Picasso.get().load(imageUrl).into(imgRoom);
            addrRoom.setText(room.getAddress());
            status.setText(String.valueOf(room.getStatus()));
            areaRoom.setText(String.valueOf(room.getArea()));
            numberPeople.setText(String.valueOf(room.getPeople())+" People");
            numberRoom.setText(String.valueOf(room.getNumberRoom()));
            descriptionRoom.setText("$"+String.valueOf(room.getDescription()));
            priceRoom.setText("$"+String.valueOf(room.getPrice()));
        }
    }
}
