package com.doancuoinam.hostelappdoancuoinam.view.room.listImgInRoom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ImgRoom;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class AdapterOverview extends RecyclerView.Adapter<AdapterOverview.ImgViewHolder> {
    private List<ImgRoom> imgRooms;

    public void AdapterOverviewList(List<ImgRoom> imgRooms) {
        this.imgRooms = imgRooms;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterOverview.ImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_img_room, parent, false);
        return new AdapterOverview.ImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOverview.ImgViewHolder holder, int position) {
        ImgRoom imgRoom = imgRooms.get(position);
        holder.bind(imgRoom);
    }


    @Override
    public int getItemCount() {
        return imgRooms != null ? imgRooms.size() : 0;
    }

    public class ImgViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1,imageView2,imageView3,imageView4,imageView5;
        public ImgViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.itemListImg1);
            imageView2 = itemView.findViewById(R.id.itemListImg2);
            imageView3 = itemView.findViewById(R.id.itemListImg3);
            imageView4 = itemView.findViewById(R.id.itemListImg4);
            imageView5 = itemView.findViewById(R.id.itemListImg5);

        }
        void bind(ImgRoom imgRoom) {
            String imageUrl1 = imgRoom.getImgUrls1();
            Picasso.get().load(imageUrl1).into(imageView1);
            String imageUrl2 = imgRoom.getImgUrls2();
            Picasso.get().load(imageUrl2).into(imageView2);
            String imageUrl3 = imgRoom.getImgUrls3();
            Picasso.get().load(imageUrl3).into(imageView3);
            String imageUrl4 = imgRoom.getImgUrls4();
            Picasso.get().load(imageUrl4).into(imageView4);
            String imageUrl5 = imgRoom.getImgUrls5();
            Picasso.get().load(imageUrl5).into(imageView5);
        }

    }
}
