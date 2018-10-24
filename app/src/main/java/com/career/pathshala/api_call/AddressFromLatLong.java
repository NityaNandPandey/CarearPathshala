package com.career.pathshala.api_call;

import android.content.Context;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by omprakash.m on 1/23/2017.
 */

public class AddressFromLatLong {

    private static String Address, City, State, Country;
    private Context context;

    public AddressFromLatLong(Context context) {
        this.context = context;
    }

    public void SaveLocationData(final double latitude, final double longitude) {
        /*latitude=6.8960833;
                longitude  =79.8901878;*/
     /*   latitude=28.673858;
        longitude=77.3583886;*/
        Address = "";
        City = "";
        State = "";
        Country = "";
        final Geocoder geocoder = new Geocoder(this.context, Locale.getDefault());
        try {

            final List<android.location.Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                android.location.Address address = addressList.get(0);

                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    Address += (address.getAddressLine(i)) + (",");
                }
                try {
                    Address = Address.substring(0, Address.length() - 1);
                    City = address.getLocality();
                    if (City == null) {
                        City = "";
                    }
                    State = address.getAdminArea();
                    if (State == null) {
                        State = "";
                    }
                    Country = address.getCountryName();
                    if (Country == null) {
                        Country = "";
                    }
                } catch (Exception ex) {
                    Address = addressList.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {


        }
    }

    public String getAddress() {
        return Address;
    }

    public String getCity() {
        return City;

    }

    public String getState() {
        return State;
    }

    public String getCountry() {
        return Country;
    }

    //----------------------------check gps status---------

}
