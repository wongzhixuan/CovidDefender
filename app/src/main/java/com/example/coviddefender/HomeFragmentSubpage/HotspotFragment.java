package com.example.coviddefender.HomeFragmentSubpage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.coviddefender.R;
import com.example.coviddefender.entity.Hotspot;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HotspotFragment extends Fragment {
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView tv_confirmed_number;
    TextView tv_recovered_number;
    TextView tv_death_number;
    Marker[] allMarkers;
    ArrayList<LatLng> positions = new ArrayList<LatLng>();
    Marker marker;
    GoogleMap googleMap;
    // Firebase Authentication
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    // Firestore
    private FirebaseFirestore firestore;
    private DocumentReference docRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotspot, container, false);

        // link widgets
        tv_confirmed_number = view.findViewById(R.id.tv_confirmed_number);
        tv_recovered_number = view.findViewById(R.id.tv_recovered_number);
        tv_death_number = view.findViewById(R.id.tv_death_number);

        // Assign variable
        supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.hotspot_map);
        // Initialize fused location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        // set Up Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // set up Firebase Firestore
        firestore = FirebaseFirestore.getInstance();
        docRef = firestore.collection("hotspot").document("testing");

        // Retrieve marker hotspot data from firebase firestore
        getData();

        // Check permission
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission granted
            //call method
            getCurrentLocation();
        } else {
            //when permission denied
            //request permission
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }

        // back button
        ImageButton btn_back = view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_hotspot_to_home);
            }
        });
        return view;
    }

    public void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //initialize location
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            Toast.makeText(getContext(), "Map is connected", Toast.LENGTH_SHORT).show();
                            //initialize lat lng
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            //create current location marker
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("My location");
                            //zoom map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            //Add marker on map
                            googleMap.addMarker(markerOptions);
                            if (ContextCompat.checkSelfPermission(getContext(),
                                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(),
                                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            //Current location tools
                            googleMap.setMyLocationEnabled(true);

                            // get marker data
                            docRef.collection("mapMarkers").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                    // get geopoint data from firebase
                                                    GeoPoint geoPoint = documentSnapshot.getGeoPoint("marker");
                                                    // convert geopoint to LatLng
                                                    Double lat = geoPoint.getLatitude();
                                                    Double lng = geoPoint.getLongitude();
                                                    LatLng latLng = new LatLng(lat, lng);
                                                    Log.d("marker", latLng.toString());

                                                    // add markers
                                                    googleMap.addMarker(new MarkerOptions().title("Confirmed cases").position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                                                }
                                            } else {
                                                Log.d("mapMarkers", task.getException().getMessage().toString());
                                            }
                                        }
                                    });

                        }
                    });
                }
            }
        });

    }

    private void getData() {
        // get hotspot data
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Hotspot hotspot = new Hotspot(documentSnapshot.get("confirmed").toString(), documentSnapshot.get("recovered").toString(), documentSnapshot.get("death").toString());
                // set up view
                tv_confirmed_number.setText(hotspot.getConfirmed_num());
                tv_recovered_number.setText(hotspot.getRecovered_num());
                tv_death_number.setText(hotspot.getDeath_num());
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //when permission granted
                //call method
                getCurrentLocation();
            }
        }
    }
}
