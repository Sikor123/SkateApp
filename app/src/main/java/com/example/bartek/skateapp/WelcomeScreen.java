package com.example.bartek.skateapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Klasa wyświetla ekran startowy, ktory pokazuje się przez 5,5 sekundy
 */
public class WelcomeScreen extends AppCompatActivity {

    private ImageView napis;
    private TextView tv;

    private static int SPLASH_TIME_OUT = 5500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        tv = findViewById(R.id.tv);
        napis = findViewById(R.id.iv);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.my_transition);
        Animation anim2 = AnimationUtils.loadAnimation(this,R.anim.my_transition2);
        napis.startAnimation(anim);
        tv.startAnimation(anim2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(WelcomeScreen.this , HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
