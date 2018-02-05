package com.cs121.myparkspot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/* <Class> ListRowArrayAdapter
 * Author: Zachary Olson
 * Purpose: Adapts the parkingSpot object from the database to a item View.
 */
public class ListRowArrayAdapter extends BaseAdapter {

    private Context context;
    private dateTime startDateTime;
    private dateTime endDateTime;
    private ArrayList<parkingSpot> pSpotListings;
    private ArrayList<parkingSpot> searchParkingSpots;
    private LayoutInflater inflater;
    private static final String TAG = "ListRowArrayAdapter: ";

    /* <Constructor> ListRowArrayAdapter
     * Author: Zachary Olson
     * Purpose: Creates the ListRowArrayAdapter object.
     * Parameters: Context, ArrayList<parkingSpot>
     * Return: ListRowArrayAdapter Object
     */
    public ListRowArrayAdapter(@NonNull Context context, ArrayList<parkingSpot> pSpotListings) {
        Log.i(TAG, "ListRowArrayAdapter()");
        this.context = context;
        this.pSpotListings = new ArrayList<parkingSpot>();
        if (this.pSpotListings.addAll(pSpotListings)) {
            //Toast.makeText(context, "addAll for pSpotListings", Toast.LENGTH_LONG).show();
        }
        this.searchParkingSpots = new ArrayList<parkingSpot>();
        if (this.searchParkingSpots.addAll(pSpotListings)) {
            //Toast.makeText(context, "addAll for searchParkingSpots", Toast.LENGTH_LONG).show();
        }
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount()");
        return pSpotListings.size();
    }

    @Override
    public Object getItem(int position) {
        Log.i(TAG, "getItem()");
        return pSpotListings.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.i(TAG, "getItemId()");
        return position;
    }

