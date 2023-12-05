package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.search;

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
import android.widget.Spinner;

import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.user.room.roomListSearch.RoomListSearch;

import java.util.Arrays;

public class Search extends Fragment {
    Button btn_select;
    Spinner edtArea,edtType;
    EditText edtGuests,edtPrice;
    String listArea[]={"Area","Hai Chau", "Thanh Khe", "Lien Chieu", "Ngu Hanh Son", "Cam Le", "Hoa Trung", "Son Tra", "Hoa Vang"};
    String listType[]={"Type","Hostel", "Motel","Apartment"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Mapping(view);
        ChoseArea();
        ChoseType();

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoomListSearch.class);
                int position = edtArea.getSelectedItemPosition();
                String selectedItemArea = listArea[position];
                int pos = edtType.getSelectedItemPosition();
                String selectedItemType = listType[pos];
                String guests = edtGuests.getText().toString();
                String price = edtPrice.getText().toString();
                intent.putExtra("area", selectedItemArea);
                intent.putExtra("type", selectedItemType);
                intent.putExtra("guests", guests);
                intent.putExtra("price", price);
                startActivity(intent);
            }
        });
        return view;
    }

    private void Mapping(View view){
        btn_select = view.findViewById(R.id.btn_select);
        edtArea = view.findViewById(R.id.area);
        edtType = view.findViewById(R.id.type);
        edtGuests = view.findViewById(R.id.guests);
        edtPrice = view.findViewById(R.id.price);
    }
    private void ChoseArea() {
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(
                getContext(), android.R.layout.simple_spinner_item, Arrays.asList(listArea), R.color.mauchu
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtArea.setAdapter(adapter);
        edtArea.setPopupBackgroundResource(R.drawable.edit_text);
    }

    private void ChoseType() {
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(
                getContext(), android.R.layout.simple_spinner_item, Arrays.asList(listType), R.color.mauchu
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtType.setAdapter(adapter);
        edtType.setPopupBackgroundResource(R.drawable.edit_text);
    }
}