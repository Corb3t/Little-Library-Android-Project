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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    private ArrayList<Library> mLibraryLocations = new ArrayList<>();
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

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

        //get all Firebase data using a Hashmap
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

                //hash map data output
//                Toast.makeText(MapsActivity.this, Libraries.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(Libraries);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //pull little library info from database
//        myRef.orderByChild(""); add child event listener

        //display closest little libraries in maps

        // Add a marker in Ann Arbor and move the camera
        LatLng annarbor = new LatLng(42.30456, -83.7333699);
        LatLng annarbor2 = new LatLng(42.2794, -83.76651);
        LatLng annarbor3 = new LatLng(42.28024, -83.7563799);
        LatLng annarbor4 = new LatLng(42.2779682, -83.7591485);
        LatLng annarbor5 = new LatLng(42.27176, -83.7360999);
        LatLng annarbor6 = new LatLng(42.27521, -83.73191);
        LatLng annarbor7 = new LatLng(42.27497, -83.71884);
        mMap.addMarker(new MarkerOptions().position(annarbor).title("Brookside Book House").snippet("Address: 1234 Main St").icon(BitmapDescriptorFactory.fromResource(R.drawable.libraryicon)));
        mMap.addMarker(new MarkerOptions().position(annarbor2).title("Library #15019").snippet("Address: 865 Brookside Dr\n" +
                "Ann Arbor, MI 48105\n" + "Genres: Crime, Fantasy, Fiction").icon(BitmapDescriptorFactory.fromResource(R.drawable.libraryicon)));
        mMap.addMarker(new MarkerOptions().position(annarbor3).title("Library #428024").snippet("Address: 1234 Main St").icon(BitmapDescriptorFactory.fromResource(R.drawable.libraryicon)));
        mMap.addMarker(new MarkerOptions().position(annarbor4).title("Library #18656").snippet("Address: 1234 Main St").icon(BitmapDescriptorFactory.fromResource(R.drawable.libraryicon)));
        mMap.addMarker(new MarkerOptions().position(annarbor5).title("The Smith's Library").snippet("Address: 1234 Main St").icon(BitmapDescriptorFactory.fromResource(R.drawable.libraryicon)));
        mMap.addMarker(new MarkerOptions().position(annarbor6).title("Vertex Coffee Roasters").snippet("Address: 1234 Main St").icon(BitmapDescriptorFactory.fromResource(R.drawable.libraryicon)));
        mMap.addMarker(new MarkerOptions().position(annarbor7).title("Library #58910").snippet("Address: 1234 Main St").icon(BitmapDescriptorFactory.fromResource(R.drawable.libraryicon)));
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(annarbor));
        float zoomLevel = 13.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(annarbor, zoomLevel));


        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent I = new Intent(MapsActivity.this, LibraryActivity.class);
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
