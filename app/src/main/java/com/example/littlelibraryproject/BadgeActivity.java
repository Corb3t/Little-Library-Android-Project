package com.example.littlelibraryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class BadgeActivity extends AppCompatActivity {

    ImageView imageViewUserPhoto,imageViewBadge;
    TextView textViewUser,textViewTD,textViewD;
    EditText editTextDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);

           imageViewBadge = findViewById ( R.id.imageViewBadge );
           imageViewUserPhoto = findViewById ( R.id.imageViewUserPhoto );
           textViewUser = findViewById ( R.id.textViewUser );
           textViewD=findViewById ( R.id.textViewD );
           textViewTD = findViewById ( R.id.textViewTD );
           editTextDate= findViewById ( R.id.editTextDate ) ;


    }






}
