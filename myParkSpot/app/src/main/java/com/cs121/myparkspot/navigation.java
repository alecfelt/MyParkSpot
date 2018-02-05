package com.cs121.myparkspot;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by alecfelt on 11/18/17.
 */

    /*
     * Navigation Functions Object: class navigation
     * Author: Alec Felt
     *
     * Purpose: Provides static navigation functions to the project scope
     *
     * @param: none
     * @return: none
     * TODO: none
     */

public class navigation {

    static String LOG = "navigation: ";

    /*
     * Navigation function: navToHome()
     * Author: Alec Felt
     *
     * Purpose: navigation function that clears the navigation stack
     *
     * @param: Context
     * @return: none
     * TODO: Do we want these functions to clear the stack?
     */

    static public void navToHome(Context c) {
        Log.i(LOG, "navToHome()");
        Intent intent = new Intent(c, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(intent);
    }

    /*
     * Navigation function: navToSignup()
     * Author: Alec Felt
     *
     * Purpose: navigation function that clears the navigation stack
     *
     * @param: Context
     * @return: none
     * TODO: Do we want these functions to clear the stack?
     */

    static public void navToSignup(Context c) {
        Log.i(LOG, "navToSignup()");
        Intent intent = new Intent(c, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(intent);
    }

    /*
     * Navigation function: navToLogin()
     * Author: Alec Felt
     *
     * Purpose: navigation function that clears the navigation stack
     *
     * @param: Context
     * @return: none
     * TODO: Do we want these functions to clear the stack?
     */
    static public void navToLogin(Context c) {
        Log.i(LOG, "navToLogin()");
        Intent intent = new Intent(c, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(intent);
    }

    /*
     * Navigation function: navToCreateSpot()
     * Author: Alec Felt
     *
     * Purpose: navigation function that clears the navigation stack
     *
     * @param: Context
     * @return: none
     * TODO: Do we want these functions to clear the stack?
     */
    static public void navToCreateSpot(Context c) {
        Log.i(LOG, "navToCreate()");
        Intent intent = new Intent(c, ParkingSpotActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(intent);
    }

    static public void navToProfile(Context c) {
        Log.i(LOG, "navToProfile()");
        Intent intent = new Intent(c, AccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(intent);
    }

    static public void navToParkingSpot(Context c) {
        Log.i(LOG, "navToParkingSpot()");
        Intent intent = new Intent(c, ViewParkingSpotActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(intent);
    }

    static public void navToMap(Context c) {
        Log.i(LOG, "navToParkingSpot()");
        Intent intent = new Intent(c, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(intent);
    }
}
