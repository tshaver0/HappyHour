package com.example.tyler.happyhour;

/**
 * Created by tyler on 12/9/2017.
 */

public class Time {
    int hour;
    boolean isPM;
    int minutes;

    Time (int passedHour, int passedMinutes, boolean isPMPassed) {
        hour = passedHour;
        minutes = passedMinutes;
        isPM = isPMPassed;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public boolean isPM() {
        return isPM;
    }

    public void setPm(boolean pm) {
        this.isPM = pm;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
    public String toString() {
        if(isPM) {
            return hour + ":" + minutes + " PM";
        }
        return hour + ":" + minutes + " AM";
    }
}
