package com.cs121.myparkspot;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//TODO: AccountActivity currently on hold. Will be used for personal Account only.

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = "AccountActivity: ";
    Query q;
    ValueEventListener listener;
    ArrayList<parkingSpot> listings, reservations;
    user u;
    private LayoutInflater inflater;
    private ListView parkListAccountActivity;
    private TextView fullNameTV, phoneNumberTV, addressTV, emailTV;
    private ListRowArrayAdapter listingAdapterAccountActivity;

    /*
     * Lifecycle Functions: onCreate(Bundle), onDestroy()
     * Author: Alec Felt
     *
     * onCreate() Purpose: queries the user object and fills global variables with relevant info
     * onDestroy() Purpose: detaches listener from Query
     *
     * @return: void
     * TODO: none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_account);
        parkListAccountActivity = (ListView) findViewById(R.id.parkList_AccountActivity);
        listings = new ArrayList<parkingSpot>();

        queryUser();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onCreate()");
        q.removeEventListener(listener);
    }

    /*
     * Firebase Database Query function: queryUser()
     * Author: Alec Felt
     *
     * Purpose: adds ValueEventListeners to the Firebase Query References,
     *          fills the global user and ArrayList<parkingSpot> variable
     *
     * @param: String
     * @return: void
     * TODO: none
     */
    private void queryUser() {
        Log.i(TAG, "queryUser()");

        // users Firebase Reference

        String ref = "users/"+FirebaseAuth.getInstance().getCurrentUser().getUid();
        q = FirebaseDatabase.getInstance().getReference(ref);

        // valueEventListener for user Firebase Reference

        listener = new ValueEventListener() {

            // executes when data for user changes

            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                Log.i(TAG, "queryUser(): onDataChange()");

                // error checking for query

                if(!dataSnapshot.exists()) {
                    Log.i(TAG, "queryUser(): no user data exists");
                } else {

                    // user info

                    String firstName = dataSnapshot.child("firstName").getValue().toString();
                    String middleName = dataSnapshot.child("middleName").getValue().toString();
                    String lastName = dataSnapshot.child("lastName").getValue().toString();
                    String phone = dataSnapshot.child("phone").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String street = dataSnapshot.child("address").child("street").getValue().toString();
                    String state = dataSnapshot.child("address").child("state").getValue().toString();
                    String city = dataSnapshot.child("address").child("city").getValue().toString();
                    String zip = dataSnapshot.child("address").child("zip").getValue().toString();
                    address a = new address(street, state, city, zip);
                    String renterRating = dataSnapshot.child("renterRating").getValue().toString();
                    String renterRatingNum = dataSnapshot.child("renterRatingNum").getValue().toString();
                    String listerRating = dataSnapshot.child("listerRating").getValue().toString();
                    String listerRatingNum = dataSnapshot.child("listerRatingNum").getValue().toString();

                    u = new user(firstName, middleName, lastName, phone, email, a);
                    Log.i(TAG, u.getAddress().getAddress());

                    String fName = u.getFirstName();
                    String mName = u.getMiddleName();
                    String lName = u.getLastName();
                    String fullname = fName + " " + mName + " " + lName;

                    fullNameTV = findViewById(R.id.fullName_AccountActivity);
                    fullNameTV.setText(fullname);
                    phoneNumberTV = findViewById(R.id.phoneNumber_AccountActivity);
                    phoneNumberTV.setText(u.getPhone());
                    addressTV = findViewById(R.id.address_AccountActivity);
                    addressTV.setText(u.getAddress().getAddress());
                    emailTV = findViewById(R.id.emailAddress_AccountActivity);
                    emailTV.setText(u.getEmail());

                    // user listings

                    listings = new ArrayList<>();

                    // error checking for query

                    if(dataSnapshot.child("listings").exists()) {

                        // valueEventListener for parkingsSpots Firebase Reference

                        Query q1 = FirebaseDatabase.getInstance().getReference("parkingSpots/");
                        ValueEventListener l1 = new ValueEventListener() {

                            // executes when parkingSpots data is changed

                            @Override
                            public void onDataChange(final DataSnapshot d) {

                                // loops through users listings

                                for (DataSnapshot shot: dataSnapshot.child("listings").getChildren()) {

                                    // creates address object

                                    Log.i(TAG, d.child(shot.getValue().toString()).getValue().toString());
                                    DataSnapshot a = d.child(shot.getValue()+"/address");
                                    address addy = new address(a.child("street").getValue().toString(),
                                            a.child("state").getValue().toString(),
                                            a.child("city").getValue().toString(),
                                            a.child("zip").getValue().toString());

                                    // creates dateTime object

                                    a = d.child(shot.getValue()+"/startDate");
                                    dateTime startDate = new dateTime(Integer.valueOf(a.child("year")
                                            .getValue().toString()),
                                            Integer.valueOf(a.child("month").getValue().toString()),
                                            Integer.valueOf(a.child("day").getValue().toString()),
                                            Integer.valueOf(a.child("hour").getValue().toString()),
                                            a.child("meridiem").getValue().toString());

                                    // creates dateTime object

                                    a = d.child(shot.getValue()+"/endDate");
                                    dateTime endDate = new dateTime(Integer.valueOf(a.child("year")
                                            .getValue().toString()),
                                            Integer.valueOf(a.child("month").getValue().toString()),
                                            Integer.valueOf(a.child("day").getValue().toString()),
                                            Integer.valueOf(a.child("hour").getValue().toString()),
                                            a.child("meridiem").getValue().toString());

                                    // creates parkingSpot object

                                    String renter;
                                    if(d.child(shot.getValue()+"/renter").getValue() == null) {
                                        renter = null;
                                    } else {
                                        renter = d.child(shot.getValue()+"/renter").getValue().toString();
                                    }
                                    parkingSpot spot = new parkingSpot(startDate, endDate, addy,
                                            d.child(shot.getValue()+"/price").getValue().toString(),
                                            d.child(shot.getValue()+"/instructions").getValue().toString(),
                                            d.child(shot.getValue()+"/lister").getValue().toString(),
                                            renter,
                                            d.child(shot.getValue()+"/key").getValue().toString(),
                                            d.child(shot.getValue()+"/thumbnail").getValue().toString(),
                                            d.child(shot.getValue()+"/image").getValue().toString());

                                    // adds parkingSpot object to the user's list of listings

                                    listings.add(spot);
                                    Log.i(TAG, "listings: "+spot.getAddress().getAddress());
                                }
                            }

                            // executes when the listener is removed

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        };
                        q1.addValueEventListener(l1);
                        Log.i(TAG, "q1 and l1 set");
                    }

                    // the user's reservations

                    reservations = new ArrayList<>();

                    // error checking for query

                    if(dataSnapshot.child("reservations").exists()) {

                        // valueEventListener for parkingSpots

                        Query q2 = FirebaseDatabase.getInstance().getReference("parkingSpots/");
                        ValueEventListener l2 = new ValueEventListener() {

                            // executes when parkingSpots data is changed

                            @Override
                            public void onDataChange(final DataSnapshot d) {

                                // loops through the user's reservations

                                for (DataSnapshot shot : dataSnapshot.child("reservations").getChildren()) {

                                    // creates address object

                                    Log.i(TAG, d.child(shot.getValue().toString()).getValue().toString());
                                    DataSnapshot a = d.child(shot.getValue() + "/address");
                                    address addy = new address(a.child("street").getValue().toString(),
                                            a.child("state").getValue().toString(),
                                            a.child("city").getValue().toString(),
                                            a.child("zip").getValue().toString());

                                    // creates dateTime object

                                    a = d.child(shot.getValue() + "/startDate");
                                    dateTime startDate = new dateTime(Integer.valueOf(a.child("year")
                                            .getValue().toString()),
                                            Integer.valueOf(a.child("month").getValue().toString()),
                                            Integer.valueOf(a.child("day").getValue().toString()),
                                            Integer.valueOf(a.child("hour").getValue().toString()),
                                            a.child("meridiem").getValue().toString());

                                    // creates dateTime object

                                    a = d.child(shot.getValue() + "/endDate");
                                    dateTime endDate = new dateTime(Integer.valueOf(a.child("year")
                                            .getValue().toString()),
                                            Integer.valueOf(a.child("month").getValue().toString()),
                                            Integer.valueOf(a.child("day").getValue().toString()),
                                            Integer.valueOf(a.child("hour").getValue().toString()),
                                            a.child("meridiem").getValue().toString());

                                    // creates parkingSpot object

                                    String renter;
                                    if (d.child(shot.getValue() + "/renter").getValue() == null) {
                                        renter = null;
                                    } else {
                                        renter = d.child(shot.getValue() + "/renter").getValue().toString();
                                    }
                                    parkingSpot spot = new parkingSpot(startDate, endDate, addy,
                                            d.child(shot.getValue() + "/price").getValue().toString(),
                                            d.child(shot.getValue() + "/instructions").getValue().toString(),
                                            d.child(shot.getValue() + "/lister").getValue().toString(),
                                            renter,
                                            d.child(shot.getValue() + "/key").getValue().toString(),
                                            d.child(shot.getValue() + "/thumbnail").getValue().toString(),
                                            d.child(shot.getValue() + "/image").getValue().toString());

                                    // add parkingSpot object to the user's list of reservations

                                    reservations.add(spot);
                                    Log.i(TAG, "reservations: " + spot.getAddress().getAddress());
                                    displayReservations();
                                }
                            }

                            // executes when the listener is removed

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        };

                        q2.addValueEventListener(l2);
                        Log.i(TAG, "q2 and q1 set");

                    }
                    int listingsNum = listings.size();
                    int reservationsNum = reservations.size();

                }
            }

            // executes when the listener is removed

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "queryUser(): onCancelled()");
                // Getting Post failed, log a message
                Log.i(TAG, databaseError.getDetails());
            }
        };

        q.addValueEventListener(listener);
        Log.i(TAG, "q and listener set");
    }


    public void displayReservations() {
        Log.i(TAG, "displayReservations()");
        listingAdapterAccountActivity = new ListRowArrayAdapter(getApplication(), reservations);
        parkListAccountActivity.setAdapter(listingAdapterAccountActivity);
        parkListAccountActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = parkListAccountActivity.getItemAtPosition(position);
                parkingSpot listing = (parkingSpot) o;
                Intent intent = new Intent(getApplication(), ViewParkingSpotActivity.class);
                intent.putExtra("key", listing.getKey());
                Log.i(TAG,
                        "Switching from AccountActivity to ViewParkingSpotActivity with Key: " + listing.getKey());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getApplicationContext().startActivity(intent);

            }
        });
    }
}
