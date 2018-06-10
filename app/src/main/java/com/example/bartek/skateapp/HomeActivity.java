package com.example.bartek.skateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Klasa rozszerzająca AppCompatActivity, będąca aktywnością, która wyświetla się po WelcomeScreenie.
 * Zawiera wygląd, oraz 2 przyciski przechodzące do kolejnych aktywnosci.
 */
public class HomeActivity extends AppCompatActivity {

    private Button mapBtn;
    private Button dataBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mapBtn = findViewById(R.id.mapBtn);
        dataBtn = findViewById(R.id.dataBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(HomeActivity.this , MapActivity.class);
                startActivity(mapIntent);
            }
        });
        dataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dataIntent = new Intent(HomeActivity.this , ListActivity.class);
                startActivity(dataIntent);
            }
        });
    }
}