package com.doancuoiky.hostelkun.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.doancuoiky.hostelkun.R;
import com.doancuoiky.hostelkun.view.room.RoomList;


public class search extends Fragment {
    Button btn_select;
    EditText edtArea,edtType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        btn_select = view.findViewById(R.id.btn_select);
        edtArea = view.findViewById(R.id.area);
        edtType = view.findViewById(R.id.type);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoomList.class);
                String area = edtArea.getText().toString();
                String type = edtType.getText().toString();
                intent.putExtra("area", area);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });
        return view;
    }
}