package com.cs121.myparkspot;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sanjeet on 12/6/2017.
 */

public class KeyValueDB {
    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "prefs";

    public KeyValueDB() {
        // Blank
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static boolean getPriceMin(Context context) {

        return getPrefs(context).getBoolean("byPriceMin_key", false);

        //return getPrefs(context).getString("username_key", "default_username");

    }

    public static void setPriceMin(Context context) {
        boolean changedVal = getPrefs(context).getBoolean("byPriceMin_key", false);
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean("byPriceMin_key", !changedVal);
        editor.commit();
    }

    public static boolean getPriceMax(Context context) {

        return getPrefs(context).getBoolean("byPriceMax_key", false);

        //return getPrefs(context).getString("username_key", "default_username");

    }

    public static void setPriceMax(Context context) {
        boolean changedVal = getPrefs(context).getBoolean("byPriceMax_key", false);
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean("byPriceMax_key", !changedVal);
        editor.commit();
    }
}

