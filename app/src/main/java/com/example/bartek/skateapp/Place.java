package com.example.bartek.skateapp;

/**
 * Klasa przechowująca informację o miejscu.
 */
public class Place {
    private int id;
    private String title;
    private String description;
    private double lat;
    private double lng;
    /**
     * Konstruktor klasy.
     */
    public Place(){}

    /**
     * Zwraca id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Ustawia id.
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Zwraca title.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Ustawia title.
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Zwraca description.
     * @return description
     */
    public String getDescription() {
        return description;
    }
    /**
     * Ustawia description.
     * @param description description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * zwraca lat.
     * @return lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * Ustawia lat.
     * @param lat lat.
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Zwraca lng
     * @return lng
     */
    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
