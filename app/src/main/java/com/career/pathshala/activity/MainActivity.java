package com.career.pathshala.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.career.pathshala.R;
import com.career.pathshala.api_call.CommonFunctions;
import com.career.pathshala.api_call.GlobalConstants;
import com.career.pathshala.fragment.Fragment_Notifications;
import com.career.pathshala.fragment.Fragment_aboutus;
import com.career.pathshala.fragment.Home_Fragment;
import com.career.pathshala.fragment.Job_Alert_Fragment;
import com.career.pathshala.fragment.Latest_Update_Fragment;
import com.career.pathshala.fragment.MangeProfile_Fragment;
import com.career.pathshala.fragment.Online_Quiz_Fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static Resources resources;
    public static Toolbar toolbar;
    private static FragmentManager manager;
    private static LinearLayout LL_Forgot_Password, LL_Share_The_App, LL_Rate_the_App, LL_About_Us, LL_Latest_Update, LL_Online_Quiz, LL_Job_Alert, LL_Exam_Alert, LL_Manage_Profile, LL_home;
    private static int isDrawerOpen = 0;
    public final int LOCATION_REQUEST = 100;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Fragment fragment;
    private CommonFunctions commonFunctions;


    public static void replaceFragment(Fragment fragment) {
        try {
            toolbar.setVisibility(View.VISIBLE);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            manager.popBackStack();
            transaction.commit();
        } catch (Exception e) {
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // super.onCreate();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resources = getResources();
        Initialization();


    }

    private void Initialization() {
        commonFunctions = new CommonFunctions(MainActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar12);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(resources.getColor(R.color.white));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // mobileno = (TextView) findViewById(R.id.mobileno);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                //friendListCount();

                //  mobileno.setText("");
            }
        };

        drawer.setDrawerListener(toggle);

        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        LL_home = (LinearLayout) findViewById(R.id.LL_home);


        LL_Forgot_Password = (LinearLayout) findViewById(R.id.LL_Forgot_Password);
        LL_Share_The_App = (LinearLayout) findViewById(R.id.LL_Share_The_App);
        LL_Rate_the_App = (LinearLayout) findViewById(R.id.LL_Rate_the_App);
        LL_About_Us = (LinearLayout) findViewById(R.id.LL_About_Us);
        LL_Latest_Update = (LinearLayout) findViewById(R.id.LL_Latest_Update);
        LL_Online_Quiz = (LinearLayout) findViewById(R.id.LL_Online_Quiz);
        LL_Job_Alert = (LinearLayout) findViewById(R.id.LL_Job_Alert);
        LL_Exam_Alert = (LinearLayout) findViewById(R.id.LL_Exam_Alert);
        LL_Manage_Profile = (LinearLayout) findViewById(R.id.LL_Manage_Profile);
        LL_home = (LinearLayout) findViewById(R.id.LL_home);


        LL_Forgot_Password.setOnClickListener(this);
        LL_Share_The_App.setOnClickListener(this);
        LL_Rate_the_App.setOnClickListener(this);
        LL_About_Us.setOnClickListener(this);
        LL_Latest_Update.setOnClickListener(this);
        LL_Online_Quiz.setOnClickListener(this);
        LL_Job_Alert.setOnClickListener(this);
        LL_Exam_Alert.setOnClickListener(this);
        LL_Manage_Profile.setOnClickListener(this);
        LL_home.setOnClickListener(this);


        navigationView.setNavigationItemSelectedListener(this);
        manager = getSupportFragmentManager();
        replaceFragment(new Home_Fragment());

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.LL_home:
                drawer.closeDrawer(GravityCompat.START);
                replaceFragment(new Home_Fragment());

                break;
            case R.id.LL_Manage_Profile:
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("Mange Profile");
                replaceFragment(new MangeProfile_Fragment());
                break;

            case R.id.LL_Exam_Alert:
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("Exam Alert");
                replaceFragment(new Fragment_Notifications());

                replaceFragment(fragment);
                break;
            case R.id.LL_Job_Alert:
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("Job Alert");
                replaceFragment(new Job_Alert_Fragment());
                break;

            case R.id.LL_Online_Quiz:
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("OnLine Quiz");
                replaceFragment(new Online_Quiz_Fragment());
                break;


            case R.id.LL_Latest_Update:
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("Latest Update");
                replaceFragment(new Latest_Update_Fragment());

                break;
            case R.id.LL_About_Us:
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("About Us");
                replaceFragment(new Fragment_aboutus());

                break;
            case R.id.LL_Rate_the_App:
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("Rate Us");
                replaceFragment(fragment);

                break;
            case R.id.LL_Share_The_App:
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("Share us");
                replaceFragment(fragment);

                break;
            case R.id.LL_Forgot_Password:
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("Profile");
                replaceFragment(fragment);

                break;

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    @Override
    protected void onResume() {
        super.onResume();

        freeMemory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        freeMemory();

    }

    public void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();

    }


}
