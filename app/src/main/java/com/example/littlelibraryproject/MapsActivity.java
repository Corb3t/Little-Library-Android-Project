package com.example.littlelibraryproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
//    private ArrayList<String> mLibraryLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        //get all Firebase data
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Libraries");
        final Map<String, Library> Libraries = new HashMap<String, Library>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Library l = postSnapshot.getValue(Library.class);
                    Libraries.put(l.libraryName, l);
                }

                Toast.makeText(MapsActivity.this, Libraries.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(Libraries.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //pull little library info from database
//        myRef.orderByChild(""); add child event listener

        //display closest little libraries in maps

        // Add a marker in Ann Arbor and move the camera
        LatLng annarbor = new LatLng(42.28, -83.74);
        mMap.addMarker(new MarkerOptions().position(annarbor).title("Marker in Ann Arbor"));
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(annarbor));
        float zoomLevel = 15.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(annarbor, zoomLevel));
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

    @Override
    public void onClick(View view) {


    }

    public HashMap<String, Library> getLibrary(DatabaseReference ref){
        final HashMap<String, Library> Libraries = new HashMap<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Library l = postSnapshot.getValue(Library.class);
                    Libraries.put(l.libraryName, l);
                    Log.w("MapsActivity", Libraries.toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return Libraries;
    }
}
