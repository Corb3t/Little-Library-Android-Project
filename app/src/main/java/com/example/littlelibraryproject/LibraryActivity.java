package com.example.littlelibraryproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LibraryActivity extends AppCompatActivity {

    private Button button;
    Button buttonAddLibraryPhoto, buttonNavigateToLibrary, buttonTakePhoto;
    TextView textViewLibraryGenres, textViewLibraryAddress;
    ImageView imageViewLibraryPhoto;
    private FirebaseAuth mAuth;


    ///onCreate starts here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_library );


        buttonAddLibraryPhoto = findViewById ( R.id.buttonAddLibraryPhoto );
        buttonTakePhoto = findViewById ( R.id.buttonTakePhoto );
        buttonNavigateToLibrary = findViewById ( R.id.buttonNavigateToLibrary );

        textViewLibraryAddress = findViewById ( R.id.textViewLibraryAddress );
        textViewLibraryGenres = findViewById ( R.id.textViewLibraryGenres );

        imageViewLibraryPhoto = findViewById ( R.id.imageViewLibraryPhoto );


        mAuth = FirebaseAuth.getInstance ();

        buttonAddLibraryPhoto.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
              openAddLibraryPhoto();

            }
        } );


    }


    public void openAddLibraryPhoto() {
        Intent intent = new Intent ( this , AddPhoto2activity.class );
        startActivity ( intent );
    }







/// Menu items start here
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
            Intent UsersIntent = new Intent(this, ProfileActivity.class);
            startActivity(UsersIntent);
        } else if (item.getItemId() == R.id.itemLibrary) {
            Intent LibraryIntent = new Intent(this, LibraryActivity.class);
            startActivity(LibraryIntent);
        } else if (item.getItemId() == R.id.itemAddLibrary) {
            Intent AddLibraryIntent = new Intent(this, AddLibraryActivity.class);
            startActivity(AddLibraryIntent);
        }
        else if (item.getItemId() == R.id.itemLogOut){

            FirebaseAuth.getInstance().signOut();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) {
                Toast.makeText(this, "You have been logged out", Toast.LENGTH_LONG).show();

                Intent LoginIntent = new Intent(this, LoginActivity.class);
                startActivity(LoginIntent);
            } else {
                Toast.makeText(this, "Log out failed", Toast.LENGTH_SHORT).show();
            }


        }
        return super.onOptionsItemSelected(item);
    }


}
