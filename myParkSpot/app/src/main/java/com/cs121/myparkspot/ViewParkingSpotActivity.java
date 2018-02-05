package com.cs121.myparkspot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;

import java.io.File;
import java.io.IOException;

public class ViewParkingSpotActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ViewParkingSpotActvty: ";
    private Button reserveButton;
    private TextView listerFullName, fullAddress, priceActual, listingInstructionsVPA,
        startTime, endTime;
    private ProgressBar imageProgressBar;
    private LinearLayout layout;
    private ImageView img;
    private String spotId;
    private parkingSpot spot;
    File image;
    Query q;
    ValueEventListener listener;

    /*
     * Lifecycle Function: onCreate(), onDestroy()
     * Author: Alec Felt
     *
     * onCreate() Purpose: fill spot variable with Intent extras,
     *                     query the specific spot's info
     * onDestroy() Purpose: detaches ValueEventListener from Query
     *
     * @return: void
     * TODO: fill spot variable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_view_parking_spot);
        reserveButton = findViewById(R.id.reserve_button);
        reserveButton.setOnClickListener(this);
        img = new ImageView(this);
        layout = findViewById(R.id.linearLayout);
        imageProgressBar = findViewById(R.id.progress_bar_3);
        spot = null;
        spotId = getIntent().getStringExtra("key");
        image = null;
        // need to fill spot String with id of parkingSpot passed with Intent
            // code goes here
        //
        querySpot();

    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy()");
        super.onDestroy();
        q.removeEventListener(listener);
        image.delete();
    }

    @Override
    public void onClick(View v) {
        reserveSpot();
    }

    public void setImage() {
        String path = image.getAbsolutePath();
        Log.i(TAG, "imagePath: "+path);
        Bitmap i = BitmapFactory.decodeFile(path);
        layout.removeView(findViewById(R.id.progress_bar_3));
        int layoutWidth = layout.getMeasuredWidth();
        float imageWidth = i.getWidth();
        float imageHeight = i.getHeight();
        float ratio = (imageHeight/imageWidth);
        int layoutHeight = (int)(layoutWidth * ratio);
        Log.i(TAG, "imageWidth: "+imageWidth+" imageHeight: "+imageHeight+" ratio: "+String.valueOf(ratio));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(layoutWidth, layoutHeight);
        img.setLayoutParams(lp);
        img.setImageBitmap(i);
        layout.addView(img);
    }

    /*
     * Firebase Query Function: querySpot()
     * Author: Alec Felt
     *
     * Purpose: queries FirebaseDatabase parkingSpots 
     *
     * @return: void
     * TODO: fill spot variable, write the code
     */
    private void querySpot() {
        Log.i(TAG, "querySpot()");

        // needs to have the parkingSpot id
        // spotId is currently hardcoded, replace with passed spotId

        // needs to query the parkingSpot
        Log.i(TAG, "spotID = "+spotId);
        q = FirebaseDatabase.getInstance().getReference("parkingSpots/"+spotId);
        listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot shot) {

                // test logs

                Log.i(TAG, "onDataChange()");
                Log.i(TAG, "onDataChange(): "+shot.toString());

                if(shot.getValue() != null && spotId != "") {

                    // creates address object

                    DataSnapshot a = shot.child("address");
                    address addy = new address(a.child("street").getValue().toString(),
                            a.child("state").getValue().toString(),
                            a.child("city").getValue().toString(),
                            a.child("zip").getValue().toString());

                    //creates dateTime object

                    a = shot.child("startDate");
                    dateTime startDate = new dateTime(Integer.valueOf(a.child("year")
                            .getValue().toString()),
                            Integer.valueOf(a.child("month").getValue().toString()),
                            Integer.valueOf(a.child("day").getValue().toString()),
                            Integer.valueOf(a.child("hour").getValue().toString()),
                            a.child("meridiem").getValue().toString());

                    //creates dateTime object

                    a = shot.child("endDate");
                    dateTime endDate = new dateTime(Integer.valueOf(a.child("year")
                            .getValue().toString()),
                            Integer.valueOf(a.child("month").getValue().toString()),
                            Integer.valueOf(a.child("day").getValue().toString()),
                            Integer.valueOf(a.child("hour").getValue().toString()),
                            a.child("meridiem").getValue().toString());

                    //creates parkingSpot object

                    String renter;
                    if(shot.child("renter").getValue() == null) {
                        renter = null;
                    } else {
                        renter = shot.child("renter").getValue().toString();
                    }
                    parkingSpot spt = new parkingSpot(startDate, endDate, addy,
                            shot.child("price").getValue().toString(),
                            shot.child("instructions").getValue().toString(),
                            shot.child("lister").getValue().toString(),
                            renter,
                            shot.child("key").getValue().toString(),
                            shot.child("thumbnail").getValue().toString(),
                            shot.child("image").getValue().toString());

                    // download image

                    String imageFileName = storage.createName();
                    Log.i(TAG, "fileName: "+imageFileName);
                    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    try {
                        image = File.createTempFile(
                                imageFileName,  /* prefix */
                                ".jpg",         /* suffix */
                                storageDir      /* directory */
                        );
                    } catch(IOException ex) {
                        Log.i(TAG, "File creation error");
                        utility.toast(getApplicationContext(), "File creation Error");
                    }
                    if(image != null) {
                        FirebaseStorage.getInstance().getReference(shot.child("image").getValue().toString())
                                .getFile(image).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Log.i(TAG, "image download successful");
                                setImage();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.i(TAG, "image download unsuccessful");
                                utility.toast(getApplicationContext(), "Error: couldn't load image");
                            }
                        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                updateImageProgress(progress);
                                Log.i(TAG, "uploadProgress: "+String.valueOf(progress));
                            }
                        });
                    }

//                    final long TEN_MEGABYTE = 10 * 1024 * 1024;
//                    FirebaseStorage.getInstance().getReference(shot.child("image").getValue().toString())
//                            .getBytes(TEN_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
//                            // Data for "images/island.jpg" is returns, use this as needed
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            // Handle any errors
//                        }
//                    });

                    // fill spot

                    spot = spt;
                    fullAddress = findViewById(R.id.fullAddress);
                    fullAddress.setText(spot.getAddress().getAddress());
                    priceActual = findViewById(R.id.priceActual);
                    priceActual.setText(spot.getPrice());
                    listingInstructionsVPA = findViewById(R.id.listingInstructionsVPA);
                    listingInstructionsVPA.setText(spot.getInstructions());
                    startTime = findViewById(R.id.listingStartTime);
                    startTime.setText(spot.getStartDate().getTime());
                    endTime = findViewById(R.id.listingEndTime);
                    endTime.setText(spot.getEndDate().getTime());

                } else {

                    spot = null;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "onCancelled()");
            }
        };
            // use logs to make sure parkingSpot query was successful
            // fill global variable with parkingSpot
        q.addListenerForSingleValueEvent(listener);
    }

    /*
     * Firebase Linking Function: reserveSpot()
     * Author: Alec Felt
     *
     * Purpose: links the parkingSpot to the user's reservations object,
     *          links the user to the parkingSpot's renter field
     *
     * @return: void
     * TODO: write the code
     */
    private void reserveSpot() {
        Log.i(TAG, "reserveSpot()");
        // add the parkingSpot's id to the user's reservations object

        if(spotId != "") {
            database.reserveSpot(spotId);
        }
        navigation.navToHome(this);

        // add the user's uid to the parkingSpot's renter field
    }

    private void updateImageProgress(double p) {
        imageProgressBar.setProgress((int) p);
    }

}
