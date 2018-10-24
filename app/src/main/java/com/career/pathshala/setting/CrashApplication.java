package com.career.pathshala.setting;


import android.support.multidex.MultiDexApplication;
import com.career.pathshala.R;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;


/**
 * Created by rupesh.m on 2/9/2017.
 */

@ReportsCrashes(mailTo = "osoftec@gmail.com", customReportContent = {
        ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
        ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
        ReportField.STACK_TRACE, ReportField.LOGCAT},
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)

public class CrashApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
    }
}
