package com.cs121.myparkspot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Button search;
    private LocationManager locationManager;
    private LocationListener locationListener;
    double searchLatitude, searchLongitude;
    double endLatitude, endLongitude;
    int kilometers;
    List<Address> displayedAdresses;
    ArrayList<parkingSpot> spots;
    Query q;
    ValueEventListener listener;
    HashMap<String, String> listings;

    static private final String TAG = "MapsActivity: ";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        spots = null;
        q = null;
        listener = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        search = (Button) findViewById(R.id.searchButton);
        listings = new HashMap<>();

        //setUpMap();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        queryParkingSpots();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        q.removeEventListener(listener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "onMapReady()");
        mMap = googleMap;
        setUpMap();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(TAG, "onMapReady(): onLocationChanged()");
                //mMap.clear();
                setUpMap();
                //LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                //mMap.addMarker(new MarkerOptions().position(myLocation).title("Your Location"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.i(TAG, "onMapReady(): onStatusChanged()");
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.i(TAG, "onMapReady(): onProviderEnabled()");
            }

            @Override
            public void onProviderDisabled(String s) {
                Log.i(TAG, "onMapReady(): onProviderDisabled()");
            }

        };
        mMap.setOnMarkerClickListener(this);
        //ask for permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            //Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            //LatLng myLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            //mMap.addMarker(new MarkerOptions().position(myLocation).title("Your Location"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
            setUpMap();
        }

    }

    @Override
    public boolean onMarkerClick(final Marker m) {
        String title = m.getTitle();
        String key = listings.get(title);
        Intent intent = new Intent(this, ViewParkingSpotActivity.class);
        intent.putExtra("key", key);
        Log.i(TAG,
                "Switching to ViewParkingSpotActivity with Key: " + key);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        return false;
    }

    public void setUpMap() {
        Log.i(TAG, "setUpMap()");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    public void populateNearbyListings(){
        Log.i(TAG, "populateNearbyListings()");
        float results[] = new float[10];
        Geocoder gc = new Geocoder(this);
        // List<Address> list = gc.getFromLocation()
        Location.distanceBetween(searchLatitude, searchLongitude, endLatitude, endLongitude, results);
        if(results[0] <= kilometers){

        }

    }


    public void onSearch(View view){
        Log.i(TAG, "onSearch()");
        EditText addressSearch = (EditText) findViewById(R.id.searchText);
        String location = addressSearch.getText().toString();
        List<Address> addressList = null;
        if(location != null || !location.equals("")){
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addressList != null && addressList.size() != 0) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                searchLatitude = address.getLatitude();
                searchLongitude = address.getLongitude();
                mMap.addMarker(new MarkerOptions().position(latLng).title("search"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                populateNearbyListings();
            } else {
                utility.toast(this, "Not an address!");
                Log.i(TAG, "Bad address.");
            }
        }
    }

    private void queryParkingSpots() {
        Log.i(TAG, "queryParkingSpots()");

        // querying the parkingSpots path

        q = FirebaseDatabase.getInstance().getReference("parkingSpots");
        listener = new ValueEventListener() {

            // exectued when data changes and listener is initially set

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "queryParkingSpots(): onDataChange()");
                spots = new ArrayList<>();

                // loops through all parkingSpots

                for (DataSnapshot shot: dataSnapshot.getChildren()) {

                    // only deals with unreserved spots

                    if(!(boolean)shot.child("reserved").getValue() && !Objects.equals(FirebaseAuth.getInstance().getCurrentUser().getUid(), shot.child("lister").getValue().toString())) {

                        // creates address object

                        DataSnapshot a = shot.child("address");
                        address addy = new address(a.child("street").getValue().toString(),
                                a.child("state").getValue().toString(),
                                a.child("city").getValue().toString(),
                                a.child("zip").getValue().toString());

                        // creates dateTime object

                        a = shot.child("startDate");
                        dateTime startDate = new dateTime(Integer.valueOf(a.child("year")
                                .getValue().toString()),
                                Integer.valueOf(a.child("month").getValue().toString()),
                                Integer.valueOf(a.child("day").getValue().toString()),
                                Integer.valueOf(a.child("hour").getValue().toString()),
                                a.child("meridiem").getValue().toString());

                        // creates dateTime object

                        a = shot.child("endDate");
                        dateTime endDate = new dateTime(Integer.valueOf(a.child("year")
                                .getValue().toString()),
                                Integer.valueOf(a.child("month").getValue().toString()),
                                Integer.valueOf(a.child("day").getValue().toString()),
                                Integer.valueOf(a.child("hour").getValue().toString()),
                                a.child("meridiem").getValue().toString());

                        // creates parkingSpot object

                        String renter;
                        if (shot.child("renter").getValue() == null) {
                            renter = null;
                        } else {
                            renter = shot.child("renter").getValue().toString();
                        }
                        parkingSpot spot = new parkingSpot(startDate, endDate, addy,
                                shot.child("price").getValue().toString(),
                                shot.child("instructions").getValue().toString(),
                                shot.child("lister").getValue().toString(),
                                renter,
                                shot.getKey(),
                                shot.child("thumbnail").getValue().toString(),
                                shot.child("image").getValue().toString());
                        Log.i(TAG, shot.getKey());

                        // adds spot to glbal data structure

                        spots.add(spot);

                    } else {

                        // do nothing when parkingSpot is already reserved

                    }

                }

                displaySpots();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "queryParkingSpots(): onCancelled()");
                // Getting Post failed, log a message
                Log.i(TAG, databaseError.getDetails());
            }
        };

        q.addValueEventListener(listener);
    }

    private void displaySpots() {
        for(int i = 0; i < spots.size(); i++) {
            Geocoder geocoder = new Geocoder(this);
            address a = spots.get(i).getAddress();
            String addy = a.getAddress();
            displayedAdresses = null;
            try {
                displayedAdresses = geocoder.getFromLocationName(addy, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(displayedAdresses != null && displayedAdresses.size() != 0) {
                Address address = displayedAdresses.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                endLatitude = address.getLatitude();
                endLongitude = address.getLongitude();
                mMap.addMarker(new MarkerOptions().position(latLng).title(addy));
                listings.put(addy, spots.get(i).getKey());
            } else {
                Log.i(TAG, "Bad address.");
            }
        }
    }

}
