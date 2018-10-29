package com.career.pathshala.api_call;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.career.pathshala.setting.ConnectionDetector;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CommonFunctions {
    public static final GlobalConstants gc = new GlobalConstants();
    private static Context context;
    public static MySharedPereference myPreference = MySharedPereference.getInstance();
    public UrlList urlList;
    public AddressFromLatLong addressfrmltlg;
    public ConnectionDetector cdr;


    public CommonFunctions(Context mcontext) {
        this.context = mcontext;
        addressfrmltlg = new AddressFromLatLong(context);
        cdr = new ConnectionDetector(context);

        final String isAcDEleted = myPreference.getString(mcontext, gc.ACCOUNT_ISDELETED);
        if (isAcDEleted.equals("1")) {
            // Signout();
        }
    }

    public static String service_response() {
        return myPreference.getString(context, gc.RESPOSE_CLG_JB_SERVICE);
    }

    public void hideKeyboard(View view) {
        try {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception Ex) {
        }
    }

    public String device_id() {
        String deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        // Toast.makeText(this, deviceId, Toast.LENGTH_SHORT).show();
        return deviceId;
    }

    public final String doubleToString(final double dbl_vlue) {
        String string_value = "";
        try {
            string_value = String.valueOf(dbl_vlue);
        } catch (Exception Ex) {
        }
        return string_value;
    }

    public boolean isLtLgNtEmpty(String Lat, String Long) {
        if (Lat.equals("") || Lat.equals("0") || Lat.equals("0.0") || Lat.equals(null) || Lat == null || Lat.isEmpty() || Long.equals("") || Long.equals("0") || Long.equals(null) || Long.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public Map<String, String> empty_() {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("om's", "om's");
        return params;
    }


    public Map<String, String> LogIn(String email, String password) {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("mobile", email);
        params.put("password", password);
        // params.put("device_token", "device_token");
        JSONObject obj = new JSONObject(params);
        Log.d("Login", obj.toString());
        return params;

    }

    public Map<String, String> Register(String username, String useremail, String usermobile, String userpassword, String userconfirm_pass, String Firebasetoken) {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("fullname", username);
        params.put("email", useremail);
        params.put("mobile", usermobile);
        params.put("password", userpassword);
        params.put("userconfirm_pass", userconfirm_pass);
        params.put("countrycode", "+91");
        params.put("device_token", Firebasetoken);
        JSONObject obj = new JSONObject(params);
        Log.d("Register", obj.toString());
        return params;
    }

    public Map<String, String> sendalert(String userid, int eventtypeid, double latitude, double longitude, String address) {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid", userid);
        params.put("eventtypeid", String.valueOf(eventtypeid));
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));
        params.put("address", "noida sector 64");
        JSONObject obj = new JSONObject(params);
        Log.d("Register", obj.toString());
        return params;
    }


    public Map<String, String> userlocation(String userid, double latitude, double longitude, String address) {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid", userid);
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));
        params.put("address", "noida sector 64");
        JSONObject obj = new JSONObject(params);
        Log.d("Register", obj.toString());
        return params;
    }


    public Map<String, String> acceptrequest(String userid, double latitude, double longitude, String address, String eventsummarydetailid) {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid", userid);
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));
        params.put("address", "noida sector 64");
        params.put("eventsummarydetailid", eventsummarydetailid);
        JSONObject obj = new JSONObject(params);
        Log.d("Register", obj.toString());
        return params;
    }


    public Map<String, String> rejectrequest(String userid, double latitude, double longitude, String address, String eventsummarydetailid) {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid", userid);
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));
        params.put("address", "noida sector 64");
        params.put("eventsummarydetailid", eventsummarydetailid);
        JSONObject obj = new JSONObject(params);
        Log.d("Register", obj.toString());
        return params;
    }


    public Map<String, String> sosbyme(String skip) {
        final HashMap<String, String> params = new HashMap<String, String>();

        JSONObject obj = new JSONObject(params);
        return params;
    }

    public Map<String, String> Questions(String test_type) {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid", test_type);
        JSONObject obj = new JSONObject(params);
        Log.d("Register", obj.toString());
        return params;
    }


}