    /* <Function> getView
     * Author: Zachary Olson
     * Purpose: Used to return the necessary view for the ListView fragment, returns the view
     *          contained in ViewHolder (Android preferred and "safe" practice.
     * Parameters: Int Position, View, ViewGroup
     * Return: parkingSpot listing View for the ListView Fragment.
     */
    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        Log.i(TAG, "getView()");
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.parking_spot_listing_item, null);
            viewHolder = new ViewHolder();
            viewHolder.location = (TextView) convertView.findViewById(R.id.location);
            viewHolder.datesAvailable = (TextView) convertView.findViewById(R.id.datesAvailable);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            //viewHolder.reserveButton = (Button) convertView.findViewById(R.id.reserveButton);
            //viewHolder.viewProfile = (Button) convertView.findViewById(R.id.viewProfile);
            viewHolder.parkingSpotImage = (ImageView) convertView
                    .findViewById(R.id.parkingSpotImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        startDateTime = pSpotListings.get(position).getStartDate();
        endDateTime = pSpotListings.get(position).getEndDate();


        int year1 = startDateTime.getYear();
        int year2 = endDateTime.getYear();
        int month1 = startDateTime.getMonth();
        int month2 = endDateTime.getMonth();
        int day1 = startDateTime.getDay();
        int day2 = endDateTime.getDay();
        int hour1 = startDateTime.getHour();
        int hour2 = endDateTime.getHour();
        String meridiem1 = startDateTime.getMeridiem();
        String meridiem2 = endDateTime.getMeridiem();

        String startDate = String.valueOf(month1) + "/" + String.valueOf(day1) +
                "/" + String.valueOf(year1);
        String endDate = String.valueOf(month2) + "/" + String.valueOf(day2) +
                "/" + String.valueOf(year2);
        String timeRange = String.valueOf(hour1) + ":00" + meridiem1 + " - " +
                String.valueOf(hour2) + ":00" + meridiem2;


        String timeAvailable = startDate + " - " + endDate + ":  [ " + timeRange + " ]";
        String price = "$" + pSpotListings.get(position).getPrice();
        String thumbnailName = pSpotListings.get(position).getThumbnail();

        viewHolder.location.setText(pSpotListings.get(position).getAddress().getAddress());
        viewHolder.datesAvailable.setText(timeAvailable);
        viewHolder.price.setText(price);
//        viewHolder.reserveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(context, "you clicked reserveButton", Toast.LENGTH_LONG).show();
//
//            }
//        });

        /*
        viewHolder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "you clicked viewProfile", Toast.LENGTH_LONG).show();
            }
        });
        */

        getThumbnail(thumbnailName, viewHolder);
        return convertView;
    }

    /*
     * Firebase/Adapter Function: getThumbnail()
     * Author: Alec Felt
     *
     * Purpose: Querys Firebase Storage for the parkingSpots thumbnail,
     *          Scales the bitmap to the size of the ImageView,
     *          Applies the bitmap to the ImageView
     *
     *  @param: View
     * @return: void
     * TODO: implement imageCapture and imageUpload functionality
     */
    private void getThumbnail(String thumbnailName, final ViewHolder v) {
        Log.i(TAG, "getThumbnail()");
        StorageReference ref = FirebaseStorage.getInstance().getReference().child(thumbnailName);

        final long ONE_MEGABYTE = 1024 * 96;
        final Context c = this.context;
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                int targetW = v.parkingSpotImage.getWidth();
                int targetH = v.parkingSpotImage.getHeight();
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;
                Log.i(TAG, "TargetH: " + targetH);
                Log.i(TAG, "TargetW: " + targetW);
//                int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
                bmOptions.inJustDecodeBounds = false;
//                bmOptions.inSampleSize = scaleFactor;

                Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmOptions);
                v.parkingSpotImage.setImageBitmap(b);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                utility.toast(c, "Error: thumbnail download unsuccessful");
            }
        });
    }


    /* <Class> ViewHolder
     * Author: Zachary Olson
     * Purpose: Holds desired view, runs through conditional and then if necessary, recreates
     *      view with this.
     */
    static class ViewHolder {
        TextView location;
        TextView datesAvailable;
        TextView price;
        ImageView parkingSpotImage;
    }


    /* <Function> filterData
     * Author: Sanjeet, modified by Zack
     * Purpose: Takes in the String Query from search bar, filters if needed
     * Parameters: String query
     * Return: Nothing.
     */
    public void filterData(String query){
        Log.i(TAG, "filterData()");
        query = query.toLowerCase();
        pSpotListings.clear();
        boolean dualSearch = false;
        String first = " ";
        String second = " ";

        /*if(query.contains(";")){
            //dualSearch = true;
            String segments[] = query.split(";");
            if(segments.length > 1) {
                dualSearch = true;
                first = segments[0];
                second = segments[1];
                Log.i(TAG, "first part equals: " + first);
                Log.i(TAG, "second part equals: " + second);
            }
        }*/

        if(query.isEmpty()){
            if (pSpotListings.addAll(searchParkingSpots)) {
                //Toast.makeText(context, "pSpotListings.addAll(searchParkingSpots)",
                // Toast.LENGTH_LONG).show();
            }
        }else{
            for (parkingSpot pSpot : searchParkingSpots){
                ArrayList<parkingSpot> newList = new ArrayList<parkingSpot>();

                if (pSpot.getAddress().getCity().toLowerCase().contains(query)) {
                    newList.add(pSpot);
                    Log.i(TAG, "checking address");
                }

                   /* if ((KeyValueDB.getCity(context) && KeyValueDB.getPrice(context)) && dualSearch) {
                        if ((pSpot.getAddress().getCity().toLowerCase().contains(first) && pSpot.getPrice().toLowerCase().contains(second))
                                || (pSpot.getAddress().getCity().toLowerCase().contains(second) && pSpot.getPrice().toLowerCase().contains(first))) {
                            newList.add(pSpot);
                            Log.i(TAG, "checking city and price:dualSearch");

                        }
                    } else if (KeyValueDB.getCity(context) && dualSearch) {
                        Log.i(TAG,"Check dualSearch");
                        if ((pSpot.getAddress().getStreet().toLowerCase().contains(first) && pSpot.getAddress().getCity().toLowerCase().contains(second))
                                || (pSpot.getAddress().getStreet().toLowerCase().contains(second) && pSpot.getAddress().getCity().toLowerCase().contains(first)) ) {
                            newList.add(pSpot);
                            Log.i(TAG, "checking address and city:dualSearch");

                        }
                    } else if (KeyValueDB.getPrice(context) && dualSearch) {
                        if ((pSpot.getAddress().getStreet().toLowerCase().contains(first) && pSpot.getPrice().toLowerCase().contains(second))
                                || (pSpot.getAddress().getStreet().toLowerCase().contains(second) && pSpot.getPrice().toLowerCase().contains(first))) {
                            newList.add(pSpot);
                            Log.i(TAG, "checking address and price:dualSearch");

                        }
                    }  else if (KeyValueDB.getCity(context) && KeyValueDB.getPrice(context)) {
                        if ((pSpot.getAddress().getStreet().toLowerCase().contains(query) || pSpot.getAddress().getCity().toLowerCase().contains(query))
                                || pSpot.getPrice().toLowerCase().contains(query)) {
                            newList.add(pSpot);
                            Log.i(TAG, "checking address,city, and price");

                        }
                    } else if (KeyValueDB.getCity(context)) {
                        if (pSpot.getAddress().getStreet().toLowerCase().contains(query) || pSpot.getAddress().getCity().toLowerCase().contains(query)) {
                            newList.add(pSpot);
                            Log.i(TAG, "checking address and city");

                        }
                    } else if (KeyValueDB.getPrice(context)) {
                        if (pSpot.getAddress().getStreet().toLowerCase().contains(query) || pSpot.getPrice().contains(query)) {
                            newList.add(pSpot);
                            Log.i(TAG, "checking address and price");

                        }
                    } else {
                        Log.i(TAG, "Default");
                        if (pSpot.getAddress().getStreet().toLowerCase().contains(query)) {
                            newList.add(pSpot);
                            Log.i(TAG, "checking address");
                        }
                    }*/

                if (newList.size() > 0){
                    parkingSpot newParkSpot = new parkingSpot(
                            pSpot.getStartDate(),
                            pSpot.getEndDate(),
                            pSpot.getAddress(),
                            pSpot.getPrice(),
                            pSpot.getInstructions(),
                            pSpot.getLister(),
                            pSpot.getRenter(),
                            pSpot.getKey(),
                            pSpot.getThumbnail(),
                            pSpot.getImage());
                    pSpotListings.add(newParkSpot);
                    //Toast.makeText(context, "added newParkSpot to pSpotListings",
                    // Toast.LENGTH_LONG).show();
                }
            }
            if(KeyValueDB.getPriceMax(context)){
                Log.i(TAG,"Sort by Price Max");
                Collections.sort(pSpotListings, new Comparator<parkingSpot>() {
                    @Override
                    public int compare(parkingSpot o1, parkingSpot o2) {
                        int first = Integer.parseInt(o1.getPrice());
                        int second = Integer.parseInt(o2.getPrice());
                        return (first - second);
                        //return o1.getAddress().getCity().toLowerCase().compareTo(o2.getAddress().getCity().toLowerCase());
                    }
                });
            } else if(KeyValueDB.getPriceMin(context)){
                Log.i(TAG,"Sort by Price Min");
                Collections.sort(pSpotListings, new Comparator<parkingSpot>() {
                    @Override
                    public int compare(parkingSpot o1, parkingSpot o2) {
                        int first = Integer.parseInt(o1.getPrice());
                        int second = Integer.parseInt(o2.getPrice());
                        return (second - first);
                        //return o1.getAddress().getCity().toLowerCase().compareTo(o2.getAddress().getCity().toLowerCase());
                    }
                });
            } else {
                Log.i(TAG,"Sort by Alphabet");
                Collections.sort(pSpotListings, new Comparator<parkingSpot>() {
                    @Override
                    public int compare(parkingSpot o1, parkingSpot o2) {
                        return o1.getAddress().getCity().toLowerCase().compareTo(o2.getAddress().getCity().toLowerCase());
                    }
                });
            }

        }
        notifyDataSetChanged();
    }
}