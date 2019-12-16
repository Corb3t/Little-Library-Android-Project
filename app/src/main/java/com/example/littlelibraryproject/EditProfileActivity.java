package com.example.littlelibraryproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextName, editTextGenre, editTextFavLibrary;
    Button buttonSubmit, buttonUploadProfilePhoto;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTextName = findViewById(R.id.editTextName);
        editTextGenre = findViewById(R.id.editTextGenre);
        editTextFavLibrary = findViewById(R.id.editTextFavLibrary);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonUploadProfilePhoto = findViewById(R.id.buttonUploadProfilePhoto);

        buttonSubmit.setOnClickListener(this);
        buttonUploadProfilePhoto.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
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
            Intent UsersIntent = new Intent(this, ProfileActivity.class);
            startActivity(UsersIntent);
/*        } else if (item.getItemId() == R.id.itemLibrary) {
            Intent LibraryIntent = new Intent(this, LibraryActivity.class);
            startActivity(LibraryIntent);*/ //doesn't make sense to keep navigation to libraries outside of the map
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

    @Override
    public void onClick(View view) {

        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");

        if (view == buttonSubmit){

            final String editUserName = user.getEmail();

            myRef.orderByChild("username").equalTo(editUserName).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    String editKey = dataSnapshot.getKey();
                    User foundUser = dataSnapshot.getValue(User.class);

                    final String editName = editTextName.getText().toString();
                    final String editGenre = editTextGenre.getText().toString();
                    final String editFavLibrary = editTextFavLibrary.getText().toString();

                    User editUser = new User(editUserName, editName, editGenre, editFavLibrary);
                    myRef.child(editKey).setValue(editUser);

                    Toast.makeText(EditProfileActivity.this, "Profile Updated!", Toast.LENGTH_SHORT).show();




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

        else if (view == buttonUploadProfilePhoto){

            Intent photoIntent = new Intent(this, AddPhoto2activity.class);
            startActivity(photoIntent);

        }

    }
}
