package com.cs121.myparkspot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class ParkingSpotActivity extends AppCompatActivity implements View.OnClickListener {

    /*
     * Globals: Global Variables
     * Author: Alec Felt
     *
     * Purpose: Variables in the global scope
     *
     * @param: String
     * @return: int
     * TODO: none
     */

    EditText street,state,city,zip,price,instr;
    Spinner startHour,startDay,startMonth,startYear,startMeridiem,endHour,
            endDay,endMonth,endYear,endMeridiem;
<<<<<<< HEAD
    Button create, imageCapture, thumbnailCapture;
    ProgressBar progressThumbnail, progressImage;
=======
    Button create;
>>>>>>> small changes with string and formtatting stuff.
    parkingSpot spot;
    static final int REQUEST_IMAGE_CAPTURE = 1, REQUEST_THUMBNAIL_CAPTURE = 2;
    String imageName, thumbnailName;
    Bitmap thumbnail;
    File image;
    static String LOG = "ParkingSpotActivity: ";
    private String mCurrentPhotoPath;

    /*
     * Activity Lifecycle Functions: onCreate(Bundle)
     * Author: Alec Felt
     *
     * Purpose: Set activity View, find Views, populate spinners
     *
     * @param: Bundle
     * @return: none
     * TODO: none
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG, "onCreate()");
        setContentView(R.layout.activity_parking_spot);
<<<<<<< HEAD

=======
>>>>>>> small changes with string and formtatting stuff.
        street=findViewById(R.id.street);
        state=findViewById(R.id.state);
        city=findViewById(R.id.city);
        zip=findViewById(R.id.zip);
        create=findViewById(R.id.spot_button);
<<<<<<< HEAD

        startHour=findViewById(R.id.start_hour);
        startDay=findViewById(R.id.start_day);
        startMeridiem=findViewById(R.id.start_meridiem);
        thumbnailCapture=findViewById(R.id.thumbnail_capture);
        startMonth=findViewById(R.id.start_month);
        startYear=findViewById(R.id.start_year);
        endMeridiem=findViewById(R.id.end_meridiem);
        imageCapture=findViewById(R.id.image_capture);
=======
        startHour=findViewById(R.id.start_hour);
        startDay=findViewById(R.id.start_day);
        startMeridiem=findViewById(R.id.start_meridiem);
        startMonth=findViewById(R.id.start_month);
        startYear=findViewById(R.id.start_year);
        endMeridiem=findViewById(R.id.end_meridiem);
>>>>>>> small changes with string and formtatting stuff.
        endDay=findViewById(R.id.end_day);
        endMonth=findViewById(R.id.end_month);
        endYear=findViewById(R.id.end_year);
        endHour=findViewById(R.id.end_hour);
        create.setOnClickListener(this);
<<<<<<< HEAD
        imageCapture.setOnClickListener(this);
        thumbnailCapture.setOnClickListener(this);
        imageName = null;
        thumbnailName = null;
        thumbnail = null;
        image = null;
        price=findViewById(R.id.price);
        instr=findViewById(R.id.instructions);
        progressImage = findViewById(R.id.progress_bar_2);
        progressThumbnail = findViewById(R.id.progress_bar_1);

=======
        price=findViewById(R.id.price);
        instr=findViewById(R.id.instructions);
>>>>>>> small changes with string and formtatting stuff.
        //
        startHour = findViewById(R.id.start_hour);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hour_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startHour.setAdapter(adapter);
        //
        endHour = findViewById(R.id.end_hour);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.hour_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endHour.setAdapter(adapter);
        //
        startYear = findViewById(R.id.start_year);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startYear.setAdapter(adapter);
        //
        endYear = findViewById(R.id.end_year);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endYear.setAdapter(adapter);
        //
        startMonth = findViewById(R.id.start_month);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startMonth.setAdapter(adapter);
        //
        endMonth = findViewById(R.id.end_month);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endMonth.setAdapter(adapter);
        //
        startDay = findViewById(R.id.start_day);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.day_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startDay.setAdapter(adapter);
        //
        endDay = findViewById(R.id.end_day);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.day_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endDay.setAdapter(adapter);
        //
        startMeridiem = findViewById(R.id.start_meridiem);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.meridiem_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startMeridiem.setAdapter(adapter);
        //
        endMeridiem = findViewById(R.id.end_meridiem);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.meridiem_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endMeridiem.setAdapter(adapter);

        mCurrentPhotoPath = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(image != null) { image.delete(); }
    }

    /*
     * Text Collection: processText()
     * Author: Alec Felt
     *
     * Purpose: pulls user text input, creates a parkingSpot object
     *
     * @param: none
     * @return: boolean
     * TODO: none
     */

    public boolean processText() {
<<<<<<< HEAD
        Log.i(LOG, "processText()");
        String str=street.getText().toString(),
                sta=state.getText().toString(),
                c=city.getText().toString(),
                z=zip.getText().toString(),
                p=price.getText().toString(),
                i=instr.getText().toString();

        if(Objects.equals(str,"")||Objects.equals(sta,"")||Objects.equals(c,"")||
                Objects.equals(z,"")||Objects.equals(p,"")||Objects.equals(i,"")) {
            utility.toast(this, "Error: Please input all info.");
            return false;
        }
        if(thumbnailName==null || imageName==null) {
            utility.toast(this, "Error: Please upload image for this Parking Spot");
            return false;
        }

=======
        String str=street.getText().toString(),sta=state.getText().toString(),
                c=city.getText().toString(),z=zip.getText().toString(),
                p=price.getText().toString(),i=instr.getText().toString();
        if(Objects.equals(str,"")||Objects.equals(sta,"")||Objects.equals(c,"")||
                Objects.equals(z,"")||Objects.equals(p,"")||Objects.equals(i,"")) {
            utility.toast(this, "Bad Address.");
            return false;
        }
>>>>>>> small changes with string and formtatting stuff.
        address addy = new address(str, sta, c, z);
        String sMer=startMeridiem.getSelectedItem().toString(),
                eMer=endMeridiem.getSelectedItem().toString();
        String sY=startYear.getSelectedItem().toString(),
                eY=endYear.getSelectedItem().toString(),sM=startMonth.getSelectedItem().toString();
        String eM=endMonth.getSelectedItem().toString(),
                sD=startDay.getSelectedItem().toString(),eD=endDay.getSelectedItem().toString();
        String sH=startHour.getSelectedItem().toString(),eH=endHour.getSelectedItem().toString();
<<<<<<< HEAD

=======
>>>>>>> small changes with string and formtatting stuff.
        dateTime startTime = new dateTime(Integer.valueOf(sY),
                utility.mon(sM), Integer.valueOf(sD),
                Integer.valueOf(sH.replace(":00", "")), sMer);
        dateTime endTime = new dateTime(Integer.valueOf(eY),
                utility.mon(eM), Integer.valueOf(eD),
                Integer.valueOf(eH.replace(":00", "")), eMer);
<<<<<<< HEAD
        String lister = FirebaseAuth.getInstance().getUid();
        String renter = null;
        String key = "";
        spot = new parkingSpot(startTime, endTime, addy, p, i,
                lister, renter, key, thumbnailName, imageName);

=======
        spot = new parkingSpot(startTime, endTime, addy, p, i, FirebaseAuth.getInstance().getUid());
>>>>>>> small changes with string and formtatting stuff.
        return true;
    }

    /*
     * Click Listener Function: onClick(View)
     * Author: Alec Felt
     *
     * Purpose: Check which View was clicked:
     *              check text/picture inputs, push parkingSpot to database,
     *              navigate to HomeActivity
     *              OR take picture
     *              OR upload picture from device files
     *
     * @param: View
     * @return: void
     * TODO: implement imageCapture and imageUpload functionality
     */

    @Override
    public void onClick(View v) {
        Log.i(LOG, "onClick()");
        if(v == create) {
            if (processText()) {
                spot.setThumbnail(thumbnailName);
                spot.setImage(imageName);
                database.pushSpot(this, spot);
                navigation.navToHome(this);
            }
        } else if(v == thumbnailCapture) {
            dispatchTakeThumbnailIntent();
        } else if(v == imageCapture) {
            dispatchTakePictureIntent();
        }
    }

    /*
     * Camera Capture function: dispatchTakePictureIntent()
     * Author: Alec Felt
     *
     * Purpose: Start camera activity for image capture,
     *          Create file stored in device files,
     *
     * @param: void
     * @return: void
     * TODO: none
     */
    private void dispatchTakePictureIntent() {
        Log.i(LOG, "dispatchTakePictureIntent()");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            File photoFile = null;
            try {
                photoFile = storage.createImageFile(this);
                mCurrentPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                utility.toast(this, "Error with Android's External Storage Directory");
                Log.i(LOG, ex.getMessage());
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.cs121.myparkspot.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    /*
     * Camera Capture function: dispatchTakeThumbnailIntent()
     * Author: Alec Felt
     *
     * Purpose: Start camera activity for image capture
     *
     * @param: void
     * @return: void
     * TODO: none
     */
    private void dispatchTakeThumbnailIntent() {
        Log.i(LOG, "dispatchTakeThumbnailIntent()");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_THUMBNAIL_CAPTURE);
        }
    }

    /*
     * Camera Result function: onActivityResult()
     * Author: Alec Felt
     *
     * Purpose: Deal with the result of the Camera Activity,
     *          Upload thumbnail or image to FirebaseStorage
     *
     * @param: int requestCode, int resultCode, Intent data
     * @return: void
     * TODO: none
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(LOG, "onActivityResult()");
        if(resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Log.i(LOG, "REQUEST_IMAGE_CAPTURE");
                // get the URI of the file
                image = new File(mCurrentPhotoPath);
                Uri contentUri = Uri.fromFile(image);
                // upload image
                imageName = uploadImage(this, contentUri);
            } else if(requestCode == REQUEST_THUMBNAIL_CAPTURE) {
                Log.i(LOG, "REQUEST_THUMBNAIL_CAPTURE");
                // get Bitmap of thumbnail
                Bundle extras = data.getExtras();
                thumbnail = (Bitmap) extras.get("data");
                // upload thumbnail
                thumbnailName = uploadThumbnail(this, thumbnail);
            }
        } else {
            Log.i(LOG, "Image/Thumbnail Capture failure");
            utility.toast(this, "Image Capture failure");
        }
    }

    private String uploadImage(final Context c, Uri u) {
        Log.i(LOG, "uploadImage()");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        final String imageName = storage.createName();
        UploadTask uploadTask = storageRef.child(imageName).putFile(u);

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                updateImageProgress(progress);
                Log.i(LOG, "uploadProgress: "+String.valueOf(progress));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                utility.toast(c.getApplicationContext(), "Error: Image Upload failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                utility.toast(c.getApplicationContext(), "Image Upload successful!");
            }
        });

        return imageName;
    }

    private String uploadThumbnail(final Context c, Bitmap bitmap) {
        Log.i(LOG, "uploadThumbnail()");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        final String thumbnailName = storage.createName();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.child(thumbnailName).putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                utility.toast(c, "Error: Thumbnail Upload failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                utility.toast(c, "Thumbnail Upload successful!");
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                updateThumbnailProgress(progress);
                Log.i(LOG, "uploadProgress: "+String.valueOf(progress));

            }
        });

        return thumbnailName;
    }

    private void updateImageProgress(double p) {
        progressImage.setProgress((int) p);
    }

    private void updateThumbnailProgress(double p) {
        progressThumbnail.setProgress((int) p);
    }

}
