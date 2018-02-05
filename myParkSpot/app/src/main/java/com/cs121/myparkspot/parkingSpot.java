package com.cs121.myparkspot;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by alecfelt on 11/4/17.
 */

    /*
     * Parking Spot Object: class parkingSpit
     * Author: Alec Felt
     *
     * Purpose: Traditional Java object with access, manipulation, and construction functions.
     *          Contains times, address, and other metadata
     *
     * @param: none
     * @return: none
     * TODO: none
     */
public class parkingSpot implements Serializable {
    // private
    private String LOG = "parkingSpot: ";
    public address address;
    public dateTime startDate, endDate;
    public String price;
    public String instructions;
    public String thumbnail;
    public String image;
    public String renter;
    public String lister;
    public String key;
    public boolean reserved;
    public int rating, ratingNum;
    // constructors
    public parkingSpot() {};
    public parkingSpot(dateTime sd, dateTime ed, address a, String p, String i,
                       String l, String r, String k, String thmbnl, String img) {
        Log.i(LOG, "parkingSpot()");
        address = a;
        startDate = sd; endDate = ed;
        price = p;
        instructions = i;
        lister = l;
        renter = r;
        key = k;
        thumbnail = thmbnl;
        image = img;
        reserved = false;
        rating = 0; ratingNum = 0;
    }

    // access methods
    public address getAddress() {return address;}
    public dateTime getStartDate() {return startDate;}
    public dateTime getEndDate() {return endDate;}
    public String getPrice() {return price;}
    public String getInstructions() {return instructions;}
    public boolean getReserved() {return reserved;}
    public int getRating() {return rating;}
    public int getRatingNum() {return ratingNum;}
    public String getThumbnail() {return thumbnail;}
    public String getLister() {return lister;}
    public String getRenter() {return renter;}
    public String getKey() {return key;}
    public String getImage() {return image;}
    // manipulation methods
    public void setPrice(String p) {price = p;}
    public void setInstructions(String i) {instructions = i;}
    public void setReserved(boolean b) {reserved=b;}
    public void setAddress(String str, String sta, String c, String z) {
        address = new address(str, sta, c, z);
    }
    public void setStartDate(int y, int m, int d, int h, String me) {
        startDate = new dateTime(y, m ,d, h, me);
    }
    public void setEndDate(int y, int m, int d, int h, String me) {
        endDate = new dateTime(y, m ,d, h, me);
    }
    public void setLister(String l) {lister = l;}
    public void setRenter(String r) {renter = r;}
    public void setThumbnail(String t) {thumbnail = t;}
    public void setKey(String k) {key = k;}
    public void setImage(String i) {image = i;}
}





