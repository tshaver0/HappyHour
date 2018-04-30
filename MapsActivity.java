package com.example.tyler.happyhour;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseFirestore mDB;
    DocumentCompiler mAddAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mDB = FirebaseFirestore.getInstance();
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
        mAddAll = new DocumentCompiler(mMap);
        loadLocations();
    }

    public void loadLocations() {
        Log.d("Firestore", "Searching for sunday");
        Query dayQuery = queryByDay("Sunday");
        addMarkersFromQuery(dayQuery);
    }

    public Query queryByDay(String passedDay) {
        CollectionReference queryByDay = mDB.collection(getString(R.string.dealCollection));
        passedDay.toLowerCase();
        Query query = queryByDay.whereEqualTo(getString(R.string.dealDay), passedDay);
        return query;
    }

    public Query queryByTime(int time) {
        CollectionReference queryByTime = mDB.collection(getString(R.string.dealCollection));
        Query query = queryByTime.whereGreaterThanOrEqualTo(getString(R.string.dealStartTime), time)
                .whereLessThan(getString(R.string.dealEndTime), time);
        return query;
    }

    public Query queryByTimeandDay(int time, String passedDay) {
        passedDay.toLowerCase();
        CollectionReference queryByTime = mDB.collection(getString(R.string.barCollection));
        Query query = queryByTime.whereGreaterThanOrEqualTo(getString(R.string.dealStartTime), time)
                .whereLessThan(getString(R.string.dealEndTime), time)
                .whereEqualTo(getString(R.string.dealDay), passedDay);
        return query;
    }

    public void addMarkersFromQuery(Query passedQuery) {
        Task<QuerySnapshot> taskQuery = passedQuery.get();
        taskQuery.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot document : task.getResult()) {
                        mAddAll.addDeal(document);
                        }
                    }
                 else {
                    Log.d("Firestore", "Error getting documents: " + task.getException());
                }
            }
        });
        Log.d("Ending", "Returned from addMarkersFromQuery");
    }


    //TODO change this to return query so it can be added with addFromQuery()
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

        withInDistance = mDB.collection(getString(R.string.barCollection))
                .whereGreaterThanOrEqualTo(getString(R.string.barLocation), lesserGeopoint)
                .whereLessThanOrEqualTo(getString(R.string.barLocation), greaterGeopoint)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Firestore", document.getId() + " => " + document.getData());
                                createMarker(document.getGeoPoint(getString(R.string.barLocation)).getLatitude(),
                                        document.getGeoPoint(getString(R.string.barLocation)).getLongitude(),
                                        document.get(getString(R.string.barName)).toString(),
                                        document.get(getString(R.string.barDescription)).toString());
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
