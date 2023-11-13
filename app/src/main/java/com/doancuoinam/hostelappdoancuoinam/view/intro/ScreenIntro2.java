package com.doancuoinam.hostelappdoancuoinam.view.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.doancuoinam.hostelappdoancuoinam.R;

public class ScreenIntro2 extends AppCompatActivity {
    ImageView intro1,intro2,intro3;
    Button btn_intro;
    private GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_intro2);
        gestureDetector = new GestureDetector(this, new GestureListener());
        View[] views = UtilsIntro.AnhXa(this);
        UtilsIntro utils = new UtilsIntro();
        utils.ListenClick(views, this);
        btn_intro = findViewById(R.id.btn_intro);
        intro1 = findViewById(R.id.intro1);

        btn_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenIntro2.this, ScreenIntro3.class);
                startActivity(intent);
                finish();

            }
        });
        View rootView = findViewById(android.R.id.content);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        startActivity(new Intent(ScreenIntro2.this, ScreenIntro3.class));
                    }
                    result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}