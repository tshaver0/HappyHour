package com.example.tyler.happyhour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button go = (Button) findViewById(R.id.go);
        go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent myIntent = new Intent(this, MapsActivity.class);
        myIntent.putExtra("day", "monday"); //Optional parameters
        Log.d("New Intent", "monday");
        this.startActivity(myIntent);
    }
}
