package com.example.codingenginer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class Navegar extends AppCompatActivity {

    EditText editText;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegar);

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        editText = (EditText) findViewById(R.id.editText);
        webView.loadUrl("https://www.google.com");
    }

    public void backButton(View view){
        if (webView.canGoBack()){
            webView.goBack();
        }
    }

    public void fwdButton(View view){
        if (webView.canGoForward()){
            webView.goForward();
        }
    }

    public void goButton(View view){
        String url = editText.getText().toString();
        webView.loadUrl(url);
    }

    public void reloadButton(View view){
        webView.reload();
    }
}
