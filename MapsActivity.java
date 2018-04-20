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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseFirestore db;
    String dayFromButtonPush;
    DocumentCompiler addAll;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        db = FirebaseFirestore.getInstance();


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                dayFromButtonPush = null;
            } else {
                dayFromButtonPush = extras.getString("day");
            }
        } else {
            dayFromButtonPush = (String) savedInstanceState.getSerializable("day");
        }
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
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(backyard, zoomLevel));
        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MapsActivity.this);
        mMap.setInfoWindowAdapter(adapter);
        loadLocations();
    }

    public void loadLocations() {
        addAll = new DocumentCompiler();
        /*
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
        */

        //pushToFireStone(CaboCantina);
        //queryFirebase();

        //Task<QuerySnapshot> withinDist = getDocumentsNear(32.797842, -117.250785, 10); //near backyard
        Log.d("Firestore", "Searching for " + dayFromButtonPush);
        Query dayQuery = queryByDay(dayFromButtonPush);
        addMarkersFromQuery(dayQuery);
        //addStoredMarkers();
        //getDocumentsNear(35.0510224,-120.3578378, 10); // not near backyard
    }

    public Query queryByDay(String passedDay) {
        // Create a reference to the cities collection
        CollectionReference queryByDay = db.collection("deal");
        passedDay.toLowerCase();
        Query query = queryByDay.whereEqualTo("day of week", passedDay);
        return query;
    }

    public Query queryByTime(int time) {
        // Create a reference to the cities collection
        CollectionReference queryByTime = db.collection("deal");
        Query query = queryByTime.whereGreaterThanOrEqualTo("start time", time)
                .whereLessThan("end time", time);
        return query;
    }

    public Query queryByTimeandDay(int time, String passedDay) {
        // Create a reference to the cities collection
        passedDay.toLowerCase();
        CollectionReference queryByTime = db.collection("deal");
        Query query = queryByTime.whereGreaterThanOrEqualTo("start time", time)
                .whereLessThan("end time", time)
                .whereEqualTo("day of week", passedDay);
        return query;
    }

    public void addMarkersFromQuery(Query passedQuery) {
        Task<QuerySnapshot> taskQuery = passedQuery.get();
        taskQuery.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot document : task.getResult()) {
                        addAll.addDeal(document, mMap);
                        addStoredMarkers();
                        }
                    }
                 else {
                    Log.d("Firestore", "Error getting documents: " + task.getException());
                }
            }
        });
        Log.d("Ending", "Returned from addMarkersFromQuery");
    }

    public void addStoredMarkers() {
        Iterator it = addAll.getMarkers().entrySet().iterator();
        Log.d("Markers", "addAll has " + Integer.toString(addAll.getMarkers().size()));
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            com.example.tyler.happyhour.Marker add = (com.example.tyler.happyhour.Marker) pair.getValue();
            mMap.addMarker(new MarkerOptions()
                    .position(add.getPosition())
                    .title(add.getTitle())
                    .snippet(add.getSnippet()));
        }
    }


    public Task<QuerySnapshot> getDocumentsNear(double latitude, double longitude, double distance) {
        double lat = 0.0144927536231884;
        double lon = 0.0181818181818182;

        double lowerLat = latitude - (lat * distance);
        double lowerLon = longitude - (lon * distance);

        double greaterLat = latitude + (lat * distance);
        double greaterLon = longitude + (lon * distance);

        GeoPoint lesserGeopoint = new GeoPoint(lowerLat, lowerLon);
        GeoPoint greaterGeopoint = new GeoPoint(greaterLat, greaterLon);

        Task<QuerySnapshot> withInDistance = null;

        withInDistance = db.collection("bars")
                .whereGreaterThanOrEqualTo("location", lesserGeopoint)
                .whereLessThanOrEqualTo("location", greaterGeopoint)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Firestore", document.getId() + " => " + document.getData());
                                createMarker(document.getGeoPoint("location").getLatitude(),
                                        document.getGeoPoint("location").getLongitude(),
                                        document.get("name").toString(),
                                        document.get("description").toString());
                                Log.d("Firestore", document.getId() + " done");
                            }
                        } else {
                            Log.d("Firestore", "Error getting documents: " + task.getException());
                        }
                    }
                });
        return withInDistance;
    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet));
    }

}
