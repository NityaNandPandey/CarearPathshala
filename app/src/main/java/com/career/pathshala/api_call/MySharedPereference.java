package com.career.pathshala.api_call;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Rupesh on 08-09-2016.
 */
public class MySharedPereference {

    public static MySharedPereference instance;
    public static final String APP_PREFERENCE = "Help";
    private MySharedPereference() {
    }

    public static MySharedPereference getInstance() {
        if (instance == null) {
            instance = new MySharedPereference();
        }
        return instance;
    }

    public void setString(Context context, String Key, String Value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Key, Value);
        editor.commit();
    }


    public String getString(Context context, String Key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        String requestToken = sharedPreferences.getString(Key, "");
        return requestToken;
    }

    public void setBoolean(Context context, String Key, boolean Value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Key, Value);
        editor.commit();
    }


    public boolean getBoolean(Context context, String Key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        boolean requestToken = sharedPreferences.getBoolean(Key, false);
        return requestToken;
    }

    public void setFloat(Context context, String Key, float Value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(Key, Value);
        editor.commit();
    }

    public float getFloat(Context context, String Key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        float requestToken = sharedPreferences.getFloat(Key, 0);
        return requestToken;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}
