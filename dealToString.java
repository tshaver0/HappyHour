package com.example.tyler.happyhour;

import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Created by tyler on 4/16/2018.
 */

public class dealToString {
    public String dealToString(DocumentSnapshot docSnap) {
        String toReturn = docSnap.getString("day of week");
        double startTime = docSnap.getDouble("start time");
        double endTime = docSnap.getDouble("end time");
        toReturn = toReturn.substring(0,1).toUpperCase() + toReturn.substring(1); //capitalize first letter
        toReturn += ": " + timeToString(startTime) + " to " + timeToString(endTime);
        toReturn += " - " + docSnap.getString("desc");
        return toReturn;
    }

    public String timeToString(double time){
        if(time > 1300 ){
            time =- 1200;
            String toReturn = Double.toString(time);
            String hours = toReturn.substring(0,2);
            String minutes = toReturn.substring(2,4);
            if(hours.charAt(0) == '0') {
                hours.substring(1);
            }
            hours += ':';
            return hours + minutes + "PM";
        }
        if (time < 1200) {
            String toReturn = Double.toString(time);
            String hours = toReturn.substring(0,2);
            String minutes = toReturn.substring(2,3);
            if(hours.charAt(0) == '0') {
                hours.substring(1);
            }
            hours += ':';
            return hours + minutes + "AM";
        }
        String toReturn = Double.toString(time);
        String hours = toReturn.substring(0,2);
        String minutes = toReturn.substring(2,4);
        hours += ':';
        return hours + minutes + "PM";
    }
}
