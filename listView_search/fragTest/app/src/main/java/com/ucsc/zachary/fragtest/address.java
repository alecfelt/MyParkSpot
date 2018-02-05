package com.ucsc.zachary.fragtest;

/**
 * Created by alecfelt on 11/4/17.
 *
 * This code hasn't been tested yet!
 */

// address object
public class address {
    private String street;
    private String state;
    private String city;
    private String zip;
    // constructors
    public address() {};
    public address(String str, String sta, String c, String z) {
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