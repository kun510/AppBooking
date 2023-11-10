package com.doancuoinam.hostelappdoancuoinam.view.intro;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.doancuoinam.hostelappdoancuoinam.R;

public class UtilsIntro {
    public static View[] AnhXa(Activity activity) {
        ImageView intro1 = activity.findViewById(R.id.clickleft);
        ImageView intro2 = activity.findViewById(R.id.clickcenter);
        ImageView intro3 = activity.findViewById(R.id.clickright);
        Button btn_intro = activity.findViewById(R.id.btn_intro);

        View[] views = {intro1, intro2, intro3, btn_intro};
        return views;
    }

    public void ListenClick(View[] views, Activity activity){
        ImageView intro1 = (ImageView) views[0];
        ImageView intro2 = (ImageView) views[1];
        ImageView intro3 = (ImageView) views[2];

        intro1.setOnClickListener(v -> {
            Intent intro11 = new Intent(activity, ScreenIntro2.class);
            activity.startActivity(intro11);
            activity.overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        });
        intro2.setOnClickListener(v -> {
            Intent intro21 = new Intent(activity, ScreenIntro3.class);
            activity.startActivity(intro21);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        intro3.setOnClickListener(v -> {
            Intent intro31 = new Intent(activity, ScreenIntro4.class);
            activity.startActivity(intro31);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}
