package com.ucsc.zachary.fragtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by zachary on 11/7/17.
 */

public class ListRowArrayAdapter extends BaseAdapter {

    private Context context;
    private String[] values;
    private parkingSpot pSpot;
    private address addy;
    private dateTime startDateTime;
    private dateTime endDateTime;
    private ArrayList<parkingSpot> pSpotListings;
    private ArrayList<parkingSpot> searchParkingSpots;
    private LayoutInflater inflater;

    public ListRowArrayAdapter(@NonNull Context context, ArrayList<parkingSpot> pSpotListings) {
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
        //this.values = values;
        /*
        this.pSpot = pSpot;
        this.addy = pSpot.getAddress();
        this.startDateTime = pSpot.getStartDate();
        this.endDateTime = pSpot.getEndDate();
        */
    }

    @Override
    public int getCount() {
        return pSpotListings.size();
    }

    @Override
    public Object getItem(int position) {
        return pSpotListings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /* Overridden Function getView
     * Written by Zack
     * Used to return the necessary view for the ListView fragment, \
     * returns the view contained in ViewHolder (Android preferred  \
     * and "safe" practice
     */
    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.parking_spot_listing_item, null);
            viewHolder = new ViewHolder();
            viewHolder.location = (TextView) convertView.findViewById(R.id.location);
            viewHolder.datesAvailable = (TextView) convertView.findViewById(R.id.datesAvailable);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.reserveButton = (Button) convertView.findViewById(R.id.reserveButton);
            viewHolder.viewProfile = (Button) convertView.findViewById(R.id.viewProfile);
            viewHolder.parkingSpotImage = (ImageView) convertView.findViewById(R.id.parkingSpotImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        startDateTime = pSpotListings.get(position).getStartDate();
        endDateTime = pSpotListings.get(position).getEndDate();

        String timeAvailable = startDateTime.getTime() + endDateTime.getTime();
        String price = "$" + pSpotListings.get(position).getPrice();

        viewHolder.location.setText(pSpotListings.get(position).getAddress().getAddress());
        viewHolder.datesAvailable.setText(timeAvailable);
        viewHolder.price.setText(price);
        viewHolder.reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "you clicked reserveButton", Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "you clicked viewProfile", Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.parkingSpotImage.setImageResource(R.drawable.park_spot_icon);

        return convertView;
    }


    /* ViewHolder Class
     * Written by Zack
     * Holds desired view, runs through conditional and \
     * then if necessary, recreates view with this.
     */
    static class ViewHolder {
        TextView location;
        TextView datesAvailable;
        TextView price;
        Button reserveButton;
        Button viewProfile;
        ImageView parkingSpotImage;
    }


    /* Function filterData
     * Written by Sanjeet, modified by Zack
     * Takes in the String Query, used for Search Bar
     */
    public void filterData(String query){
        query = query.toLowerCase();
        pSpotListings.clear();

        if(query.isEmpty()){
            if (pSpotListings.addAll(searchParkingSpots)) {
                //Toast.makeText(context, "pSpotListings.addAll(searchParkingSpots)", Toast.LENGTH_LONG).show();
            }
        }else{
            for (parkingSpot pSpot : searchParkingSpots){
                ArrayList<parkingSpot> newList = new ArrayList<parkingSpot>();

                if (pSpot.getAddress().getStreet().toLowerCase().contains(query)){
                    newList.add(pSpot);
                    //Toast.makeText(context, "added pSpot to newList", Toast.LENGTH_LONG).show();
                }

                if (newList.size() > 0){
                    //Parking Spot Constructor (dateTime sd, dateTime ed, address a, String p, String i)
                    parkingSpot newParkSpot = new parkingSpot(pSpot.getStartDate(), pSpot.getEndDate(),
                            pSpot.getAddress(), pSpot.getPrice(), pSpot.getInstructions());
                    pSpotListings.add(newParkSpot);
                    //Toast.makeText(context, "added newParkSpot to pSpotListings", Toast.LENGTH_LONG).show();
                }
            }
        }
        notifyDataSetChanged();
    }
}
