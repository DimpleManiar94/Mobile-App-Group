package com.example.dimple.fastpath;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Lokcation extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    Button list, bill;
    LocationManager locationManager;
    String lattitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokcation);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        list = findViewById(R.id.mylist);
        bill = findViewById(R.id.mybill);

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Lokcation.this,"your current list", Toast.LENGTH_SHORT).show();
            }
        });

        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Lokcation.this,"your current bill", Toast.LENGTH_SHORT).show();
            }
        });


    }
}

