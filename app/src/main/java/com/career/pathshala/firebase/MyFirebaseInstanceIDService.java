package com.career.pathshala.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.career.pathshala.api_call.CommonFunctions;
import com.career.pathshala.api_call.GlobalConstants;


//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private CommonFunctions commonFunctions;
    private static final String TAG = "MyFirebaseIIDService";
    public static String refreshedToken;

    @Override
    public void onTokenRefresh() {
        commonFunctions = new CommonFunctions(MyFirebaseInstanceIDService.this);
        //Getting registration token
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        commonFunctions.myPreference.setString(this, GlobalConstants.Firebasetoken, refreshedToken);
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}