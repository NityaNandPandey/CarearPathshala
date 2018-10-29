package com.career.pathshala.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.career.pathshala.R;
import com.career.pathshala.api_call.CommonFunctions;
import com.career.pathshala.api_call.GlobalConstants;
import com.career.pathshala.fragment.Fragment_ExamAlert;
import com.career.pathshala.fragment.Fragment_aboutus;
import com.career.pathshala.fragment.Fragment_contactus;
import com.career.pathshala.fragment.Home_Fragment;
import com.career.pathshala.fragment.Job_Alert_Fragment;
import com.career.pathshala.fragment.Latest_Update_Fragment;
import com.career.pathshala.fragment.MangeProfile_Fragment;
import com.career.pathshala.fragment.Online_Quiz_Fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static Resources resources;
    public static Toolbar toolbar;
    private static FragmentManager manager;
    private static LinearLayout LL_contactus, LL_Share_The_App, LL_Rate_the_App, LL_About_Us, LL_Latest_Update, LL_Online_Quiz, LL_Job_Alert, LL_Exam_Alert, LL_Manage_Profile, LL_home;
    private static int isDrawerOpen = 0;
    public final int LOCATION_REQUEST = 100;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Fragment fragment;
    private CommonFunctions commonFunctions;
    TextView name, mobileno, TV_firstletter;
    String mName, mMobileno, firstletter;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;


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
        mobileno = (TextView) findViewById(R.id.mobileno);
        name = (TextView) findViewById(R.id.name);
        TV_firstletter = (TextView) findViewById(R.id.TV_firstletter);


        mMobileno = commonFunctions.myPreference.getString(MainActivity.this, GlobalConstants.MOBILE);
        mName = commonFunctions.myPreference.getString(MainActivity.this, GlobalConstants.FIRSTNAME);
        mobileno.setText(mMobileno);
        name.setText(mName);
        TV_firstletter.setText(mName);
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


        LL_contactus = (LinearLayout) findViewById(R.id.LL_contactus);
        LL_Share_The_App = (LinearLayout) findViewById(R.id.LL_Share_The_App);
        LL_Rate_the_App = (LinearLayout) findViewById(R.id.LL_Rate_the_App);
        LL_About_Us = (LinearLayout) findViewById(R.id.LL_About_Us);
        LL_Latest_Update = (LinearLayout) findViewById(R.id.LL_Latest_Update);
        LL_Online_Quiz = (LinearLayout) findViewById(R.id.LL_Online_Quiz);
        LL_Job_Alert = (LinearLayout) findViewById(R.id.LL_Job_Alert);
        LL_Exam_Alert = (LinearLayout) findViewById(R.id.LL_Exam_Alert);
        LL_Manage_Profile = (LinearLayout) findViewById(R.id.LL_Manage_Profile);
        LL_home = (LinearLayout) findViewById(R.id.LL_home);


        LL_contactus.setOnClickListener(this);
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

  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();

        drawer.closeDrawer(GravityCompat.START);
    }
*/

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            drawer.closeDrawer(GravityCompat.START);
            return;
        } else {
            drawer.closeDrawer(GravityCompat.START);
            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
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
                toolbar.setTitle("Home");
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
                replaceFragment(new Fragment_ExamAlert());

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
            case R.id.LL_contactus:
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("contact us");
                replaceFragment(new Fragment_contactus());
                break;

            case R.id.LL_Rate_the_App:
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("Rate Us");
                AppRate();
                replaceFragment(fragment);
                break;
            case R.id.LL_Share_The_App:
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("Share us");
                sharing();
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

    public void sharing() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Career Pathshala");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Please use my refer Career Pathshala when you used  get More");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void AppRate() {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }

}
