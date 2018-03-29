package com.example.tyler.happyhour;


/**
 * Created by tyler on 12/9/2017.
 */

public class Deal {
    Time startTime;
    Time endTime;
    String discrption;

    /* int to store the day of week deal takes place on
    indexed by day of week, i.e Monday = 0 ... Sunday = 6
     */
    int dayOfWeek;

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getDiscrption() {
        return discrption;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    Deal() {}

    Deal(Time passedStart, Time passedEnd, int passedDay) {
        startTime = passedStart;
        endTime = passedEnd;
        dayOfWeek = passedDay;
    }

    void setDiscrption(String passedDiscrption) {
        discrption = passedDiscrption;
    }
     public String toString(){
         return startTime.toString() + " to " + endTime.toString() + ", " + discrption;
     }



}
