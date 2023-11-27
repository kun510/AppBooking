package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.home;

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
        holder.content.setText(itemObject.getType());
        String a = itemObject.getImg();
        Picasso.get().load(a).into(holder.imgRoomcontent);
        Picasso.get().load(a).into(holder.imgRoom);
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
                Toast.makeText(view.getContext(), "P"+position , Toast.LENGTH_SHORT).show();
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
        TextView title,content;
        Button btnF ;
        ImageView check,imgRoomcontent,imgRoom;
        LinearLayout liner;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            foldingCell = itemView.findViewById(R.id.folding_cell);
            content = itemView.findViewById(R.id.addRoom);
            btnF = itemView.findViewById(R.id.btn_select);
            check = itemView.findViewById(R.id.check);
            imgRoomcontent = itemView.findViewById(R.id.imgRoomcontent);
            imgRoom = itemView.findViewById(R.id.imgRoom);
            liner = itemView.findViewById(R.id.liner);
        }
    }
}
