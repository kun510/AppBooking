package com.doancuoinam.hostelappdoancuoinam.chatSupport;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.widget.Toolbar;

import com.doancuoinam.hostelappdoancuoinam.R;


public class ChatSupportActivity extends AppCompatActivity {
    WebView webView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_support);
        webView = findViewById(R.id.webView);
        toolbar = findViewById(R.id.toolbar);
        String  a = "Hổ Trợ";
        setToolbar(toolbar,a);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://console.dialogflow.com/api-client/demo/embedded/89dceffc-8ef4-40a0-bad5-8d6e9840b313");
    }
    private void setToolbar(Toolbar toolbar, String name){
        setSupportActionBar(toolbar);
        SpannableString spannableString = new SpannableString(name);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(spannableString);
        toolbar.setNavigationIcon(R.drawable.backdetail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
