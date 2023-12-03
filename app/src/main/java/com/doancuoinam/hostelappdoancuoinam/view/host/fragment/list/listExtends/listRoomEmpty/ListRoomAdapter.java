package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listRoomEmpty;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Rent;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.host.addBill.AddBillActivity;
import com.doancuoinam.hostelappdoancuoinam.view.host.addRoom.AddListImgByRoom;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListRoomAdapter extends RecyclerView.Adapter<ListRoomAdapter.ListRoomViewHolder> {
    List<Room> listRoom;
    Context context;
    public void setRooms(List<Room> rooms,Context context) {
        this.listRoom = rooms;
        this.context = context;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showItemDialog(room);
            }
        });
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
    private void showItemDialog(Room room) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View customLayout = LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout_room, null);
        builder.setView(customLayout);

        TextView numberRoom = customLayout.findViewById(R.id.NameUserDialog);
        TextView areaRoom = customLayout.findViewById(R.id.phoneDialog);
        TextView status = customLayout.findViewById(R.id.NumberRoom);
        ImageView imgAvtRoom= customLayout.findViewById(R.id.imgAvtDialog);
        TextView btnClose = customLayout.findViewById(R.id.btnClose);
        TextView btnAddBill = customLayout.findViewById(R.id.btnAddBill);

        String idRoom = String.valueOf(room.getId());
        String idHost = String.valueOf(room.getUser().getId());
        String areaRoomGet = String.valueOf(room.getBoardingHostel().getArea());
        String statusRoom = String.valueOf(room.getStatus());
        numberRoom.setText(idRoom);
        areaRoom.setText(areaRoomGet);
        status.setText(statusRoom);

        String imgAvt = room.getImg();
        Picasso.get().load(imgAvt).into(imgAvtRoom);
        AlertDialog alertDialog = builder.create();
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddListImgByRoom.class);
                intent.putExtra("idRoom",idRoom);
                intent.putExtra("idHost",idHost);
                context.startActivity(intent);
            }
        });
        alertDialog.show();
    }
}
