package com.cs121.myparkspot;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity: ";

    /*
     * Activity/Fragment Lifecycle Functions: onCreate(Bundle)
     * Author: Alec Felt
     *
     * Purpose: Set activity View and add parkListFrag fragment
     *
     * @param: Bundle savedInstance
     * @return: none
     * TODO: none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_home);
        parkListFrag listFrag = new parkListFrag();
        getSupportFragmentManager().beginTransaction().add(R.id.container, listFrag).commit();
    }

}
