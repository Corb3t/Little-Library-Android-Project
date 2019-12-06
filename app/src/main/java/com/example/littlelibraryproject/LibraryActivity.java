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

import com.google.firebase.auth.FirebaseAuth;

public class LibraryActivity extends AppCompatActivity {

    private Button button;
    Button buttonAddLibraryPhoto, buttonNavigateToLibrary;
    TextView textViewLibraryGenres, textViewLibraryAddress;
    ImageView imageViewLibraryPhoto;
    private FirebaseAuth mAuth;


///onCreate starts here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        buttonAddLibraryPhoto = findViewById(R.id.buttonAddLibrary);
        buttonNavigateToLibrary = findViewById(R.id.buttonNavigateToLibrary);

        textViewLibraryAddress = findViewById(R.id.textViewLibraryAddress);
        textViewLibraryGenres = findViewById(R.id.textViewLibraryGenres);

        imageViewLibraryPhoto = findViewById(R.id.imageViewLibraryPhoto);


        buttonAddLibraryPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openAddPhotoActivity();
            }
        });



        mAuth = FirebaseAuth.getInstance();
    }

///Not sure what this is or who put it there?
    public void openAddPhotoActivity() {
        Intent intent = new Intent(this, AddPhoto2activity.class);
        startActivity(intent);
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
            Intent mainIntent = new Intent(this, LoginActivity.class);
            startActivity(mainIntent);

        }
        return super.onOptionsItemSelected(item);
    }


}
