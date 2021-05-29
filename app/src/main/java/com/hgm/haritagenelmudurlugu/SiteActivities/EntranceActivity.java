package com.hgm.haritagenelmudurlugu.SiteActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.hgm.haritagenelmudurlugu.Admin.LoginActivity;
import com.hgm.haritagenelmudurlugu.MainActivity;


import com.hgm.haritagenelmudurlugu.R;
import com.hgm.haritagenelmudurlugu.SiteActivities.Utility.NetworkChangeListener;

public class EntranceActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3000;
    private static int SPLASH_SHINE = 1900;
    Animation top_animation, bottom_animation;
    ImageView imageViewLogo, imageShine;
    TextView textViewLogo;

// 2 ayrı animasyon sırayla çalışır


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_entrance);
        imageViewLogo = findViewById(R.id.imageViewLogo);
        imageShine = findViewById(R.id.imageShine);
        textViewLogo = findViewById(R.id.textViewLogo);






        top_animation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom_animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        imageViewLogo.setAnimation(top_animation);
        textViewLogo.setAnimation(bottom_animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                shineStart();
            }
        },SPLASH_SHINE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(EntranceActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);


    }

    private void shineStart() {
        Animation animation = new TranslateAnimation(0, imageViewLogo.getWidth() + imageShine.getWidth(), 0, 0);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        imageShine.startAnimation(animation);

    }


}