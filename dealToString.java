package com.example.tyler.happyhour;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        Double objTime = time;

        try {
            Date date = new SimpleDateFormat("hhmm").parse(String.format("%04d", objTime.intValue()));
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            String toReturn = sdf.format(date);
            if (toReturn.charAt(0) == '0') {
                toReturn = toReturn.substring(1);
            }

            if(time >= 1200 && time < 2400) {
                return toReturn + " PM";
            }
            return toReturn + " AM";
        }

        catch (ParseException e) {
            Log.e("Error", "Parse error with " + Double.toString(time));
            e.printStackTrace();
        }
        return "Problem parsing time";

    }
}
