package com.example.tyler.happyhour;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tyler on 4/15/2018.
 */

public class Marker {
    LatLng position;
    String title;
    String snippet;

    public Marker(LatLng passedLatLng, String passedTitle, String passedSnippet){
        position = passedLatLng;
        title = passedTitle;
        snippet = passedSnippet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public void addToSnippet(String toAdd){
        snippet = snippet + toAdd;
    }
}
