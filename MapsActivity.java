package com.example.tyler.happyhour;

import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng backyard = new LatLng(32.797143, -117.254139);
        mMap.addMarker(new MarkerOptions().position(backyard).title("Marker at Backyard??"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(backyard));
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(backyard, zoomLevel));
        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MapsActivity.this);
        mMap.setInfoWindowAdapter(adapter);

        loadLocations();
    }

    public void loadLocations() {
        LatLng cabo = new LatLng(32.797842, -117.250785);
        Bar CaboCantina = new Bar("Cabo Cantina", cabo, "Mexican Themed Bar with good drink deals",
                "1050 Garnet Ave", "San Diego", "CA", "92109");
        CaboCantina.setHappyScore(9);

        Time startTime = new Time(4,30, true);
        Time endTime = new Time(8, 30, true);
        Deal monday2for1 = new Deal(startTime, endTime, 0);
        monday2for1.setDiscrption("2 for 1 drinks");
        CaboCantina.addMondayDeal(monday2for1);

        Marker CaboMarkerr = mMap.addMarker(new MarkerOptions()
                .position(CaboCantina.getLocation())
                .title("Cabo Cantina")
                .snippet(CaboCantina.mondayDeals.get(0).toString()));

        pushToFireStone(CaboCantina);
        queryFirebase();
    }

    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        Log.d("Button push", "Begining search");

        if (location != null || !location.equals("")) {
            Log.d("Button push", "Searching for " + location);
        }
    }

    public List<Bar> searchFor(String location, List<Bar> barList) {
        List<Bar> toReturn = new ArrayList<Bar>();

        for(Bar current : barList) {
            if(current.tags.contains(location)) {
                toReturn.add(current);
            }
        }

        return toReturn;
    }

    public void pushToFireStone(Bar toPush) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> docData = new HashMap<>();
        docData.put("name", "Los Angeles");
        docData.put("state", "CA");
        docData.put("country", "USA");
// Add a new document (asynchronously) in collection "cities" with id "LA"
        db.collection("cities").document("LA").set(docData);
    }
    public void queryFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("bars").document("Cabo Cantina");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Bar bar = documentSnapshot.toObject(Bar.class);
            }
        });
    }


}
