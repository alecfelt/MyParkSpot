package com.cs121.myparkspot;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by alecfelt on 11/4/17.
 */

    /*
     * Location Object: class address
     * Author: Alec Felt
     *
     * Purpose: Traditional Java object with access, manipulation, and construction functions
     *
     * @param: none
     * @return: none
     * TODO: none
     */
@IgnoreExtraProperties
public class address {
    private static final String TAG = "address: ";
    public String street;
    public String state;
    public String city;
    public String zip;
    // constructors
    public address() {};
    public address(String str, String sta, String c, String z) {
        Log.i(TAG, "address()");
        street = str;
        state = sta;
        city = c;
        zip = z;
    }
    // access methods
    public String getStreet() {return street;}
    public String getState() {return state;}
    public String getCity() {return city;}
    public String getZip() {return zip;}
    public String getAddress() {return (street + ", " + city + ", " + state + " " + zip); }
    // manipulation methods
    public void setStreet(String str) {street = str;}
    public void setState(String sta) {state = sta;}
    public void setCity(String c) {city = c;}
    public void setZip(String z) {zip = z;}
}
