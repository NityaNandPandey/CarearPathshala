package com.career.pathshala.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.career.pathshala.api_call.CallWebService;
import com.career.pathshala.api_call.CommonFunctions;
import com.career.pathshala.api_call.MyServiceListener;
import com.career.pathshala.api_call.UrlList;


public abstract class BaseActivity extends AppCompatActivity implements MyServiceListener {

    private static final String TAG = "FieldUtils";
    public static ConnectionDetector cdr;
    public static CommonFunctions commonFunctions;
    public static CallWebService myservice;
    public static UrlList urlList;

    public static void JsonData(String Jsondata) {
        System.out.println(Jsondata);
        return;
    }

    public static void Tst(Context ctx, String msg) {
        // Call Toast to show msg
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    public static void log_(String head, String msg) {
        Log.d(head, "---->" + msg);
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_NO_TITL
        super.onCreate(savedInstanceState);
        // preference = getApplicationContext().getSharedPreferences("SHMOOOPS", MODE_PRIVATE);
        cdr = new ConnectionDetector(this);
        commonFunctions = new CommonFunctions(this);

    }

    public boolean isInternetAvailable() {
        return cdr.isConnectingToInternet();
        // return false
    }

    public void GotoNextActivityClearTop(Activity currentActivity, Class nextActivity) {
        Intent intent = new Intent(currentActivity, nextActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void Tst_Snake(View view, String msg) {
        /*Snackbar.make(view, Html.fromHtml("<font color=\"#8c1212\">"+msg+"</font>"), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
        try {
            Snackbar snackbar = Snackbar.make(view, msg,
                    Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(Color.parseColor("#8c1212"));

            snackbar.show();
        } catch (Exception ex) {
            Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccess(String string) {

    }

    public String device_id() {
        String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        // Toast.makeText(this, deviceId, Toast.LENGTH_SHORT).show();
        return deviceId;
    }


    @Override
    public void onFailed() {

    }


    @Override
    public void onBackPressed() {        // code here to show dialog
        // super.onBackPressed();  // optional depending on your needs
    }

}
