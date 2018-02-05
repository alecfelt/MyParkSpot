package com.cs121.myparkspot;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by alecfelt on 11/18/17.
 */

    /*
     * Utility Functions Object: class utility
     * Author: Alec Felt
     *
     * Purpose: Provide static utility functions to the project scope
     *
     * @param: none
     * @return: none
     * TODO: none
     */

public class utility {

    private static final String TAG = "utility: ";

    /*
     * Display Function: toast(Context, String)
     * Author: Alec Felt
     *
     * Purpose: Display a string to the activity's screen
     *
     * @param: Context, String
     * @return: void
     * TODO: none
     */

    static public void toast(Context c, String s) {
        Toast.makeText(c.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    /*
     * Conversion Function: mon(String)
     * Author: Alec Felt
     *
     * Purpose: Returns int equivalent of the month String
     *
     * @param: String
     * @return: int
     * TODO: none
     */

    static public int mon(String s) {
        switch(s) {
            case "Jan" :return 1;
            case "Feb" :return 2;
            case "Mar" :return 3;
            case "Apr" :return 4;
            case "May" :return 5;
            case "Jun" :return 6;
            case "Jul" :return 7;
            case "Aug" :return 8;
            case "Sep" :return 9;
            case "Oct" :return 10;
            case "Nov" :return 11;
            case "Dec" :return 12;
        } return 0;
    }

}
