package com.example.littlelibraryproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.littlelibraryproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private BottomNavigationView mMapsNav;
    private Map<String, Library> Libraries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mMapsNav = findViewById(R.id.profile_nav);

        mMapsNav.setOnNavigationItemSelectedListener(this);
        mMapsNav.getMenu().findItem(R.id.navMap).setChecked(true);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //get all Firebase data using a Hashmap
        getLibrary(new MyCallback() {
            @Override
            public void onCallBack(Map<String, Library> value) {
                Libraries = value;
                loadData(Libraries, mMap);
            }
        });


        //Navigate from the infowindow to the library page
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String name = marker.getTitle();
                Intent I = new Intent(MapsActivity.this, LibraryActivity.class);
                I.putExtra("WelcomeMessage", Libraries.get(name).welcomeMessage);
                I.putExtra("Name", name);
                startActivity(I);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //menu bar stuff
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.itemLogin) {
            Intent LoginIntent = new Intent(this, LoginActivity.class);
            startActivity(LoginIntent);

        } else if (item.getItemId() == R.id.itemMap) {
            Intent MapIntent = new Intent(this, MapsActivity.class);
            startActivity(MapIntent);
        } else if (item.getItemId() == R.id.itemUsers) {
            Intent UsersIntent = new Intent(this, EditProfileActivity.class);
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

    @Override
    public void onClick(View view) {

    }

    //Hashmap stuff for getting library firebase data
    public void getLibrary(final MyCallback callback){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Libraries");
        final HashMap<String, Library> Libraries = new HashMap<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Library l = postSnapshot.getValue(Library.class);
                    Libraries.put(l.libraryName, l);
                }
                callback.onCallBack(Libraries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //bottom bar navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.navMap) {
            Intent mapIntent = new Intent(MapsActivity.this, MapsActivity.class);
            startActivity(mapIntent);
            return true;

        } else if (menuItem.getItemId() == R.id.navLibrary) {
            Intent libraryIntent = new Intent(MapsActivity.this, LibraryActivity.class);
            startActivity(libraryIntent);
            return true;

        } else if (menuItem.getItemId() == R.id.navProfile) {
            Intent profileIntent = new Intent(MapsActivity.this, ProfileActivity.class);
            startActivity(profileIntent);
            return true;
        }

        return false;
    }

    //loading up hashmap library info in the map
    public void loadData(Map<String, Library> Libraries, GoogleMap mMap){
        for(Map.Entry element: Libraries.entrySet()){
            Library l = (Library) element.getValue();
            LatLng annarbor = new LatLng(l.latitude, l.longitude);
            mMap.addMarker(new MarkerOptions().position(annarbor).title(l.libraryName).snippet("Address: 1234 Main St").icon(BitmapDescriptorFactory.fromResource(R.drawable.libraryicon)));

        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(42.28024, -83.7563799)));
        float zoomLevel = 13.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.28024, -83.7563799), zoomLevel));
    }
}
