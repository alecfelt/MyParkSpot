package com.cs121.myparkspot;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * Created by alecfelt on 11/23/17.
 */

/*
 * Storage Object: class storage
 * Author: Alec Felt
 *
 * Purpose: Provide static firebase storage functions to the project scope
 *
 * @param: none
 * @return: none
 * TODO: none
 */
public class storage {

    private static final String TAG = "storage: ";

    /*
     * Storage Function: uploadThumbnail()
     * Author: Alec Felt
     *
     * Purpose: Provide static Storage function to upload a thumbnail Bitmap,
     *          Returns the name of the file
     *
     * @param: Context c, Bitmap b
     * @return: String
     * TODO: none
     */
    static public String uploadThumbnail(final Context c, Bitmap bitmap) {
        Log.i(TAG, "uploadThumbnail()");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        final String thumbnailName = createName();
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
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                utility.toast(c, "Thumbnail Upload successful!");
            }
        });

        return thumbnailName;
    }

    /*
     * Storage Function: uploadImage()
     * Author: Alec Felt
     *
     * Purpose: Provide static Storage function to upload a Uri,
     *          Returns the name of the file
     *
     * @param: Context c, Uri u
     * @return: String
     * TODO: none
     */
    static public String uploadImage(final Context c, Uri u) {
        Log.i(TAG, "uploadImage()");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        final String imageName = createName();
        UploadTask uploadTask = storageRef.child(imageName).putFile(u);

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.i(TAG, "uploadProgress: "+String.valueOf(progress));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                utility.toast(c, "Error: Image Upload failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                utility.toast(c, "Image Upload successful!");
            }
        });

        return imageName;
    }

    /*
     * Storage Utility Functions: createImageFile(),
     *                            createName(),
     * Author: Alec Felt
     *
     * Purpose: Provide utility functions that create random names,
     *          and externally stored files for downloading images
     *
     * @param: none
     * @return: none
     * TODO: implement complete logic for createImageFile
     */
    static public File createImageFile(Context c) throws IOException {
        Log.i(TAG, "createImageFile()");
        String imageFileName = createName();
        File storageDir = c.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }
    static public String createName() {
        Log.i(TAG, "createName()");
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        return imageFileName;
    }

}
