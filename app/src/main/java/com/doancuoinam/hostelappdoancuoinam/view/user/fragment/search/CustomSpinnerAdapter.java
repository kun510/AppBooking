package com.doancuoinam.hostelappdoancuoinam.view.user.fragment.search;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.core.content.ContextCompat;


import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private int textColorResource;

    public CustomSpinnerAdapter(Context context, int resource, List<String> objects, int textColorResource) {
        super(context, resource, objects);
        this.textColorResource = textColorResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView text = view.findViewById(android.R.id.text1);
        text.setTextColor(ContextCompat.getColor(getContext(), textColorResource));
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView text = view.findViewById(android.R.id.text1);
        text.setTextColor(ContextCompat.getColor(getContext(), textColorResource));
        return view;
    }
}
