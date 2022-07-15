package com.example.stockviewer69.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stockviewer69.R;
import com.example.stockviewer69.model.entity.NewsModel;
import com.example.stockviewer69.utils.Const;

public class WebviewActivity extends AppCompatActivity {
    WebView theWebPage;
    ImageView back;
    TextView url;

    public void starter(Context context, NewsModel.Article news) {
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra(Const.Key.URL, news.url);
        intent.putExtra(Const.Key.SOURCE, news.source.name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.primaryDark));
        }
        overridePendingTransition(R.anim.slide_in, R.anim.slide_nothin);
        url = findViewById(R.id.url);
        back = findViewById(R.id.back);
        theWebPage = findViewById(R.id.webView);
        theWebPage.setWebViewClient(new WebViewClient());
        WebSettings webSettings = theWebPage.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        theWebPage.loadUrl(getIntent().getStringExtra(Const.Key.URL));
        url.setText(getIntent().getStringExtra(Const.Key.SOURCE));
        bind();
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

    public void bind() {
        back.setOnClickListener(v -> {
            if (theWebPage.canGoBack()) {
                theWebPage.goBack();
            } else {
                finish();
            }
        });
    }
}