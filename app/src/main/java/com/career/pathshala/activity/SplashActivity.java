package com.career.pathshala.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.career.pathshala.R;
import com.career.pathshala.api_call.CommonFunctions;
import com.career.pathshala.api_call.GlobalConstants;


/**
 * Created by Rupesh on 28-11-2016.
 */
public class SplashActivity extends Activity {


    String isactive;
CommonFunctions commonFunctions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        commonFunctions=new CommonFunctions(this);

        isactive= commonFunctions.myPreference.getString(SplashActivity.this, GlobalConstants.ISACTIVE);

        initview();
    }

    private void initview() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(isactive.equals("1")) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(SplashActivity.this, SignInActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2000);
    }
}
