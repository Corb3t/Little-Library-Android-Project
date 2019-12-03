package com.example.littlelibraryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textViewName, textViewLikes, textViewFaveorites, textViewBadges, textViewPhotos;

    private FirebaseAuth mAuth;
    //private Object View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

            textViewName = findViewById(R.id.textViewName);
            textViewLikes = findViewById(R.id.textViewLikes);
            textViewFaveorites = findViewById(R.id.textViewFaveorites);
            textViewBadges = findViewById(R.id.textViewBadges);
            textViewPhotos = findViewById(R.id.textViewPhotos);

            mAuth = FirebaseAuth.getInstance();



        }

    @Override
    public void onClick(View view) {

    }
}






