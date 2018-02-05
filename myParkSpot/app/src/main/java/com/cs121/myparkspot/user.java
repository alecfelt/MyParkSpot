package com.cs121.myparkspot;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by alecfelt on 11/13/17.
 */

    /*
     * User Object: class user
     * Author: Alec Felt
     *
     * Purpose: Traditional Java object with access, manipulation, and construction functions
     *
     * @param: none
     * @return: none
     * TODO: none
     */

@IgnoreExtraProperties
public class user {
    // Class variables
    private static final String TAG = "user: ";
    // Instance Variables
    public String firstName;
    public String middleName;
    public String lastName;
    public String fullName;
    public String phone;
    public String email;
    public address address;
    public float renterRating, renterRatingNum, listerRating, listerRatingNum;
    // Constructors
    public user() {}
    public user(String f, String m, String l, String p, String e, address a) {
        Log.i(TAG, "user()");
        firstName=f;middleName=m;lastName=l;phone=p;email=e;address=a;
        renterRating=0;listerRating=0;renterRatingNum=0;listerRatingNum=0;
        fullName=f + m + l;
    }
    // Access methods
    public String getFirstName() {return firstName;}
    public String getMiddleName() {return middleName;}
    public String getLastName() {return lastName;}
    public String getFullName() {return fullName;}
    public String getPhone() {return phone;}
    public String getEmail() {return email;}
    public address getAddress() {return address;}
    public float getRenterRating() {return renterRating;}
    public float getRenterRatingNum() {return renterRatingNum;}
    public float getListerRating() {return listerRating;}
    public float getListerRatingNum() {return listerRatingNum;}
    // Manipulation Methods
    public void setFirstName(String s) {firstName=s;}
    public void setMiddleName(String s) {middleName=s;}
    public void setLastName(String s) {lastName=s;}
    public void setPhone(String s) {phone=s;}
    public void setEmail(String s) {email=s;}
    public void setAddress(address a) {address = a;}
    public void setRenterRating(float f) {renterRating=f;}
    public void setRenterRatingNum(float f) {renterRatingNum=f;}
    public void setListerRating(float f) {listerRating=f;}
    public void setListerRatingNum(float f) {listerRatingNum=f;}
}
