package com.example.bartek.skateapp;

import com.google.android.gms.maps.model.Marker;

/**
 * Klasa zawierająca element klasy marker, oraz id obiektu przechowującego dane o miejscu w bazie
 * danych
 */
public class MapMarker {

    /**
     * id miejsca w bazie danych
     */
    public long id;
    /**
     * Obiekt markera na mapie
     */
    public Marker marker;

    /**
     * konstruktor klasy
     * @param m - marker który zjanduje się na mapie
     * @param id - id obiektu z bazy danych
     */
    MapMarker(Marker m , long id){
        this.marker = m;
        this.id = id;
    }
}
