package com.cs121.myparkspot;

import android.util.Log;

/**
 * Created by alecfelt on 11/4/17.
 *
 * This code hasn't been tested yet!
 */

    /*
     * Time Object: class dateTime
     * Author: Alec Felt
     *
     * Purpose: Traditional Java object with access, manipulation, and construction functions
     *
     * @param: none
     * @return: none
     * TODO: none
     */

public class dateTime {
    private static final String TAG = "dateTime: ";
    private int year;
    private int month;
    private int day;
    private int hour;
    private String meridiem;
    // constructors
    public dateTime() {};
    public dateTime(int y, int mo, int d, int h, String m) {
        Log.i(TAG, "dateTime()");
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
    public String getTime() {Log.i(TAG, "getTime()");
        return String.valueOf(hour) + ":00" + " " + meridiem + ", "
            + String.valueOf(month) + "/" + String.valueOf(day) + "/" + String.valueOf(year);}
    // manipulation methods
    public void setYear(int y) {year = y;}
    public void setMonth(int m) {month = m;}
    public void setDay(int d) {day = d;}
    public void setHour(int h) {hour = h;}
    public void setMeridiem(String m) {meridiem = m;}
}