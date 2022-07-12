package com.example.stockviewer69.Controller.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stockviewer69.R;

public class WebviewActivity extends AppCompatActivity {
    WebView theWebPage;
    ImageView ivBack;
    TextView tvUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //intent.getStringExtra("stockShortName")
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.primaryDark));
        }
        tvUrl=findViewById(R.id.tvUrl);
        ivBack=findViewById(R.id.ivBackBtnWebView);
        theWebPage = findViewById(R.id.webView);
        theWebPage.setWebViewClient(new WebViewClient());
        WebSettings webSettings=theWebPage.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
       // theWebPage.getSettings().setJavaScriptEnabled(true);
       // theWebPage.getSettings().setPluginState(PluginState.ON);
        //setContentView(theWebPage);
        theWebPage.loadUrl(getIntent().getStringExtra("url"));
        tvUrl.setText(getIntent().getStringExtra("source"));
        bind();

    }
    public void bind(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (theWebPage.canGoBack()) {
                    theWebPage.goBack();
                } else {
                    finish();
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (theWebPage.canGoBack()) {
                        theWebPage.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}