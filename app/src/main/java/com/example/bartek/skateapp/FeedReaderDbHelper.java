package com.example.bartek.skateapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Klasa rozszerzająca SQLiteOpenHelper, zawiera ona metody potrzebne do obsługi bazy danych.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FeedReader.db";

    /**
     * Kostruktor klasy
     * @param context obecny context aplikacji.
     */
    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** Metoda wywoływana gdy baza danych jest tworzona po raz pierwszy.
     * Tutaj tworzymy tabele i wypełniamy początkową zawartością bazę danych.
     * @param db Baza danych którą tworzymy.
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.getSqlCreateEntries());
    }

    /**
     * Wywoływana gdy baza danych musi zostać zaktualizowana.
     * @param db baza danych która jest aktualizowana.
     * @param oldVersion numer identyfikujacy stara wersje.
     * @param newVersion numer identyfikujacy nowa wersję.
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //db.execSQL(FeedReaderContract.getSqlDeleteEntries());
        //onCreate(db);
    }

    /**
     * Metoda pozwalająca nam dodać nowy wpis do bazy danych.
     * @param title Tutuł nowego miejsca.
     * @param description Opis nowego miejsca.
     * @param lat Pozycja geograficzna lat nowego miejsca.
     * @param lng Pozycja geograficzna lng nowego miejsca.
     * @return Metoda zwraca id nowo dodanego miejsca.
     */
    public long addPlace(String title, String description, double lat, double lng) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LAT, lat);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LNG, lng);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    /**
     * Metoda zwracająca nam miejsce z bazy danych.
     * @param id id miesca które metoda ma zwrócić.
     * @return zwraca miejsce w postaci obiektu Place.
     */
    public Place getPlace(int id){
        Place place = new Place();
        SQLiteDatabase db = getReadableDatabase();
        String projection[] = {FeedReaderContract.FeedEntry._ID, FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, FeedReaderContract.FeedEntry.COLUMN_NAME_LAT, FeedReaderContract.FeedEntry.COLUMN_NAME_LNG};
        String selection = FeedReaderContract.FeedEntry._ID + "=?";
        String selectionArgs[] = {"" + id};
        // How you want the results sorted in the resulting Cursor
        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );
        if(cursor != null){
            cursor.moveToFirst();
            place.setId(cursor.getInt(0));
            place.setTitle(cursor.getString(1));
            place.setDescription(cursor.getString(2));
            place.setLat(cursor.getDouble(3));
            place.setLng(cursor.getDouble(4));
        }
        cursor.close();
        return place;
    }

    /**
     * Metoda pozwalająca uzyskać nam wszystkie miejsca zapisane w bazie danych.
     * @return Wszystkie miejsca z bazy danych w postaci LinkedListy.
     */
    public LinkedList<Place> getAllPlaces(){
        SQLiteDatabase db = getReadableDatabase();
        LinkedList<Place> places = new LinkedList<>();
        String projection[] = {FeedReaderContract.FeedEntry._ID, FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, FeedReaderContract.FeedEntry.COLUMN_NAME_LAT, FeedReaderContract.FeedEntry.COLUMN_NAME_LNG};
        Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, null, null, null, null, null);
        while(cursor.moveToNext()){
            Place place = new Place();
            place.setId(cursor.getInt(0));
            place.setTitle(cursor.getString(1));
            place.setDescription(cursor.getString(2));
            place.setLat(cursor.getDouble(3));
            place.setLng(cursor.getDouble(4));
            places.add(place);
        }
        cursor.close();
        return places;
    }

    /**
     * Metoda pozwalająca usunąć miejsce pod podanym id.
     * @param id Id miejsca które chcemy usunąć.
     */
    public void removePlace(int id){
        SQLiteDatabase db = getWritableDatabase();
        String selection = FeedReaderContract.FeedEntry._ID + "=?";
        String[] selectionArgs = {"" + id};
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Metoda pozwalajaca nam zaktualizowac miejsce o podanym id w bazie danych.
     * @param id id miejsca ktore chcemy edytowac.
     * @param title nowy title.
     * @param description nowy description.
     * @param lat nowy lat.
     * @param lng nowy lng.
     */
    public void updatePlace(int id, String title, String description, double lat, double lng){
        SQLiteDatabase db = getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LAT, lat);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LNG, lng);

        // Which row to update, based on the title
        String selection = FeedReaderContract.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = {"" + id};
        db.update(FeedReaderContract.FeedEntry.TABLE_NAME, values, selection, selectionArgs);
    }
}

