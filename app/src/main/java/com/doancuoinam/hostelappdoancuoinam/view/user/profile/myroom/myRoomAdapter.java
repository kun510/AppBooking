package com.doancuoinam.hostelappdoancuoinam.view.user.profile.myroom;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Rent;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.user.profile.myroom.overviewMyyRoom.ChoseOverviewMyRoom;
import com.squareup.picasso.Picasso;

import java.util.List;

public class myRoomAdapter extends RecyclerView.Adapter<myRoomAdapter.RoomViewHolder>{
    private List<Rent> rooms;

    public void setMyRooms(List<Rent> rooms) {
        this.rooms = rooms;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public myRoomAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemmyroom, parent, false);
        return new myRoomAdapter.RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myRoomAdapter.RoomViewHolder holder, int position) {
        Rent room = rooms.get(position);
        holder.bind(room);
        holder.btn_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getBindingAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
//                    String message = "Nút được nhấn ở vị trí: " + clickedPosition;
//                    Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(v.getContext(), ChoseOverviewMyRoom.class);
                    v.getContext().startActivity(intent);
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
        TextView priceRoom,numberPeople,electricityBill,waterBill,numberRoom,descriptionRoom,areaRoom,addrRoom;
        Button btn_manager;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoom = itemView.findViewById(R.id.imgRoom);
            priceRoom = itemView.findViewById(R.id.priceRoom);
            numberPeople = itemView.findViewById(R.id.numberPeople);
            electricityBill = itemView.findViewById(R.id.electricitybill);
            waterBill = itemView.findViewById(R.id.waterBill);
            numberRoom = itemView.findViewById(R.id.numberRoom);
            descriptionRoom = itemView.findViewById(R.id.descriptionRoom);
            areaRoom = itemView.findViewById(R.id.areaRoom);
            addrRoom = itemView.findViewById(R.id.addRoom);
            btn_manager = itemView.findViewById(R.id.btn_selectManager);
        }
        void bind(Rent rent) {
            String imageUrl = rent.getRoom().getImg();
            Picasso.get().load(imageUrl).into(imgRoom);
            addrRoom.setText(rent.getRoom().getBoardingHostel().getAddress());
            electricityBill.setText(String.valueOf(rent.getRoom().getElectricBill()));
            waterBill.setText(String.valueOf(rent.getRoom().getWaterBill()));
            areaRoom.setText(String.valueOf(rent.getRoom().getBoardingHostel().getArea()));
            numberPeople.setText( "max " + rent.getRoom().getPeople() + " people");
            numberRoom.setText(rent.getRoom().getNumberRoom() + " Room");
            descriptionRoom.setText("$"+String.valueOf(rent.getRoom().getDescription()));
            priceRoom.setText("$"+String.valueOf(rent.getRoom().getPrice()));
        }
    }
}
