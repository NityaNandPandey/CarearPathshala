package com.career.pathshala.api_call;

import android.app.Activity;
import android.content.Intent;

import com.career.pathshala.R;


/**
 * Created by ram.sinha on 12/17/2016.
 */

public class AppUtil {



    public static void startActivityWithAnimation(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void finishActivityWithAnimation(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void finishActivityWithAnimationRight(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public static void startActivityForResultWithAnimation(Activity activity, Intent intent, int
            requestCode) {
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }




}





