package com.example.shaheed.andelachallengeproject;

/**
 * Created by shaheed on 10/4/17.
 */
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<ResponseModel> {

    private static final String TAG = "CustomListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    //Holds variables in a View
    private static class ViewHolder {
        TextView txtUSD;
        TextView txtEUR;
    }

    public MyListAdapter(Context context, int resource, ArrayList<ResponseModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the persons information
        float EUR = getItem(position).getEUR();
        float USD = getItem(position).getUSD();

        try{

            //create the view result for showing the animation
            final View result;

            //ViewHolder object
            ViewHolder holder;

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mResource, parent, false);
                holder= new ViewHolder();
                holder.txtEUR = (TextView) convertView.findViewById(R.id.cardTitle);
                holder.txtUSD = (TextView) convertView.findViewById(R.id.cardImage);

                result = convertView;

                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            lastPosition = position;

            holder.txtUSD.setText(String.valueOf(USD));
            holder.txtEUR.setText(String.valueOf(EUR));

            return convertView;
        }catch (IllegalArgumentException e){
            Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage() );
            return convertView;
        }

    }
}