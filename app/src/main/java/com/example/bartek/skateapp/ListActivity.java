package com.example.bartek.skateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.LinkedList;

/**
 * Klasa rozszerzająca AppCompatActivity.
 * Zawiera wygląd, oraz listView wyświetlający liste miejsc w bazie danych.
 */
public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private LinkedList<Place> places = new LinkedList<>();
    private FeedReaderDbHelper db = new FeedReaderDbHelper(this);
    private PlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = findViewById(R.id.lista);
        places = db.getAllPlaces();
        adapter = new PlaceAdapter(this, 0, places);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        places = db.getAllPlaces();
        adapter.updateData(places);
    }
}
