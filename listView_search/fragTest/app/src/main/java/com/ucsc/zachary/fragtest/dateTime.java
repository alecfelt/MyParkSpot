package com.ucsc.zachary.fragtest;

/**
 * Created by alecfelt on 11/4/17.
 *
 * This code hasn't been tested yet!
 */

// date/time object
public class dateTime {
    private int year;
    private int month;
    private int day;
    private int hour;
    private String meridiem;
    // constructors
    public dateTime() {};
    public dateTime(int y, int mo, int d, int h, String m) {
        year = y;
        month = mo;
        day = d;
        hour = h;
        meridiem = m;
    }
    // access methods
    public int getYear() {return year;}
    public int getMonth() {return month;}
    public int getDay() {return day;}
    public int getHour() {return hour;}
    public String getMeridiem() {return meridiem;}
    public String getTime() {return String.valueOf(hour) + ":00" + " " + meridiem + ", " + String.valueOf(month) + "/" + String.valueOf(day) + "/" + String.valueOf(year);}
    // manipulation methods
    public void setYear(int y) {year = y;}
    public void setMonth(int m) {month = m;}
    public void setDay(int d) {day = d;}
    public void setHour(int h) {hour = h;}
    public void setMeridiem(String m) {meridiem = m;}
}