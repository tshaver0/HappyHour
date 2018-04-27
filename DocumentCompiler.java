package com.example.tyler.happyhour;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;

/**
 * Created by tyler on 4/15/2018.
 */

public class DocumentCompiler {
    private HashMap<String, Marker> mMarkers;
    private GoogleMap mMap;

    public DocumentCompiler(GoogleMap passedMap) {

        this.mMarkers = new HashMap<String, Marker>();
        mMap = passedMap;
    }

    public void addBar(DocumentSnapshot documentSnapshot){
        String name = documentSnapshot.get("name").toString();
        if(mMarkers.containsKey(name)){
            return;
        }

        Marker toAdd = new Marker(new LatLng(documentSnapshot.getGeoPoint("location").getLatitude(), documentSnapshot.getGeoPoint("location").getLongitude()),
                documentSnapshot.get("name").toString(), documentSnapshot.get("description").toString());
        mMap.addMarker(new MarkerOptions()
                .position(toAdd.getPosition())
                .title(toAdd.getTitle())
                .snippet(toAdd.getSnippet()));
        Log.d("Markers", "Made new bar marker");
        mMarkers.put(name, toAdd); //TODO might cause issues as name might not be unique
    }

    public void addDeal(final DocumentSnapshot documentSnapshot){
        DocumentReference barRef = (DocumentReference) documentSnapshot.get("bar");
        final Task<DocumentSnapshot> barRefTask = barRef.get();
        barRefTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot docSnap = barRefTask.getResult();
                if(mMarkers.containsKey(docSnap.getString("name"))) {
                    Marker toEdit  = mMarkers.get(docSnap.getString("name"));
                    //TODO edit marker to include new deal and replace in HashMap
                    dealToString parse = new dealToString();
                    toEdit.addToSnippet( System.getProperty("line.separator") + parse.dealToString(documentSnapshot));
                    Log.d("Markers", "Adding string to marker: " + toEdit.getSnippet());
                    mMarkers.remove(docSnap.getString("name"));
                    mMarkers.put(docSnap.getString("name"), toEdit);
                    mMap.addMarker(new MarkerOptions()
                            .position(toEdit.getPosition())
                            .title(toEdit.getTitle())
                            .snippet(toEdit.getSnippet()));
                    Log.d("Markers", "Added deal to existing Marker");
                    return;
                }
                Log.e("Markers", "Error getting " + docSnap.contains("name"));

                Marker toAdd = new Marker(new LatLng(docSnap.getGeoPoint("location").getLatitude(), docSnap.getGeoPoint("location").getLongitude()),
                        docSnap.get("name").toString(), docSnap.get("description").toString());
                Log.d("Markers", "Added deal to new Marker");
                dealToString parse = new dealToString();
                toAdd.addToSnippet(System.getProperty("line.separator") + parse.dealToString(documentSnapshot));
                mMap.addMarker(new MarkerOptions()
                        .position(toAdd.getPosition())
                        .title(toAdd.getTitle())
                        .snippet(toAdd.getSnippet()));
                mMarkers.put(docSnap.getString("name"), toAdd);
                Log.d("Markers", "Deal has " + mMarkers.size());
            }
        });
    }
}
