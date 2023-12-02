package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listUserRent;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Rent;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.ResponseAll;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.view.host.addBill.AddBillActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        TextView cancelRent = customLayout.findViewById(R.id.cancelRent);
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
        cancelRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc chắn đã hết thuê chưa?");
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long idRent = rent.getId();
                        long idRoom =Long.valueOf(idRoomRent);
                        EndRent(idRoom,idRent);
                        dialog.dismiss();
                        alertDialog.dismiss();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
    private void EndRent(long idRoom, long idRent){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseAll> call = apiService.endRent(idRoom,idRent);
        call.enqueue(new Callback<ResponseAll>() {
            @Override
            public void onResponse(Call<ResponseAll> call, Response<ResponseAll> response) {
                if (response.isSuccessful()) {
                    ResponseAll result = response.body();
                    Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Huỷ Thuê Thất bại", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseAll> call, Throwable t) {
                Toast.makeText(context, "Lỗi HTTP", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
