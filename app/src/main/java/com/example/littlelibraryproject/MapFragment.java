//package com.example.littlelibraryproject;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.FragmentActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Toast;
//
//import com.example.littlelibraryproject.R;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.database.core.Tag;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class MapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {
//
//    private GoogleMap mMap;
//    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
//
//    //hash map of Firebase library data
//    private ArrayList<Library> mLibraryLocations = new ArrayList<>();
//
//
//    public MapFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_map, container, false);
//
//        // trouble transferring
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_map);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onClick(View view) {
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        //get all Firebase data using a Hashmap
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Libraries");
//
//        final Map<String, Library> Libraries = new HashMap<String, Library>();
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Library l = postSnapshot.getValue(Library.class);
//                    Libraries.put(l.libraryName, l);
//                }
//
//                Toast.makeText(MapsActivity.this, Libraries.toString(), Toast.LENGTH_SHORT).show();
//                System.out.println(Libraries.toString());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        //display closest little libraries in maps
//        // Add a marker in Ann Arbor and move the camera
//        LatLng annarbor = new LatLng(42.28, -83.74);
//        mMap.addMarker(new MarkerOptions().position(annarbor).title("Marker in Ann Arbor").snippet("Address: 1234 Main St"));
//        mMap.setMyLocationEnabled(true);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(annarbor));
//        float zoomLevel = 15.0f; //This goes up to 21
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(annarbor, zoomLevel));
//    }
//}
