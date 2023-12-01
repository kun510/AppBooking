package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.user.room.listImgInRoom.OverviewRoom;
import com.doancuoinam.hostelappdoancuoinam.view.user.room.roomList.RoomListByBoarding;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapterListHot extends RecyclerView.Adapter<HomeAdapterListHot.Viewholder> {
    List<Room> list;

    public void setDataAdapter(List<Room> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeAdapterListHot.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemfolding,parent,false);
        return new  HomeAdapterListHot.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapterListHot.Viewholder holder, int position) {
        Room itemObject = list.get(position);
        holder.onBind(itemObject);
        holder.idBoarding = itemObject.getBoardingHostel().getId();
        holder.nameBoarding = itemObject.getBoardingHostel().getAddress();
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.foldingCell.toggle(false);
            }
        });
        holder.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.foldingCell.toggle(false);
            }
        });
        holder.btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String imageUrl = itemObject.getImg();
              String idRoom =String.valueOf(itemObject.getId());
              String addressRoom =itemObject.getBoardingHostel().getAddress();
              String area =itemObject.getBoardingHostel().getArea();
              String numberStar =itemObject.getNumberOfStars();
              String tienphong =itemObject.getPrice();
              String tiendien = String.valueOf(itemObject.getElectricBill());
              String tiennuoc = String.valueOf(itemObject.getWaterBill());;
              String songuoi =itemObject.getPeople();
              String idHost = String.valueOf(itemObject.getUser().getId());
                Intent intent = new Intent(view.getContext(), OverviewRoom.class);
                intent.putExtra("selected_image_url", imageUrl);
                intent.putExtra("idRoom",idRoom);
                intent.putExtra("addressRoom",addressRoom);
                intent.putExtra("area",area);
                intent.putExtra("numberStar",numberStar);
                intent.putExtra("tienphong",tienphong);
                intent.putExtra("tiendienne",tiendien);
                intent.putExtra("tiennuocne",tiennuoc);
                intent.putExtra("songuoine",songuoi);
                intent.putExtra("idHost",idHost);
                view.getContext().startActivity(intent);
            }
        });
        holder.liner.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycer_one));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        FoldingCell foldingCell;
        TextView txtAdrr,numberStar,content,areaRoom,descriptionRoom,numberRoom,electricitybill,waterBill,numberPeople,priceRoom;
        Button btnF ;
        ImageView check,imgRoomcontent,imgRoom;
        LinearLayout liner;
        long idBoarding;
        String nameBoarding;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            foldingCell = itemView.findViewById(R.id.folding_cell);
            content = itemView.findViewById(R.id.addRoom);
            btnF = itemView.findViewById(R.id.btn_select);
            check = itemView.findViewById(R.id.check);
            imgRoomcontent = itemView.findViewById(R.id.imgRoomcontent);
            imgRoom = itemView.findViewById(R.id.imgRoom);
            liner = itemView.findViewById(R.id.liner);
            numberStar = itemView.findViewById(R.id.numberStar);
            txtAdrr = itemView.findViewById(R.id.txtAdrr);
            areaRoom = itemView.findViewById(R.id.areaRoom);
            descriptionRoom = itemView.findViewById(R.id.descriptionRoom);
            numberRoom = itemView.findViewById(R.id.numberRoom);
            electricitybill = itemView.findViewById(R.id.electricitybill);
            waterBill = itemView.findViewById(R.id.waterBill);
            numberPeople = itemView.findViewById(R.id.numberPeople);
            priceRoom = itemView.findViewById(R.id.priceRoom);
        }
        void onBind(Room room){
            content.setText(room.getType());
            String a = room.getImg();
            Picasso.get().load(a).into(imgRoomcontent);
            Picasso.get().load(a).into(imgRoom);
            txtAdrr.setText(room.getBoardingHostel().getAddress());
            numberStar.setText(room.getNumberOfStars());
            areaRoom.setText(room.getBoardingHostel().getArea());
            descriptionRoom.setText(room.getDescription());
            numberRoom.setText(room.getNumberRoom());
            electricitybill.setText(String.valueOf(room.getElectricBill()));
            waterBill.setText(String.valueOf(room.getWaterBill()));
            numberPeople.setText(room.getPeople());
            priceRoom.setText(room.getPrice());
        }
    }
}
