package com.hgm.haritagenelmudurlugu.SiteActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hgm.haritagenelmudurlugu.R;

public class yayinIlkeActivity extends AppCompatActivity {


    private float mScale = 1f;
    private ScaleGestureDetector mScaleDetector;
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yayin_ilke);

        gestureDetector = new GestureDetector(this, new GestureListener());
        mScaleDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scale = 1 - detector.getScaleFactor();
                float prevScale=mScale;
                mScale +=scale;

                if (mScale< 0.1f){
                    mScale=0.1f;
                }
                if (mScale>10f){
                    mScale=10f;
                }
                ScaleAnimation scaleAnimation=new ScaleAnimation(1f/prevScale,1f/mScale,1f/prevScale,1f/mScale,detector.getFocusX(),detector.getFocusY());
                scaleAnimation.setDuration(0);
                scaleAnimation.setFillAfter(true);
                ScrollView scrollView=(ScrollView) findViewById(R.id.scrollView);
                scrollView.startAnimation(scaleAnimation);
                return true;

            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        mScaleDetector.onTouchEvent(ev);
        gestureDetector.onTouchEvent(ev);
        return gestureDetector.onTouchEvent(ev);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }
}
