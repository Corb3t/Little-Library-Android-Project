package com.example.littlelibraryproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    private BottomNavigationView mProfileNav;
    private FrameLayout mProfileFrame;

    //Button buttonEditProfile;

    private MapFragment mapFragment;
    private LibraryFragment libraryFragment;
    private ProfileFragment profileFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        mProfileFrame = findViewById(R.id.profile_frame);
        mProfileNav = findViewById(R.id.profile_nav);

        //buttonEditProfile = findViewById(R.id.buttonEditProfile);

        mProfileNav.setOnNavigationItemSelectedListener(this);

        //buttonEditProfile.setOnClickListener(this);


        mapFragment = new MapFragment();
        libraryFragment = new LibraryFragment();
        profileFragment = new ProfileFragment();

        setFragment(profileFragment);
    }

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

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

    @Override
    public void onClick(View view) {

//        if (view == buttonEditProfile){
//
//            Intent editProfileIntent = new Intent(this, EditProfileActivity.class);
//            startActivity(editProfileIntent);
//        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.navMap) {

            setFragment(mapFragment);

            return true;


        } else if (menuItem.getItemId() == R.id.navLibrary) {

            setFragment(libraryFragment);

            return true;


        } else if (menuItem.getItemId() == R.id.navProfile) {

            setFragment(profileFragment);

            return true;

        }

        return false;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.profile_frame, fragment);
        fragmentTransaction.commit();
    }
}