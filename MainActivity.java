package com.example.tyler.happyhour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button monday = (Button) findViewById(R.id.monday);
        Button tuesday = (Button) findViewById(R.id.tuesday);
        Button wednesday = (Button) findViewById(R.id.wednesday);
        Button thursday = (Button) findViewById(R.id.thursday);
        Button friday = (Button) findViewById(R.id.friday);
        Button saturday = (Button) findViewById(R.id.saturday);
        Button sunday = (Button) findViewById(R.id.sunday);

        monday.setOnClickListener(this);
        tuesday.setOnClickListener(this);
        wednesday.setOnClickListener(this);
        thursday.setOnClickListener(this);
        friday.setOnClickListener(this);
        saturday.setOnClickListener(this);
        sunday.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.monday:
                Intent myIntent = new Intent(this, MapsActivity.class);
                myIntent.putExtra("day", "monday"); //Optional parameters
                this.startActivity(myIntent);
        }

        switch (v.getId()) {
            case R.id.monday:
                Intent myIntent = new Intent(this, MapsActivity.class);
                myIntent.putExtra("day", "monday"); //Optional parameters
                this.startActivity(myIntent);
        }

        switch (v.getId()) {
            case R.id.tuesday:
                Intent myIntent = new Intent(this, MapsActivity.class);
                myIntent.putExtra("day", "tuesday"); //Optional parameters
                this.startActivity(myIntent);
        }

        switch (v.getId()) {
            case R.id.wednesday:
                Intent myIntent = new Intent(this, MapsActivity.class);
                myIntent.putExtra("day", "wednesday"); //Optional parameters
                this.startActivity(myIntent);
        }

        switch (v.getId()) {
            case R.id.thursday:
                Intent myIntent = new Intent(this, MapsActivity.class);
                myIntent.putExtra("day", "thursday"); //Optional parameters
                this.startActivity(myIntent);
        }

        switch (v.getId()) {
            case R.id.friday:
                Intent myIntent = new Intent(this, MapsActivity.class);
                myIntent.putExtra("day", "friday"); //Optional parameters
                this.startActivity(myIntent);
        }

        switch (v.getId()) {
            case R.id.saturday:
                Intent myIntent = new Intent(this, MapsActivity.class);
                myIntent.putExtra("day", "saturday"); //Optional parameters
                this.startActivity(myIntent);
        }

        switch (v.getId()) {
            case R.id.sunday:
                Intent myIntent = new Intent(this, MapsActivity.class);
                myIntent.putExtra("day", "sunday"); //Optional parameters
                this.startActivity(myIntent);
        }
    }
}
