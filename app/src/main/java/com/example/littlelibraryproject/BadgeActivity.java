package com.example.littlelibraryproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


// Future steps (outside of class scope)

public class BadgeActivity extends AppCompatActivity {

    ImageView imageViewUserPhoto, imageViewBadge;
    TextView textViewUser, textViewTD, textViewD;
    EditText editTextDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_badge );

        imageViewBadge = findViewById ( R.id.imageViewBadge );
        textViewUser = findViewById ( R.id.textViewUser );
        textViewD = findViewById ( R.id.textViewD );
        textViewTD = findViewById ( R.id.textViewTD );
        editTextDate = findViewById ( R.id.editTextDate );

    }




    FirebaseDatabase database = FirebaseDatabase.getInstance ();
    DatabaseReference myRef = database.getReference ( "Users" );

    String Username = textViewUser.getText ().toString ();
    String UserBadgeD = textViewD.getText ().toString ();
    String UserBadgeTD = textViewTD.getText ().toString ();
    String Date = editTextDate.getText ().toString ();
    String image = imageViewBadge.getImageMatrix ().toShortString ();



}



