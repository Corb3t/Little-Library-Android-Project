package com.example.littlelibraryproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private Context mContext;
    private StorageReference mStorageRef;
    private ValueEventListener userListener;

    private BottomNavigationView mProfileNav;


    TextView textViewPhotoSubmissions, textViewFaveorites, textViewFirstfaveaddress,
            textViewFirstfave, textViewlongitude, textViewSecondfave, textViewSecondfaveaddress, textViewusername, textViewFavegenres;
    ImageView imageViewlibuno, imageViewSecondfave, imageViewProfilephoto;
    Button buttonEditProfile;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();

        buttonEditProfile = findViewById(R.id.buttonEditProfile);
        imageViewProfilephoto = findViewById(R.id.imageViewProfilephoto);
        textViewPhotoSubmissions = findViewById(R.id.textViewPhotoSubmissions);
        textViewlongitude = findViewById(R.id.textViewlongitude);
        textViewFirstfaveaddress = findViewById(R.id.textViewFirstfaveaddress);
        imageViewlibuno = findViewById(R.id.imageViewlibuno);
        textViewFaveorites = findViewById(R.id.textViewFaveorites);
        textViewFavegenres = findViewById(R.id.textViewFavegenres);
        textViewusername = findViewById(R.id.textViewusername);

        mProfileNav = findViewById(R.id.profile_nav);

        mProfileNav.setOnNavigationItemSelectedListener(this);
        mProfileNav.getMenu().findItem(R.id.navProfile).setChecked(true);

        buttonEditProfile.setOnClickListener(this);

        Intent intent = getIntent();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("Users");
        final DatabaseReference myRef2 = database.getReference().child("Libraries");

        myRef.orderByChild("username").equalTo(email).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        final User u = dataSnapshot.getValue(User.class);
                        textViewusername.setText(u.name);
                        String completeGen = "Your favorite genre: " + u.favGenre;
                        textViewFavegenres.setText(completeGen);

                        String completeLib = "Your favorite library: " + u.favLibrary;
                        textViewFaveorites.setText(completeLib);

                        myRef2.orderByChild("libraryName").equalTo(u.favLibrary).
                                addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        //Toast.makeText(ProfileActivity.this, u.favLibrary, Toast.LENGTH_SHORT).show();
                                        Library l = dataSnapshot.getValue(Library.class);
                                        String textAddress = "GPS coordinates:" ;
                                        textViewFirstfaveaddress.setText(textAddress);
                                        String textCoordinates = "( " + Double.toString(l.latitude) + " , "
                                                + Double.toString(l.longitude) + " )";
                                        textViewlongitude.setText(textCoordinates);
                                    }

                                    @Override
                                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


                                    }

                                    @Override
                                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }


                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    //Menu
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
        } else if (item.getItemId() == R.id.itemLogOut) {

            FirebaseAuth.getInstance().signOut();
            Intent mainIntent = new Intent(this, LoginActivity.class);
            startActivity(mainIntent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.navMap) {
            Intent mapIntent = new Intent(ProfileActivity.this, MapsActivity.class);
            startActivity(mapIntent);
            return true;

        } else if (menuItem.getItemId() == R.id.navLibrary) {
            Intent libraryIntent = new Intent(ProfileActivity.this, LibraryActivity.class);
            startActivity(libraryIntent);
            return true;


        } else if (menuItem.getItemId() == R.id.navProfile) {
            Intent profileIntent = new Intent(ProfileActivity.this, ProfileActivity.class);
            startActivity(profileIntent);
            return true;
        }


        return false;
    }

    @Override
    public void onClick(View view) {

        if (view == buttonEditProfile) {

            Intent editProfileIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(editProfileIntent);

        }

    }


//deleted text views imageViewFavetwo, imageViewHomePhotoone, imageViewPhototwo,
//imageViewFavetwo = findViewById(R.id.imageViewFavetwo);
//imageViewHomePhotoone = findViewById(R.id.imageViewHomePhotoone);
//imageViewPhototwo = findViewById(R.id.imageViewPhototwo);
//textViewFaveorites = findViewById(R.id.textViewFaveorites);
}







