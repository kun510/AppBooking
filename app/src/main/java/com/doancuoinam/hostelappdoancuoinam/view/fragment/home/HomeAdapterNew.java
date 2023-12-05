package com.doancuoinam.hostelappdoancuoinam.view.fragment.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Boarding_host;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.room.roomList.RoomListByBoarding;
import com.doancuoinam.hostelappdoancuoinam.view.room.roomListSearch.RoomListSearch;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapterNew extends RecyclerView.Adapter<HomeAdapterNew.HomeViewHolder>{
    private List<Boarding_host> rooms;

    public void setRooms(List<Boarding_host> rooms) {
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
        Boarding_host room = rooms.get(position);
        holder.bind(room);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getBindingAdapterPosition();
                String roomName = holder.txtRoomNear.getText().toString();
                String id = String.valueOf(holder.idBoarding);
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    String message = "Nút được nhấn ở vị trí: " + id;
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), RoomListByBoarding.class);
                    intent.putExtra("idBoarding", id);
                    intent.putExtra("nameboarding",roomName);
                    view.getContext().startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return rooms != null ? rooms.size() : 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRoomNear;
        TextView txtRoomNear,numberStar;

        CardView cardView;
        long idBoarding;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoomNear = itemView.findViewById(R.id.imgRoomNear);
            txtRoomNear = itemView.findViewById(R.id.txtRoomNear);
            numberStar = itemView.findViewById(R.id.numberStar);
            cardView = itemView.findViewById(R.id.cardViewNear);

        }
        void bind(Boarding_host room) {
            String imageUrl = room.getImg();
           Picasso.get().load(imageUrl).into(imgRoomNear);
            txtRoomNear.setText(room.getAddress());
            numberStar.setText(String.valueOf(room.getNumberOfStars()));
            idBoarding = room.getId();
        }
    }
}
