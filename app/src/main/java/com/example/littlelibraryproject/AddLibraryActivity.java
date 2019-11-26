package com.example.littlelibraryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_library);

        FirebaseDatabase mDb = FirebaseDatabase.getInstance();

    }

    // Write a message to the database

//    DatabaseReference myRef = mDb.getReference("message");

}
