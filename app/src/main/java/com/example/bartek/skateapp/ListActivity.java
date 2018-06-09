package com.example.bartek.skateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.LinkedList;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    LinkedList<Place> places = new LinkedList<>();
    FeedReaderDbHelper db = new FeedReaderDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = findViewById(R.id.lista);
        places = db.getAllPlaces();

        PlaceAdapter adapter = new PlaceAdapter(this, 0, places);
        listView.setAdapter(adapter);

    }
}
