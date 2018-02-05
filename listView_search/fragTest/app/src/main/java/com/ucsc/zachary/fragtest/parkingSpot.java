package com.ucsc.zachary.fragtest;

/**
 * Created by alecfelt on 11/4/17.
 *
 * This code hasn't been tested yet!
 */

public class parkingSpot {
    // private
    private address addy;
    private dateTime startDate;
    private dateTime endDate;
    private String price;
    private String instructions;
    private boolean reserved;
    private String timestamp;
    // constructors
    public parkingSpot() {};
    public parkingSpot(dateTime sd, dateTime ed, address a, String p, String i) {
        startDate = sd;
        endDate = ed;
        addy = a;
        price = p;
        instructions = i;
        reserved = false;
        timestamp = "";
    }
    // access methods
    public address getAddress() {return addy;}
    public dateTime getStartDate() {return startDate;}
    public dateTime getEndDate() {return endDate;}
    public String getPrice() {return price;}
    public String getInstructions() {return instructions;}
    public boolean getReserved() {return reserved;}
    public String getTimestamp() {return timestamp;}
    // manipulation methods
    public void setAddress(String str, String sta, String c, String z) {addy = new address(str, sta, c, z);}
    public void setStartDate(int y, int mo, int d, int h, String m) {startDate = new dateTime(y, mo, d, h, m);}
    public void setEndDate(int y, int mo, int d, int h, String m) {endDate = new dateTime(y, mo, d, h, m);}
    public void setPrice(String p) {price = p;}
    public void setInstructions(String i) {instructions = i;}

}