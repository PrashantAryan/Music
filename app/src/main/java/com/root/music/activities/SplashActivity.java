package com.root.music.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.root.music.R;
import com.root.music.util.UtilClass;

/**
 * Created by Ashutosh on 20/7/17.
 */
public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UtilClass.intentClass(MainActivity.class, SplashActivity.this);
            }
        }, SPLASH_TIME_OUT);
    }

}
