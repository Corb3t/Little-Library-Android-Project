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
    private Context mContext; //context variable, for use later on
    private StorageReference mStorageRef; //storage to link to firebase, need to import mstorage
    private ValueEventListener userListener;

    private BottomNavigationView mProfileNav;


    TextView textViewPhotoSubmissions, textViewFaveorites, textViewFirstfaveaddress,
            textViewFirstfave,textViewSecondfave, textViewSecondfaveaddress, textViewusername, textViewFavegenres;
    ImageView imageViewlibuno, imageViewSecondfave, imageViewFavetwo, imageViewHomePhotoone, imageViewPhototwo, imageViewProfilephoto;
    Button buttonEditProfile, buttonShowprofile;
    DatabaseReference reff; //either use myRef or reff?
//}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//get references to widgets

        mAuth = FirebaseAuth.getInstance();

        buttonEditProfile = findViewById(R.id.buttonEditProfile);
        imageViewProfilephoto = findViewById(R.id.imageViewProfilephoto);
        textViewPhotoSubmissions = findViewById(R.id.textViewPhotoSubmissions);
        //textViewFaveorites = findViewById(R.id.textViewFaveorites);
        textViewFirstfaveaddress = findViewById(R.id.textViewFirstfaveaddress);
        textViewFirstfave = findViewById(R.id.textViewFirstfave);
        imageViewlibuno = findViewById(R.id.imageViewlibuno);
        textViewSecondfave = findViewById(R.id.textViewSecondfave);
        textViewSecondfaveaddress = findViewById(R.id.textViewSecondfaveaddress);
        imageViewSecondfave = findViewById(R.id.imageViewSecondfave);
        //imageViewFavetwo = findViewById(R.id.imageViewFavetwo);
        imageViewHomePhotoone = findViewById(R.id.imageViewHomePhotoone);
        //imageViewPhototwo = findViewById(R.id.imageViewPhototwo);
        textViewFavegenres = findViewById(R.id.textViewFavegenres);
        textViewusername = findViewById(R.id.textViewusername);

        mProfileNav = findViewById(R.id.profile_nav);

        mProfileNav.setOnNavigationItemSelectedListener(this);
        mProfileNav.getMenu().findItem(R.id.navProfile).setChecked(true);

        buttonEditProfile.setOnClickListener(this);
        //buttonShowprofile.setOnClickListener(this);

        //get the intent and its data
        Intent intent = getIntent();
        String name = intent.getStringExtra("username");
        String favGenre = intent.getStringExtra("favGenre");
        String favLibrary = intent.getStringExtra("favLibrary");

        //display data on widgets
        //useless comment


        //setting the listener
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users"); //link to firebase

        myRef.orderByChild("name").equalTo("Joe").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User u = dataSnapshot.getValue(User.class);
                textViewusername.setText(u.name);
                textViewFavegenres.setText(u.favGenre);
                textViewFirstfave.setText(u.favLibrary);
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

/*    public class User (@NonNull DataSnapshot dataSnapshot){
        public String favGenre;
        public String favLibrary;
        public String name;

        public User (String favGenre, String favLibrary, String name){

        }
        String favGenre = dataSnapshot.child("favGenre").getValue().toString();
        String favLibrary = dataSnapshot.child("favLibrary").getValue().toString();
        String name = dataSnapshot.child("name").getValue().toString();
        //               String username = dataSnapshot.child("username").getValue().toString();

        textViewFirstfave.getText(favLibrary);
        textViewFavegenres.getText(favGenre);
        textViewusername.getText(name);

    }*/

    // Write a message to the database
    //Link to Listener here, then print out information




    //Button to Edit Profile when clicked
    @Override
    public void onClick(View view) {

        if (view == buttonEditProfile) {
            Intent editProfileIntent = new Intent(this, EditProfileActivity.class);
            startActivity(editProfileIntent);
            //} else if (view == buttonShowprofile) { //for some reason if I don't type View view, my 'view' remains red.

        }/* reff = FirebaseDatabase.getInstance().getReference().child("User").child("1");

            reff.addValueEventListener(new ValueEventListener() {

                //when we click "button show profile" we ned to display all of the current information on the profile
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //how should I label my strings? should they resemble to code in
                    //pull information from user profile
                    String favGenre = dataSnapshot.child("favGenre").getValue().toString();
                    String favLibrary = dataSnapshot.child("favLibrary").getValue().toString();
                    String name = dataSnapshot.child("name").getValue().toString();
                    //               String username = dataSnapshot.child("username").getValue().toString();

                    textViewFirstfave.setText(favLibrary);
                    textViewFavegenres.setText(favGenre);
                    textViewusername.setText(name);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } */
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
}

    //Code from Paul's example amended. How can this work for photo submissions, profile images and images of the library?
    //Display profile image
/*    @Override
    public void onBindViewHolder(@NonNull) {
        holder.textView.setText(User.get(position).name); //from Paul's code, but i don't have a holder?
        final File localFile = User.get(position).profilePic;
        StorageReference profileRef = mStorageRef.child(User.get(position).photoTitle);

        profileRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        try{
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.fromFile(localFile));
                            holder.imageView.setImageBitmap(bitmap);
                        }
                        catch(IOException e){

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });

    }*/






