package com.cs121.myparkspot;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;


/* <Class> parkListFrag
 * Author: Zachary Olson
 * Purpose: Manages and Creates ListView fragment view.
 */

public class parkListFrag extends Fragment implements
        View.OnClickListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final String TAG = "parkingListFrag: ";
    private ListView parkList;
    private ListRowArrayAdapter listingAdapter;
    private android.widget.SearchView searchView;
    private SearchManager searchManager;
    private MenuItem searchItem;
    FirebaseAuth mAuth;
    View signOut, createSpot, mapView, myProfile;
    ArrayList<parkingSpot> spots;
    ArrayList<user> users;
    Query q;
    ValueEventListener listener;


    /* <Constructor> parkListFrag
      * Author: Zachary Olson
      * Purpose:  Creates a parkListFrag object.
      * Parameters: None.
      * Return: parkListFrag object
      */
    public parkListFrag() {
        Log.i(TAG, "parkListFrag()");
    }

    /* <Function> onCreateView
      * Author: Zachary Olson
      * Purpose: Used by FragmentManager on parkListFrag being added to the Activity.
      * Parameters: LayoutInflater, ViewGroup, Bundle
      * Return: ListView fragment View.
      */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_park_list, container, false);
        parkList = (ListView) view.findViewById(R.id.parkList);
        searchManager = (SearchManager) getActivity().getSystemService(getContext().SEARCH_SERVICE);
        mAuth = FirebaseAuth.getInstance();
        signOut = view.findViewById(R.id.signout_button);
        signOut.setOnClickListener(this);
        createSpot = view.findViewById(R.id.create_spot);
        createSpot.setOnClickListener(this);
        myProfile = view.findViewById(R.id.my_profile);
        myProfile.setOnClickListener(this);
        mapView = view.findViewById(R.id.maps_button);
        mapView.setOnClickListener(this);
        spots = new ArrayList<>();

        queryParkingSpots();
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        q.removeEventListener(listener);
    }

    /*
     * Firebase Function: queryParkingSpots(void)
     * Author: Alec Felt
     *
     * Purpose: Sets a listener that fills an ArrayList<parkingSpot> with parkingSpots from Firebase
     *
     * @param: void
     * @return: void
     * TODO: none
     */
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
                Log.d(TAG, databaseError.getDetails());
            }
        };

        q.addValueEventListener(listener);
    }

    /* <Function> onCreateOptionsMenu
     * Author: Sanjeet Sanhotra
     * Purpose: Creates the SearchBar Fragment above the ListView Fragment.
     * Parameters: Menu, MenuInflater
     * Return: Nothing.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        Log.i(TAG, "onCreateOptionsMenu()");
        //inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);

        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();

        //return true;
    }

    /* <Function> onOptionsItemsSelected
     * Author: Sanjeet
     * Purpose: Save filter options through settings menu
     * Parameters: MenuItem item
     * Return: true.
     */
    public boolean onOptionsItemSelected(MenuItem item){
        // SharedPreferences sharedPref = this.getSharedPreferences("searchInfo", Context.MODE_PRIVATE);
        int id = item.getItemId();
        if(id == R.id.action_pricemin){
            //item.setChecked(!item.isChecked());
            Log.i(TAG,"Pricemin Click Registered");
            KeyValueDB.setPriceMin(getActivity());
            item.setChecked((KeyValueDB.getPriceMin(getActivity())));
            Log.i(TAG,String.valueOf(KeyValueDB.getPriceMin(getActivity())));
        }
        if(id == R.id.action_pricemax){
            //item.setChecked(!item.isChecked());
            Log.i(TAG,"Pricemax Click Registered");
            KeyValueDB.setPriceMax(getActivity());
            item.setChecked((KeyValueDB.getPriceMax(getActivity())));
            Log.i(TAG,String.valueOf(KeyValueDB.getPriceMax(getActivity())));
        }
        return true;
    }

    /*
     * Click Listener Function: onClick(View v)
     * Author: Alec Felt
     *
     * Purpose: Decides which button has been clicked
     *
     * @param: View
     * @return: void
     * TODO: none
     */
    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick()");
        if(v == createSpot) {
            navigation.navToCreateSpot(getContext());
        } else if(v == signOut) {
            database.signOut(getContext());
            navigation.navToLogin(getContext());
        } else if (v == myProfile) {
            navigation.navToProfile(getContext());
        } else if (v == mapView) {
            navigation.navToMap(getContext());
        }
    }


    @Override
    public boolean onClose() {
        Log.i(TAG, "onClose()");
        listingAdapter.filterData("");
        return false;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i(TAG, "onQueryTextSubmit()");
        listingAdapter.filterData(query);
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i(TAG, "onQueryTextChange()");
        listingAdapter.filterData(newText);
        return false;
    }

    /*
     * Populate Function: displaySpots()
     * Author: Alec Felt
     *
     * Purpose: Populates list View with the adapter's parkingSpots
     *
     * @param: void
     * @return: void
     * TODO: none
     */
    public void displaySpots() {
        Log.i(TAG, "displaySpots()");
        listingAdapter = new ListRowArrayAdapter(getActivity(), spots);
        parkList.setAdapter(listingAdapter);
        parkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = parkList.getItemAtPosition(position);
                parkingSpot listing = (parkingSpot) o;
                Intent intent = new Intent(getActivity(), ViewParkingSpotActivity.class);
                intent.putExtra("key", listing.getKey());
                Log.i(TAG,
                        "Switching to ViewParkingSpotActivity with Key: " + listing.getKey());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(intent);

            }
        });
    }

}
