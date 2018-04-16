package com.example.tyler.happyhour;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by tyler on 4/15/2018.
 */

public class DocumentCompiler {
    private HashMap<String, Marker> markers;

    public DocumentCompiler() {
        this.markers = new HashMap<String, Marker>();
    }

    public void addBar(DocumentSnapshot documentSnapshot){
        String name = documentSnapshot.get("name").toString();
        if(markers.containsKey(name)){
            return;
        }
        Marker toAdd = new Marker(new LatLng(documentSnapshot.getGeoPoint("location").getLatitude(), documentSnapshot.getGeoPoint("location").getLongitude()),
                documentSnapshot.get("name").toString(), documentSnapshot.get("description").toString());
        markers.put(name, toAdd); //TODO might cause issues as name might not be unique
    }
    public void addDeal(DocumentSnapshot documentSnapshot){
        DocumentReference barRef = (DocumentReference) documentSnapshot.get("bar");
        final Task<DocumentSnapshot> barRefTask = barRef.get();
        barRefTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot docSnap = barRefTask.getResult();
                if(markers.containsKey(docSnap.getString("name"))) {
                    Marker toEdit  = markers.get(docSnap.getString("name"));
                    //TODO edit marker to include new deal and replace in HashMap
                }

                //TODO Add new marker

            }
        });
    }
}
