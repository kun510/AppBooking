package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listUserRent;

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
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.host.bill.AddBillActivity;
import com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listRoom.ListRoomAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListUserRentAdapter extends RecyclerView.Adapter<ListUserRentAdapter.ViewHolder> {
    List<Rent> rentList;
    private Context context;
    public void setDataRentList(Context context,List<Rent> rent){
        this.context = context;
        this.rentList = rent;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListUserRentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_rent, parent, false);
        return new ListUserRentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListUserRentAdapter.ViewHolder holder, int position) {
        Rent rent = rentList.get(position);
        holder.onBind(rent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showItemDialog(rent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rentList != null ? rentList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idRent,nameUserRent,priceRoomRent,numberPeopleInRent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idRent = itemView.findViewById(R.id.idRent);
            nameUserRent = itemView.findViewById(R.id.nameUserRent);
            priceRoomRent = itemView.findViewById(R.id.priceRoomRent);
            numberPeopleInRent = itemView.findViewById(R.id.numberPeopleInRent);
        }
        void onBind(Rent rent){
            idRent.setText(String.valueOf(rent.getId()));
            nameUserRent.setText(rent.getUser().getName());
            priceRoomRent.setText(rent.getRoom().getPrice());
            numberPeopleInRent.setText(String.valueOf(rent.getPeopleInRoom()));
        }
    }
    private void showItemDialog(Rent rent) {
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
                context.startActivity(intent);
            }
        });
        alertDialog.show();
    }
}
