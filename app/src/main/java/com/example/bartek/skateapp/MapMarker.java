package com.example.bartek.skateapp;

import com.google.android.gms.maps.model.Marker;

public class MapMarker {

    public long id;
    public Marker marker;


    MapMarker(Marker m , long id){
        this.marker = m;
        this.id = id;
    }
}
