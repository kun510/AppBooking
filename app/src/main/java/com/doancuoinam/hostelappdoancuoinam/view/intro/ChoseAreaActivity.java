package com.doancuoinam.hostelappdoancuoinam.view.intro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.doancuoinam.hostelappdoancuoinam.BaseActivity;
import com.doancuoinam.hostelappdoancuoinam.R;
import com.doancuoinam.hostelappdoancuoinam.view.fragment.home.HomeFragment;

public class ChoseAreaActivity extends AppCompatActivity {
    String list[]={"Hai Chau", "Thanh Khe", "Lien Chieu", "Ngu Hanh Son", "Cam Le", "Hoa Trung", "Son Tra", "Hoa Vang"};
    Spinner spinner;
    Button btn_chose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_area);
        btn_chose = findViewById(R.id.btn_choseaa);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, list
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        btn_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = spinner.getSelectedItemPosition();
                String selectedItem = list[position];
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedItem", selectedItem);
                editor.apply();
                Intent intent = new Intent(ChoseAreaActivity.this, BaseActivity.class);
                startActivity(intent);
            }
        });

    }
    private class event implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedItem = list[position];
//            AlertDialog.Builder viewSpinner = new AlertDialog.Builder(ChoseAreaActivity.this);
//            viewSpinner.setMessage("Bạn đã chọn: " + selectedItem);
//            viewSpinner.setTitle(R.string.khuvuc);
//            viewSpinner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            viewSpinner.show();
            //parent.getItemAtPosition(position);
            Intent intent = new Intent(ChoseAreaActivity.this, HomeFragment.class);
            intent.putExtra("selectedItem", selectedItem);
            startActivity(intent);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}