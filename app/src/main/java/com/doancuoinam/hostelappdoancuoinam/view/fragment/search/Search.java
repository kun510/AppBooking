package com.doancuoinam.hostelappdoancuoinam.view.fragment.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.room.roomListSearch.RoomListSearch;

public class Search extends Fragment {
    Button btn_select;
    EditText edtArea,edtType,edtGuests,edtPrice;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        btn_select = view.findViewById(R.id.btn_select);
        edtArea = view.findViewById(R.id.area);
        edtType = view.findViewById(R.id.type);
        edtGuests = view.findViewById(R.id.guests);
        edtPrice = view.findViewById(R.id.price);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoomListSearch.class);
                String area = edtArea.getText().toString();
                String type = edtType.getText().toString();
                String guests = edtGuests.getText().toString();
                String price = edtPrice.getText().toString();
                intent.putExtra("area", area);
                intent.putExtra("type", type);
                intent.putExtra("guests", guests);
                intent.putExtra("price", price);
                startActivity(intent);
            }
        });
        return view;
    }
}