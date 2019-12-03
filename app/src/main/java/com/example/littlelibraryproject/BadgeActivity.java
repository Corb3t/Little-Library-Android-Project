package com.example.littlelibraryproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


        String Username = textViewUser.getText ().toString ();
        String UserBadgeD = textViewD.getText().toString();
        String UserBadgeTD = textViewTD.getText ().toString ();
        String Date = editTextDate.getText ().toString ();
        String image =  imageViewBadge.getImageMatrix ().toShortString ();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.itemLogin) {
            Intent LoginIntent = new Intent(this, LoginActivity.class);
            startActivity(LoginIntent);

        } else if (item.getItemId() == R.id.itemMap) {
            Intent MapIntent = new Intent(this, MapsActivity.class);
            startActivity(MapIntent);
        } else if (item.getItemId() == R.id.itemUsers) {
            Intent UsersIntent = new Intent(this, ProfileCreationActivity.class);
            startActivity(UsersIntent);
        } else if (item.getItemId() == R.id.itemLibrary) {
            Intent LibraryIntent = new Intent(this, LibraryActivity.class);
            startActivity(LibraryIntent);
        } else if (item.getItemId() == R.id.itemAddLibrary) {
            Intent AddLibraryIntent = new Intent(this, AddLibraryActivity.class);
            startActivity(AddLibraryIntent);
        }
        else if (item.getItemId() == R.id.itemLogOut){

            /// Implement log out funcitonality here
        }
        return super.onOptionsItemSelected(item);
    }





}



