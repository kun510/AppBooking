package com.doancuoinam.hostelappdoancuoinam.view.room.roomList;

import static android.content.Context.MODE_PRIVATE;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
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
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Room;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.RoomFavourite;
import com.doancuoinam.hostelappdoancuoinam.Model.Response.Response;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient;
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService;
import com.doancuoinam.hostelappdoancuoinam.view.profile.roomFavourite.RoomFavouriteActivity;
import com.doancuoinam.hostelappdoancuoinam.view.profile.roomFavourite.RoomFavouriteAdapter;
import com.doancuoinam.hostelappdoancuoinam.view.room.AddListImgByRoom;
import com.doancuoinam.hostelappdoancuoinam.view.room.listImgInRoom.OverviewRoom;
import com.doancuoinam.hostelappdoancuoinam.view.room.roomListSearch.RoomAdapter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AdapterListRoomByBoarding extends RecyclerView.Adapter<AdapterListRoomByBoarding.RoomViewHolder>{
    private List<Room> rooms;
    public void setRoomsList(List<Room> rooms) {
        this.rooms = rooms;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AdapterListRoomByBoarding.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new AdapterListRoomByBoarding.RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListRoomByBoarding.RoomViewHolder holder, int position) {
        Room room = rooms.get(position);
        holder.bind(room);
        holder.btn_selectRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getBindingAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    Room selectedRoom = rooms.get(clickedPosition);
                    String imageUrl = selectedRoom.getImg();
                    String idRoom = String.valueOf(holder.idRoom);
                    String addressRoom = holder.AddressRoom;
                    String numberStar = holder.numberStar;
                    String area = holder.Area;
                    String idHost = String.valueOf(holder.idHost);
                    Intent intent = new Intent(v.getContext(), OverviewRoom.class);
                    intent.putExtra("selected_image_url", imageUrl);
                    intent.putExtra("idRoom",idRoom);
                    intent.putExtra("addressRoom",addressRoom);
                    intent.putExtra("area",area);
                    intent.putExtra("numberStar",numberStar);
                    intent.putExtra("idHost",idHost);
                    v.getContext().startActivity(intent);
                }
            }
        });
        SharedPreferences sharedPreferencesFavourite = holder.favourite.getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isFavourite = sharedPreferencesFavourite.getBoolean("isFavourite" + room.getId(), false);
        holder.favourite.setSelected(isFavourite);

        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedPreferencesFavourite.edit();
                boolean isFavourite = sharedPreferencesFavourite.getBoolean("isFavourite" + room.getId(), false);
                if (isFavourite) {
                    holder.favourite.setSelected(false);
                    editor.putBoolean("isFavourite" + room.getId(), false).apply();
                    Toast.makeText(view.getContext(), "bat", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertThongBao = new AlertDialog.Builder(view.getContext());
                    alertThongBao.setMessage(R.string.bancochac);
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
                    holder.favourite.setSelected(true);
                    editor.putBoolean("isFavourite" + room.getId(), true).apply();
                    Toast.makeText(view.getContext(), "tat", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    long userID = sharedPreferences.getLong("userId", 0);
                    long idRoom = holder.idRoom;
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    Call<Response> call = apiService.addRoomFavourite(idRoom, userID);
                    call.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if (response.isSuccessful()) {
                                Response result = response.body();
                                Toast.makeText(view.getContext(), "ok"+result, Toast.LENGTH_SHORT).show(); result.getMessage();
                            } else {
                                try {
                                    String errorBodyString = response.errorBody().string();
                                    Toast.makeText(view.getContext(), "Lỗi: " + errorBodyString, Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Toast.makeText(view.getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "onFailureListRoom: " + t.getMessage());
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
        TextView priceRoom,numberPeople,electricityBill,waterBill,numberRoom,descriptionRoom,areaRoom,addrRoom;
        Button btn_selectRoom;
        ImageButton favourite;
        long idRoom;
        long idRoomFavourite;
        String AddressRoom;
        String Area;
        String numberStar;
        long idHost;
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
            btn_selectRoom = itemView.findViewById(R.id.btn_selectRoom);
            favourite = itemView.findViewById(R.id.ImBFavourite);
        }
        void bind(Room room) {
            String imageUrl = room.getImg();
            Picasso.get().load(imageUrl).into(imgRoom);
            addrRoom.setText(room.getBoardingHostel().getAddress());
            electricityBill.setText(String.valueOf(room.getElectricBill()));
            waterBill.setText(String.valueOf(room.getWaterBill()));
            areaRoom.setText(room.getBoardingHostel().getArea());
            numberPeople.setText( "max " + room.getPeople() + " people");
            numberRoom.setText(room.getNumberRoom() + " Room");
            descriptionRoom.setText("$"+String.valueOf(room.getDescription()));
            priceRoom.setText("$"+String.valueOf(room.getPrice()));
            idRoom = room.getId();
            idHost = room.getUser().getId();
            Area = room.getBoardingHostel().getArea();
            AddressRoom = room.getBoardingHostel().getAddress();
            numberStar = room.getNumberOfStars();
            RoomFavourite roomFavourite = new RoomFavourite();
            idRoomFavourite = roomFavourite.getId();
        }
    }
}
