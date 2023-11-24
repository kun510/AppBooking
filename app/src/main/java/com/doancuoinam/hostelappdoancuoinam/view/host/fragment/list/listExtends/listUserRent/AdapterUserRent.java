package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listUserRent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Rent;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.host.addBill.AddBillActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUserRent extends RecyclerView.Adapter<AdapterUserRent.ViewHolder>{
    List<Rent> rentList;
    private Context context;
    public void setDataRentList(Context context,List<Rent> rent){
        this.context = context;
        this.rentList = rent;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AdapterUserRent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user_rent_bety, parent, false);
        return new AdapterUserRent.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUserRent.ViewHolder holder, int position) {
        Rent rent = rentList.get(position);
        holder.onBind(rent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imageSelect.setVisibility(View.VISIBLE);
                String id = String.valueOf(holder.RentId) ;
                String name = holder.nameRent;
                String idRoom = String.valueOf(holder.idRoomRent);
                showItemDialog(rent,id,name,idRoom);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rentList != null ? rentList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView UserRent,IdRoomRent,tienphon,numberPeople;
        ShapeableImageView avtUser;
        ImageView imageSelect;
        long RentId;
        String nameRent;
        long idRoomRent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            UserRent = itemView.findViewById(R.id.UserRent);
            IdRoomRent = itemView.findViewById(R.id.IdRoomRent);
            tienphon = itemView.findViewById(R.id.tienphon);
            numberPeople = itemView.findViewById(R.id.numberPeople);
            avtUser = itemView.findViewById(R.id.avtUser);
            imageSelect = itemView.findViewById(R.id.imageSelect);
        }
        void onBind(Rent rent){
            IdRoomRent.setText(String.valueOf(rent.getRoom().getId()));
            UserRent.setText(rent.getUser().getName());
            tienphon.setText(rent.getRoom().getPrice());
            numberPeople.setText(String.valueOf(rent.getPeopleInRoom()));
            String avt = rent.getUser().getImg();
            Picasso.get().load(avt).into(avtUser);
            RentId = rent.getId();
            nameRent = rent.getUser().getName();
            idRoomRent = rent.getRoom().getId();
        }
    }
    private void showItemDialog(Rent rent,String idRent,String nameRent,String idRoomRent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View customLayout = LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout, null);
        builder.setView(customLayout);

        TextView nameUser = customLayout.findViewById(R.id.NameUserDialog);
        TextView phoneDialog = customLayout.findViewById(R.id.phoneDialog);
        TextView NumberRoom = customLayout.findViewById(R.id.NumberRoom);
        ImageView imgAvtDialog= customLayout.findViewById(R.id.imgAvtDialog);
        TextView btnClose = customLayout.findViewById(R.id.btnClose);
        TextView btnAddBill = customLayout.findViewById(R.id.btnAddBill);
        nameUser.setText(rent.getUser().getName());
        phoneDialog.setText(rent.getUser().getPhone());
        NumberRoom.setText(String.valueOf(rent.getRoom().getId()));

        String imgAvt = rent.getUser().getImg();
        Picasso.get().load(imgAvt).into(imgAvtDialog);
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
                Intent intent = new Intent(context, AddBillActivity.class);
                intent.putExtra("idRent",idRent);
                intent.putExtra("nameRent",nameRent);
                intent.putExtra("idRoomRent",idRoomRent);
                context.startActivity(intent);
            }
        });
        alertDialog.show();
    }
}
