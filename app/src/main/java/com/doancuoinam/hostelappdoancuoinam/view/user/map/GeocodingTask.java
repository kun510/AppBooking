package com.doancuoinam.hostelappdoancuoinam.view.user.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingTask extends AsyncTask<String, Void, Address> {
    private Context context;
    private GeocodingListener listener;
    public GeocodingTask(Context context, GeocodingListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Address doInBackground(String... strings) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String addressString = strings[0];

        try {
            List<Address> addresses = geocoder.getFromLocationName(addressString, 1);
            if (addresses != null && addresses.size() > 0) {
                return addresses.get(0);
            }
        } catch (IOException e) {
            Log.e("GeocodingTask", "Error obtaining location from address", e);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Address address) {
        if (address != null) {
            listener.onGeocodingResult(address.getLatitude(), address.getLongitude());
        } else {
            listener.onGeocodingFailed();
        }
    }


    public interface GeocodingListener {
        void onGeocodingResult(double latitude, double longitude);

        void onGeocodingFailed();
    }
}
