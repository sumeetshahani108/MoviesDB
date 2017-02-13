package com.example.user.moviesdb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "GoodDog.otf");
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setTypeface(typeface);
        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "Sofia-Regular.otf");
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        textView4.setTypeface(typeface1);

        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}
