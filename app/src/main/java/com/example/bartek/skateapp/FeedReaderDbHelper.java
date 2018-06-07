package com.example.bartek.skateapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.getSqlCreateEntries());
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //db.execSQL(FeedReaderContract.getSqlDeleteEntries());
        //onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public void addPlace(String title, String description, double lat, double lng) {
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
    }
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
    public void removePlace(int id){
        SQLiteDatabase db = getWritableDatabase();
        String selection = FeedReaderContract.FeedEntry._ID + "=?";
        String[] selectionArgs = {"" + id};
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }
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

