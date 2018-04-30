package com.example.tyler.happyhour;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
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
        //make sure doc exists and has start time field in which case it will have others
        if(!docSnap.exists()) {
            Log.e("Firestore", "Doc does not exist");
            return "";
        }
        //custom class onyl used to get Application context so string.xml is accessible from this class
        App myApp = new App();
        String toReturn = docSnap.getString(myApp.getContext().getString(R.string.dealDay));
        double startTime = docSnap.getDouble(myApp.getContext().getString(R.string.dealStartTime));
        double endTime = docSnap.getDouble(myApp.getContext().getString(R.string.dealEndTime));
        toReturn = toReturn.substring(0,1).toUpperCase() + toReturn.substring(1); //capitalize first letter
        toReturn += ": " + timeToString(startTime) + " to " + timeToString(endTime);
        toReturn += " - " + docSnap.getString(myApp.getContext().getString(R.string.dealDescription));
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
