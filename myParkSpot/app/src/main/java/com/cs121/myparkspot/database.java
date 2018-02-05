package com.cs121.myparkspot;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by alecfelt on 11/18/17.
 */

/*
 * Database Object: class database
 * Author: Alec Felt
 *
 * Purpose: Provide static firebase functions to the project scope
 *
 * @param: none
 * @return: none
 * TODO: none
 */

public class database {

    private static final String TAG = "database: ";

    /*
     * Firebase Function: login(Context, String, String)
     * Author: Alec Felt
     *
     * Purpose: FirebaseAuth login wrapper
     *
     * @param: Context, String, String
     * @return: void
     * TODO: none
     */

    static public void login(final Context c, String e, String p) {
        Log.i(TAG, "login()");
        FirebaseAuth.getInstance().signInWithEmailAndPassword(e, p)
                .addOnCompleteListener((Activity)c, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(TAG, "signInWithEmail:success");
                            navigation.navToHome(c);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i(TAG, "signInWithEmail:failure", task.getException());
                            utility.toast(c, "Authentication failed.");
                        }

                        // ...
                    }
                });
    }

    /*
     * Firebase Function: signup(Context, String, String)
     * Author: Alec Felt
     *
     * Purpose: FirebaseAuth signup wrapper
     *
     * @param: Context, String, String
     * @return: void
     * TODO: none
     */

    static public void signup(final Context c, String e, String p) {
        Log.i(TAG, "signup()");
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(e, p)
                .addOnCompleteListener((Activity)c, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            utility.toast(c, "Signup successful.");
                            navigation.navToSignup(c);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            utility.toast(c, "Authentication failed.");
                        }

                        // ...
                    }
                });
    }

    /*
     * Firebase Function: pushUser(Context, user)
     * Author: Alec Felt
     *
     * Purpose: Pushes user object to the currentUser's database info
     *
     * @param: Context, user
     * @return: void
     * TODO: Error Message
     */

    static public void pushUser(Context c, user u) {
        Log.i(TAG, "pushUser()");
        FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
        if(us != null) {
            FirebaseDatabase.getInstance().getReference("users/" + us.getUid()).setValue(u);
        }
    }

    /*
     * Firebase Function: autoLogin(Context)
     * Author: Alec Felt
     *
     * Purpose: Checks FirebaseAuth currentUser status
     *
     * @param: Context
     * @return: void
     * TODO: none
     */

    static public void autoLogin(Context c) {
        Log.i(TAG, "autoLogin()");
        if(FirebaseAuth.getInstance().getCurrentUser() != null) { navigation.navToHome(c); }
    }

    /*
     * Firebase Function: pushSpot(Context, parkingSpot)
     * Author: Alec Felt
     *
     * Purpose: Adds a parkingSpot the database
     *
     * @param: Context, parkingSpot
     * @return: void
     * TODO: Error Message
     */
    static public void pushSpot(Context c, parkingSpot p) {
        Log.i(TAG, "pushSpot()");
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            String key = FirebaseDatabase.getInstance().getReference("parkingSpots")
                    .push().getKey();
            p.setKey(key);
            FirebaseDatabase.getInstance().getReference("parkingSpots/"+key).setValue(p);
            String k = FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth
                    .getInstance().getUid()+"/listings").push().getKey();
            FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth.getInstance()
                    .getUid()+"/listings/"+k).setValue(key);
        }
    }

    /*
     * Firebase Function: signOut(Context)
     * Author: Alec Felt
     *
     * Purpose: Clears FirebaseAuth's currentUser
     *
     * @param: Context
     * @return: void
     * TODO: Error Message
     */
    static public void signOut(Context c) {
        Log.i(TAG, "signOut()");
        FirebaseAuth.getInstance().signOut();
    }

    /*
     * Firebase Linking Function: reserveSpot()
     * Author: Alec Felt
     *
     * Purpose: links the parkingSpot to the user's reservations object,
     *          links the user to the parkingSpot's renter field
     *
     * @return: void
     * TODO: nothing
     */
    static public void reserveSpot(String spotId) {
        Log.i(TAG, "reserveSpot()");
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance().getReference("parkingSpots/"+spotId+"/renter")
        .setValue(uid)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Log.i(TAG, "reserveSpot():parkingSpots renter success");
                        } else {
                            Log.i(TAG, "reserveSpot():parkingSpots renter failure");
                        }
                    }
                });
        FirebaseDatabase.getInstance().getReference("parkingSpots/"+spotId+"/reserved")
                .setValue(true)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Log.i(TAG, "reserveSpot():parkingSpots reserved success");
                        } else {
                            Log.i(TAG, "reserveSpot():parkingSpots reserved failure");
                        }
                    }
                });
        FirebaseDatabase.getInstance().getReference("users/"+uid+"/reservations")
        .push().setValue(spotId)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Log.i(TAG, "reserveSpot():users success");
                        } else {
                            Log.i(TAG, "reserveSpot():users failure");
                        }
                    }
                });
    }

}
