package com.hgm.haritagenelmudurlugu.SiteActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hgm.haritagenelmudurlugu.MainActivity;
import com.hgm.haritagenelmudurlugu.R;

public class ViewActivity extends AppCompatActivity {
    ProgressBar progressBar;
    WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        tanimlamalar();


        Intent intent=getIntent();
        String gelenVeri=intent.getStringExtra("key");

        try {
            webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + gelenVeri);
            webview.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        } catch (Exception error) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(R.drawable.problem)
                    .setTitle("Bir Hata Alındı")
                    .setPositiveButton("Tekrar dene", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ViewActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }).show();
        }





}

    private void tanimlamalar() {
        webview = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressBar);
        webview.getSettings().setJavaScriptEnabled(true);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setMax(100);
    }
}