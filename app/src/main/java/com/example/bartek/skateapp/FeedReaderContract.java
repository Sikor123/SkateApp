package com.example.bartek.skateapp;

import android.provider.BaseColumns;

/**
 * Klasa definiuąca strukturę bazy danych oraz polecenia SQLowe wymagane do jej utworzenia.
 */
public class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /**
     * Wewnętrzna klasa definiująca kolumny bazy danych.
     */
    public static class FeedEntry implements BaseColumns {
        /**
         * Nazwa tabeli.
         */
        public static final String TABLE_NAME = "Place";
        /**
         * Nazwa kolumny TITLE.
         */
        public static final String COLUMN_NAME_TITLE = "title";
        /**
         * Nazwa kolumny DESCRIPTION.
         */
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        /**
         * Nazwa kolumny LAT.
         */
        public static final String COLUMN_NAME_LAT = "lat";
        /**
         * Nazwa kolumny LNG.
         */
        public static final String COLUMN_NAME_LNG = "lng";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FeedEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    FeedEntry.COLUMN_NAME_LAT + " REAL," +
                    FeedEntry.COLUMN_NAME_LNG + " REAL )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    /**
     * Metoda zwracająca SQL_CREATE_ENTRIES
     * @return SQL_CREATE_ENTRIES
     */
    public static String getSqlCreateEntries() {
        return SQL_CREATE_ENTRIES;
    }
    /**
     * Metoda zwracająca SQL_DELETE_ENTRIES
     * @return SQL_DELETE_ENTRIES
     */
    public static String getSqlDeleteEntries() {
        return SQL_DELETE_ENTRIES;
    }
}

