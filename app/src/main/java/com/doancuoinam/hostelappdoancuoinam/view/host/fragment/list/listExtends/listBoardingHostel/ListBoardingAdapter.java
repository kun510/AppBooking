package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listBoardingHostel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Boarding_host;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.host.addRoom.AddRoomByHostActivity;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListBoardingAdapter extends RecyclerView.Adapter<ListBoardingAdapter.ListBoardingViewHolder>{
    List<Boarding_host> boardingHostList;
    Context context;
    public void setDataBoardingHostel(Context context, List<Boarding_host> boardingHostList) {
        this.context = context;
        this.boardingHostList = boardingHostList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListBoardingAdapter.ListBoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemtest,parent,false);
        return new  ListBoardingAdapter.ListBoardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListBoardingAdapter.ListBoardingViewHolder holder, int position) {
        Boarding_host listandCoutRoom = boardingHostList.get(position);
        holder.onBind(listandCoutRoom);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = String.valueOf(holder.BoardingId);
                showItemDialog(listandCoutRoom,id);
                Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                holder.onClickImg.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return   boardingHostList != null ? boardingHostList.size() : 0;
    }

    public class ListBoardingViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageView;
        TextView Address,numberRoom,Status;
        RatingBar ratingBar;
        ImageView onClickImg;
        long BoardingId;
        public ListBoardingViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView = itemView.findViewById(R.id.imageShow);
            Address = itemView.findViewById(R.id.TextName);
            numberRoom = itemView.findViewById(R.id.textCreate);
            Status = itemView.findViewById(R.id.textStory);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            onClickImg = itemView.findViewById(R.id.imageSelect);
        }
        void onBind(Boarding_host boardingHost){
            Address.setText(boardingHost.getAddress());
            numberRoom.setText(String.valueOf(boardingHost.getNumberRoom()));
            Status.setText(String.valueOf(boardingHost.getStatus()));
            String img = boardingHost.getImg();
            Picasso.get().load(img).into(roundedImageView);
            ratingBar.setRating(Float.valueOf(boardingHost.getNumberOfStars()));
            BoardingId = boardingHost.getId();
        }
    }
    private void showItemDialog(Boarding_host listandCoutRoom,String idBoarding) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View customLayout = LayoutInflater.from(context).inflate(R.layout.custom_dialog_boarding, null);
        builder.setView(customLayout);

        TextView addressDialog = customLayout.findViewById(R.id.addressDialog);
        TextView AreaDialog = customLayout.findViewById(R.id.AreaDialog);
        ImageView imgBoardingDialog= customLayout.findViewById(R.id.imgBoardingDialog);
        TextView btnClose = customLayout.findViewById(R.id.btnClose);
        TextView btnAddBill = customLayout.findViewById(R.id.btnAddBill);

        addressDialog.setText(listandCoutRoom.getAddress());
        AreaDialog.setText(listandCoutRoom.getArea());
        String imgAvt = listandCoutRoom.getImg();
        Picasso.get().load(imgAvt).into(imgBoardingDialog);
        AlertDialog alertDialog = builder.create();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        if ("Waiting Confirm".equals(listandCoutRoom.getStatus())) {
            btnAddBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Hãy đợi duyệt", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            btnAddBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddRoomByHostActivity.class);
                    intent.putExtra("boardingId", idBoarding);
                    context.startActivity(intent);
                }
            });
        }

        alertDialog.show();
    }
}
