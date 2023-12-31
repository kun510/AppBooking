package com.doancuoinam.hostelappdoancuoinam.view.user.profile.roomFavourite;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.RoomFavourite;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.toast.ToastInterface;
import com.doancuoinam.hostelappdoancuoinam.view.user.room.listImgInRoom.OverviewRoom;
import com.squareup.picasso.Picasso;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class RoomFavouriteAdapter extends RecyclerView.Adapter<RoomFavouriteAdapter.RoomViewHolder> {
    private List<RoomFavourite> rooms;
    Context context;
    public void setRoomsFavouriteList(List<RoomFavourite> rooms,Context context) {
        this.rooms = rooms;
        this.context = context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoomFavouriteAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemfavourite, parent, false);
        return new RoomFavouriteAdapter.RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomFavouriteAdapter.RoomViewHolder holder, int position) {
        RoomFavourite room = rooms.get(position);
        holder.bind(room);
        holder.btn_selectRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getBindingAdapterPosition();
               // Toast.makeText(view.getContext(), ""+ clickedPosition, Toast.LENGTH_SHORT).show();
                String imageUrl = room.getRoom().getImg();
                String idRoom =String.valueOf(room.getRoom().getId());
                String addressRoom =room.getRoom().getBoardingHostel().getAddress();
                String area =room.getRoom().getBoardingHostel().getArea();
                String numberStar =room.getRoom().getNumberOfStars();
                String tienphong =room.getRoom().getPrice();
                String tiendien = String.valueOf(room.getRoom().getElectricBill());
                String tiennuoc = String.valueOf(room.getRoom().getWaterBill());;
                String songuoi =room.getRoom().getPeople();
                String SPhone = room.getRoom().getUser().getPhone();
                String idHost = String.valueOf(room.getRoom().getUser().getId());
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
                intent.putExtra("phoneHost",SPhone);
                view.getContext().startActivity(intent);
            }
        });
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.favourite.isSelected()){
                    holder.favourite.setSelected(false);

                }else {
                    holder.favourite.setSelected(true);
                    SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    long userID = sharedPreferences.getLong("userId", 0);
                    long idRoom = holder.idRoomFavourite;
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    Call<Boolean> call = apiService.deleteRoomFavourite(idRoom,userID);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                boolean deleteRoom = response.body();
                                if (deleteRoom) {
                                    AlertDialog.Builder alertThongBao = new AlertDialog.Builder(view.getContext());
                                    alertThongBao.setMessage(R.string.bothich);
                                    alertThongBao.setTitle(R.string.thongbao);
                                    alertThongBao.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(view.getContext(), RoomFavouriteActivity.class);
                                            view.getContext().startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    });
                                    alertThongBao.show();
                                } else {
                                    Toast.makeText(view.getContext(), "Không thể xóa phòng yêu thích", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(view.getContext(), "Lỗi từ server", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(view.getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "onFailureRoomFavourite: " + t.getMessage());
                        }
                    });
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
        ImageButton favourite;
        TextView addRoom,dateRoom;
        Button btn_selectRoom;
        long idRoomFavourite;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoom = itemView.findViewById(R.id.imgRoom);
            addRoom = itemView.findViewById(R.id.addrRoom);
            dateRoom = itemView.findViewById(R.id.dateRoom);
            favourite = itemView.findViewById(R.id.favourite);
            btn_selectRoom = itemView.findViewById(R.id.btn_selectRoom);
        }
        void bind(RoomFavourite room) {
            String imageUrl = room.getRoom().getImg();
            Date date = room.getDay();
            Instant instant = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                instant = date.toInstant();
            }
            LocalDateTime localDateTime = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            }
            String dateString = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                dateString = localDateTime.format(formatter);
            }

            Picasso.get().load(imageUrl).into(imgRoom);
            addRoom.setText(room.getRoom().getBoardingHostel().getAddress());
            dateRoom.setText(dateString);
            idRoomFavourite = room.getId();
        }
    }
}
