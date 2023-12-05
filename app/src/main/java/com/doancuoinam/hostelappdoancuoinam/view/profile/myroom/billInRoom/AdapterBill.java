package com.doancuoinam.hostelappdoancuoinam.view.profile.myroom.billInRoom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.TotalBill;
import com.doancuoinam.hostelappdoancuoinam.R;

import java.util.List;


public class AdapterBill extends RecyclerView.Adapter<AdapterBill.BillViewHolder> {
    List<TotalBill> totalBills;

    public void setDataBill(List<TotalBill> totalBills) {
        this.totalBills = totalBills;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterBill.BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new AdapterBill.BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBill.BillViewHolder holder, int position) {
        TotalBill totalBill = totalBills.get(position);;
        holder.bind(totalBill);
    }

    @Override
    public int getItemCount() {
        return totalBills != null ? totalBills.size() : 0;
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {
        TextView tienDien,tienNuoc,tienKhac,thang,tongTien;
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            tienDien = itemView.findViewById(R.id.tienDien);
            tienNuoc = itemView.findViewById(R.id.tienNuoc);
            tienKhac = itemView.findViewById(R.id.tienKhac);
            thang = itemView.findViewById(R.id.thang);
            tongTien = itemView.findViewById(R.id.tongTien);
        }
        void bind(TotalBill totalBill) {
            tienDien.setText(String.valueOf(totalBill.getElectricBill()));
            tienNuoc.setText(String.valueOf(totalBill.getWaterBill()));
            tienKhac.setText(String.valueOf(totalBill.getCostsIncurred()));
            thang.setText(totalBill.getMonth());
            tongTien.setText(String.valueOf(totalBill.getSum()));
        }
    }
}
