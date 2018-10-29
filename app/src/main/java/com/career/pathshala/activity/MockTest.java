package com.career.pathshala.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.career.pathshala.R;
import com.career.pathshala.api_call.AppUtil;
import com.career.pathshala.api_call.ConstantValues;
import com.career.pathshala.api_call.ReferenceWrapper;
import com.career.pathshala.database.DatabaseHandler;

public class MockTest extends Activity implements OnClickListener {

    private Button mocktest1;
    private Button mocktest2;
    private Button mocktest3;
    private Button mocktest4;
    private Button mocktest5;
    private Button mocktest6;
    private Button mocktest7;
    private Button mocktest8;
    private TextView header;
    private DatabaseHandler databasehandler;
    private ReferenceWrapper referenceWrapper;
    ImageView IV_backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_test);
        IV_backpress = (ImageView) findViewById(R.id.IV_backpress);
        IV_backpress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.finishActivityWithAnimation(MockTest.this);
            }
        });
        init();
    }

    private void init() {
        referenceWrapper = ReferenceWrapper.getReferenceWrapper(this);
        referenceWrapper.addRecordStore(this, "tablename", "");
        referenceWrapper.addRecordStore(this, "mocktest_name", "");
        referenceWrapper.addRecordStore(this, "counter", "");
        for (int i = 0; i < 30; i++) {
            referenceWrapper.arr_userans[i] = "";
        }

        databasehandler = new DatabaseHandler();
        databasehandler.creatdatabase(this);

        findIds();
        onClickListeners();

    }


    @Override
    public void onStart() {
        super.onStart();
        //  EasyTracker.getInstance(this).activityStart(this);  //Add this method for google analytics.
    }

    @Override
    public void onStop() {
        super.onStop();
        //   EasyTracker.getInstance(this).activityStop(this);  // Add this method for google analytics.
    }


    private void findIds() {
        mocktest1 = (Button) findViewById(R.id.mocktest1);
        mocktest2 = (Button) findViewById(R.id.mocktest2);
        mocktest3 = (Button) findViewById(R.id.mocktest3);
        mocktest4 = (Button) findViewById(R.id.mocktest4);
        mocktest5 = (Button) findViewById(R.id.mocktest5);
        mocktest6 = (Button) findViewById(R.id.mocktest6);
        mocktest7 = (Button) findViewById(R.id.mocktest7);
        mocktest8 = (Button) findViewById(R.id.mocktest8);
        header = (TextView) findViewById(R.id.header);

        Typeface face = Typeface.createFromAsset(getAssets(), "adobearabic_regular.otf");
        header.setTypeface(face);
        mocktest1.setTypeface(face);
        mocktest2.setTypeface(face);
        mocktest3.setTypeface(face);
        mocktest4.setTypeface(face);
        mocktest5.setTypeface(face);
        mocktest6.setTypeface(face);
        mocktest7.setTypeface(face);
        mocktest8.setTypeface(face);
    }

    private void onClickListeners() {
        mocktest1.setOnClickListener(this);
        mocktest2.setOnClickListener(this);
        mocktest3.setOnClickListener(this);
        mocktest4.setOnClickListener(this);
        mocktest5.setOnClickListener(this);
        mocktest6.setOnClickListener(this);
        mocktest7.setOnClickListener(this);
        mocktest8.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mocktest1) {
            ConstantValues.MOCKTEST_NO = 1;
            ConstantValues.TABLENAME = "mocktest5";
            ConstantValues.MOCKTEST_NAME = "Mock Test-1";
        } else if (v.getId() == R.id.mocktest2) {
            ConstantValues.MOCKTEST_NO = 2;
            ConstantValues.TABLENAME = "mocktest6";
            ConstantValues.MOCKTEST_NAME = "Mock Test-2";
        } else if (v.getId() == R.id.mocktest3) {
            ConstantValues.MOCKTEST_NO = 3;
            ConstantValues.TABLENAME = "mocktest7";
            ConstantValues.MOCKTEST_NAME = "Mock Test-3";
        } else if (v.getId() == R.id.mocktest4) {
            ConstantValues.MOCKTEST_NO = 4;
            ConstantValues.TABLENAME = "mocktest8";
            ConstantValues.MOCKTEST_NAME = "Mock Test-4";
        } else if (v.getId() == R.id.mocktest5) {
            ConstantValues.MOCKTEST_NO = 5;
            ConstantValues.TABLENAME = "mocktest1";
            ConstantValues.MOCKTEST_NAME = "Mock Test-5";
        } else if (v.getId() == R.id.mocktest6) {
            ConstantValues.MOCKTEST_NO = 6;
            ConstantValues.TABLENAME = "mocktest2";
            ConstantValues.MOCKTEST_NAME = "Mock Test-6";
        } else if (v.getId() == R.id.mocktest7) {
            ConstantValues.MOCKTEST_NO = 7;
            ConstantValues.TABLENAME = "mocktest3";
            ConstantValues.MOCKTEST_NAME = "Mock Test-7";
        } else if (v.getId() == R.id.mocktest8) {
            ConstantValues.MOCKTEST_NO = 8;
            ConstantValues.TABLENAME = "mocktest4";
            ConstantValues.MOCKTEST_NAME = "Mock Test-8";
        }
        referenceWrapper.addRecordStore(this, "mocktest_name", ConstantValues.MOCKTEST_NAME);
        referenceWrapper.addRecordStore(this, "tablename", ConstantValues.TABLENAME);
        referenceWrapper.addRecordStore(this, "counter", "1");

        Intent intent = new Intent(this, MockTestSession.class);
        startActivity(intent);
        finish();
    }

}
