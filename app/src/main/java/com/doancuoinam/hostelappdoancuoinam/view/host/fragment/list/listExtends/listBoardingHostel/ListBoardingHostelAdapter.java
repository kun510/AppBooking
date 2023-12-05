package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listBoardingHostel;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.Boarding_host;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.host.addRoom.AddRoomByHostActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListBoardingHostelAdapter extends RecyclerView.Adapter<ListBoardingHostelAdapter.ListBoardingViewHolder> {
    List<Boarding_host> boardingHostList;
    Context context;
    public void setDataBoardingHostel(Context context, List<Boarding_host> boardingHostList) {
        this.context = context;
        this.boardingHostList = boardingHostList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListBoardingHostelAdapter.ListBoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_boardinghostel,parent,false);
        return new  ListBoardingHostelAdapter.ListBoardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListBoardingHostelAdapter.ListBoardingViewHolder holder, int position) {
        Boarding_host listandCoutRoom = boardingHostList.get(position);
        holder.onBind(listandCoutRoom);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = String.valueOf(holder.BoardingId);
                showItemDialog(listandCoutRoom,id);
            }
        });
    }

    @Override
    public int getItemCount() {
      return   boardingHostList != null ? boardingHostList.size() : 0;
    }

    public class ListBoardingViewHolder extends RecyclerView.ViewHolder {
        TextView idBoarding,AddressBoarding,numberRoomIn,numberRoomEmpty;
        long BoardingId;
        public ListBoardingViewHolder(@NonNull View itemView) {
            super(itemView);
            idBoarding = itemView.findViewById(R.id.idBoarding);
            AddressBoarding = itemView.findViewById(R.id.AddressBoarding);
            numberRoomIn = itemView.findViewById(R.id.numberRoomIn);
            numberRoomEmpty = itemView.findViewById(R.id.numberRoomEmpty);
        }
        void onBind(Boarding_host boardingHost){
            idBoarding.setText(String.valueOf(boardingHost.getId()));
            AddressBoarding.setText(boardingHost.getAddress());
            numberRoomIn.setText(String.valueOf(boardingHost.getNumberRoom()));
            numberRoomEmpty.setText(String.valueOf(boardingHost.getStatus()));
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
