package com.example.tyler.happyhour;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

/**
 * Created by tyler on 11/24/2017.
 */

public class Bar {

    public int getHappyScore() {
        return happyScore;
    }

    public HashSet<String> getTags() {
        return tags;
    }

    public Address getAddress() {
        return address;
    }

    public ArrayList<Deal> getMondayDeals() {
        return mondayDeals;
    }

    public ArrayList<Deal> getTuesdayDeals() {
        return tuesdayDeals;
    }

    public ArrayList<Deal> getWednesdayDeals() {
        return wednesdayDeals;
    }

    public ArrayList<Deal> getThursdayDeals() {
        return thursdayDeals;
    }

    public ArrayList<Deal> getFridayDeals() {
        return fridayDeals;
    }

    public ArrayList<Deal> getSaturdayDeals() {
        return saturdayDeals;
    }

    public ArrayList<Deal> getSundayDeals() {
        return sundayDeals;
    }

    public String name;
    public String discrption;

    int happyScore; //int that scores our rank of the bar 1-10;
    HashSet<String> tags;

    Address address;

    public ArrayList<Deal> mondayDeals;
    public ArrayList<Deal> tuesdayDeals;
    public ArrayList<Deal> wednesdayDeals;
    public ArrayList<Deal> thursdayDeals;
    public ArrayList<Deal> fridayDeals;
    public ArrayList<Deal> saturdayDeals;
    public ArrayList<Deal> sundayDeals;

    public Bar() {}

    public Bar(String passedName, LatLng passedLoc, String passedDisc, String line1, String City, String State, String postalCode) {
        name = passedName;
        Locale myLocale = new Locale("English", "USA");
        address = new Address(myLocale);

        address.setLongitude(passedLoc.longitude);
        address.setLatitude(passedLoc.latitude);
        address.setAddressLine(0, line1);
        address.setLocality(City);
        address.setAdminArea(State);
        address.setPostalCode(postalCode);

        mondayDeals = new ArrayList<Deal>();
        tuesdayDeals = new ArrayList<Deal>();
        wednesdayDeals = new ArrayList<Deal>();
        thursdayDeals = new ArrayList<Deal>();
        fridayDeals = new ArrayList<Deal>();
        saturdayDeals = new ArrayList<Deal>();
        sundayDeals = new ArrayList<Deal>();

        tags = new HashSet<String>();

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLocation() {
        return new LatLng(address.getLatitude(), address.getLongitude());
    }

    public String getDiscrption() {
        return discrption;
    }

    public void setDiscrption(String discrption) {
        this.discrption = discrption;
    }
    public void setHappyScore(int passedScore) {
        happyScore = passedScore;
    }

    public void addMondayDeal(Deal passedDeal) {
        mondayDeals.add(passedDeal);
    }
    public void addTuesdayDeal(Deal passedDeal) {
        tuesdayDeals.add(passedDeal);
    }
    public void addWednesdayDeal(Deal passedDeal) {
        wednesdayDeals.add(passedDeal);
    }
    public void addThursdayDeal(Deal passedDeal) {
        thursdayDeals.add(passedDeal);
    }
    public void addFridayDeal(Deal passedDeal) {
        fridayDeals.add(passedDeal);
    }
    public void addSaturdayDeal(Deal passedDeal) {
        saturdayDeals.add(passedDeal);
    }
    public void addSundayDeal(Deal passedDeal) {
        sundayDeals.add(passedDeal);
    }
}
