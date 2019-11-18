package com.example.littlelibraryproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.littlelibraryproject.R;

public class MainActivity extends AppCompatActivity
    {

    //variables
    private boolean mLocationPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
