package com.example.littlelibraryproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.google.firebase.storage.StorageReference;


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


        //mAuth = FirebaseAuth.getInstance();
        //String email = mAuth.getCurrentUser().getEmail();
        String email = "jdblackhurst@aol.com"; //uncomment out once we create the log in page

        buttonEditProfile = findViewById(R.id.buttonEditProfile);
        imageViewProfilephoto = findViewById(R.id.imageViewProfilephoto);
        textViewPhotoSubmissions = findViewById(R.id.textViewPhotoSubmissions);
        textViewFirstfaveaddress = findViewById(R.id.textViewFirstfaveaddress);
        textViewlongitude = findViewById(R.id.textViewlongitude);
        textViewFirstfave = findViewById(R.id.textViewFirstfave);
        imageViewlibuno = findViewById(R.id.imageViewlibuno);
        textViewSecondfave = findViewById(R.id.textViewSecondfave);
        textViewSecondfaveaddress = findViewById(R.id.textViewSecondfaveaddress);
        imageViewSecondfave = findViewById(R.id.imageViewSecondfave);
        textViewFaveorites = findViewById(R.id.textViewFaveorites);
        textViewFavegenres = findViewById(R.id.textViewFavegenres);
        textViewusername = findViewById(R.id.textViewusername);

        //mProfileNav = findViewById(R.id.profile_nav);

        //mProfileNav.setOnNavigationItemSelectedListener(this);
        //mProfileNav.getMenu().findItem(R.id.navProfile).setChecked(true);

        buttonEditProfile.setOnClickListener(this);

        Intent intent = getIntent();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("Users");

        myRef.orderByChild("username").equalTo(email).
                //equalTo("Joe").
                        addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        User u = dataSnapshot.getValue(User.class);
                        textViewusername.setText(u.name);
                        textViewFavegenres.setText(u.favGenre);
                        textViewFaveorites.setText(u.favLibrary);

                        myRef.orderByChild("library").equalTo(u.favLibrary).
                                addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                            Library l = dataSnapshot.getValue(Library.class);
                                            textViewFirstfave.setText(l.libraryName);
                                    }

                                    @Override
                                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        Library l = dataSnapshot.getValue(Library.class);
                                        textViewFirstfaveaddress.setText(Double.toString(l.latitude));
                                        textViewlongitude.setText(Double.toString(l.longitude));

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
        public boolean onCreateOptionsMenu (Menu menu){

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.mainmenu, menu);

            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){

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
        public boolean onNavigationItemSelected (@NonNull MenuItem menuItem){

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
    public void onClick(View view){

    }

    }

//deleted text views imageViewFavetwo, imageViewHomePhotoone, imageViewPhototwo,
//imageViewFavetwo = findViewById(R.id.imageViewFavetwo);
//imageViewHomePhotoone = findViewById(R.id.imageViewHomePhotoone);
//imageViewPhototwo = findViewById(R.id.imageViewPhototwo);
//textViewFaveorites = findViewById(R.id.textViewFaveorites);








