package com.career.pathshala.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.career.pathshala.R;
import com.career.pathshala.api_call.AppUtil;
import com.career.pathshala.api_call.ConstantValues;
import com.career.pathshala.api_call.ReferenceWrapper;

public class CategoryScreen extends Activity implements OnClickListener {

    private RelativeLayout business;
    private RelativeLayout environment;
    private RelativeLayout entertainment;
    private TextView header;
    private RelativeLayout politics;
    private RelativeLayout science;
    private RelativeLayout sports;
    private RelativeLayout technology;
    private RelativeLayout topnews;
    private RelativeLayout world;
    private RelativeLayout usnews;
    private TextView txt_business;
    private TextView txt_environment;
    private TextView txt_entertainment;
    private TextView txt_politics;
    private TextView txt_science;
    private TextView txt_sports;
    private TextView txt_technology;
    private TextView txt_topnews;
    private TextView txt_world;
    private TextView txt_usnews;
    private Dialog dialog_no_network;
    private TextView heading;
    private Button ok;
    private RelativeLayout olddata;
    private TextView txt_olddata;
    ProgressDialog progressBar;
    private Handler progressBarHandler = new Handler();
    private TextView text;
    private Dialog dialog_loading;
    private Handler handler;
    private ReferenceWrapper referenceWrapper;
    ImageView IV_backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_screen);
        init();
    }

    private void init() {
        Log.e("...", "init");

        IV_backpress = (ImageView) findViewById(R.id.IV_backpress);
        IV_backpress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.finishActivityWithAnimation(CategoryScreen.this);
            }
        });

        handler = new Handler();
        findIds();
        onClickListeners();
        setclickabletrue();


    }


    @Override
    protected void onResume() {
        Log.e("...", "onresume");
        if (ConstantValues.CLICKABLE == 1) {
            Log.e("....", "ConstantValues.CLICKABLE= " + ConstantValues.CLICKABLE);
            ConstantValues.CLICKABLE = 0;
            setclickabletrue();
        }
        if (ConstantValues.LOADING_RSS == 1) {
            Log.e("....", "ConstantValues.LOADING_RSS= " + ConstantValues.LOADING_RSS);
            ConstantValues.LOADING_RSS = 0;
            ConstantValues.CLICKABLE = 0;
            dialog_loading.dismiss();
            setclickabletrue();
        }

        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


    private void findIds() {
        referenceWrapper = ReferenceWrapper.getReferenceWrapper(this);

        business = (RelativeLayout) findViewById(R.id.business);
        environment = (RelativeLayout) findViewById(R.id.environment);
        entertainment = (RelativeLayout) findViewById(R.id.entertainment);
        politics = (RelativeLayout) findViewById(R.id.politics);
        science = (RelativeLayout) findViewById(R.id.science);
        sports = (RelativeLayout) findViewById(R.id.sports);
        technology = (RelativeLayout) findViewById(R.id.technology);
        topnews = (RelativeLayout) findViewById(R.id.topnews);
        world = (RelativeLayout) findViewById(R.id.world);
        usnews = (RelativeLayout) findViewById(R.id.usnews);
        olddata = (RelativeLayout) findViewById(R.id.olddata);

        header = (TextView) findViewById(R.id.header);

        txt_business = (TextView) findViewById(R.id.txt_business);
        txt_environment = (TextView) findViewById(R.id.txt_environment);
        txt_entertainment = (TextView) findViewById(R.id.txt_entertainment);
        txt_politics = (TextView) findViewById(R.id.txt_politics);
        txt_science = (TextView) findViewById(R.id.txt_science);
        txt_sports = (TextView) findViewById(R.id.txt_sports);
        txt_technology = (TextView) findViewById(R.id.txt_technology);
        txt_topnews = (TextView) findViewById(R.id.txt_topnews);
        txt_world = (TextView) findViewById(R.id.txt_world);
        txt_usnews = (TextView) findViewById(R.id.txt_usnews);
        txt_olddata = (TextView) findViewById(R.id.txt_olddata);

        Typeface face = Typeface.createFromAsset(getAssets(), "adobearabic_regular.otf");
        header.setTypeface(face);
        txt_business.setTypeface(face);
        txt_entertainment.setTypeface(face);
        txt_environment.setTypeface(face);
        txt_politics.setTypeface(face);
        txt_science.setTypeface(face);
        txt_sports.setTypeface(face);
        txt_technology.setTypeface(face);
        txt_topnews.setTypeface(face);
        txt_world.setTypeface(face);
        txt_usnews.setTypeface(face);
        txt_olddata.setTypeface(face);
    }

    private void onClickListeners() {
        business.setOnClickListener(this);
        entertainment.setOnClickListener(this);
        environment.setOnClickListener(this);
        politics.setOnClickListener(this);
        science.setOnClickListener(this);
        sports.setOnClickListener(this);
        technology.setOnClickListener(this);
        topnews.setOnClickListener(this);
        usnews.setOnClickListener(this);
        world.setOnClickListener(this);
        olddata.setOnClickListener(this);
    }

    private void setclickablefalse() {
        ConstantValues.CLICKABLE = 1;
        business.setClickable(false);
        entertainment.setClickable(false);
        environment.setClickable(false);
        politics.setClickable(false);
        science.setClickable(false);
        sports.setClickable(false);
        technology.setClickable(false);
        topnews.setClickable(false);
        usnews.setClickable(false);
        world.setClickable(false);
        olddata.setClickable(false);
    }

    private void setclickabletrue() {
        ConstantValues.CLICKABLE = 0;
        business.setClickable(true);
        entertainment.setClickable(true);
        environment.setClickable(true);
        politics.setClickable(true);
        science.setClickable(true);
        sports.setClickable(true);
        technology.setClickable(true);
        topnews.setClickable(true);
        usnews.setClickable(true);
        world.setClickable(true);
        olddata.setClickable(true);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.business) {
            ConstantValues.CATEGORY = "Business";
            ConstantValues.CATEGORY_URL = "http://feeds.reuters.com/reuters/businessNews";
//			ConstantValues.CATEGORY_URL="http://www.tehelka.com/category/current-affairs/?feed=custom_feed";
            fire_intent();
        } else if (v.getId() == R.id.entertainment) {
            ConstantValues.CATEGORY = "Entertainment";
            ConstantValues.CATEGORY_URL = "http://feeds.reuters.com/reuters/entertainment";
//			ConstantValues.CATEGORY_URL="http://www.pbs.org/kcet/tavissmiley/rss/entertainment.xml";
            fire_intent();
        } else if (v.getId() == R.id.environment) {
            ConstantValues.CATEGORY = "Environment";
//			ConstantValues.CATEGORY_URL="http://www.tehelka.com/environment/?feed=custom_feed";
            ConstantValues.CATEGORY_URL = "http://feeds.reuters.com/reuters/environment";
            fire_intent();
        } else if (v.getId() == R.id.sports) {
            ConstantValues.CATEGORY = "Sports";
            ConstantValues.CATEGORY_URL = "http://feeds.reuters.com/reuters/sportsNews";
            fire_intent();
        } else if (v.getId() == R.id.science) {
            ConstantValues.CATEGORY = "Science";
            ConstantValues.CATEGORY_URL = "http://feeds.reuters.com/reuters/scienceNews";
            fire_intent();
        } else if (v.getId() == R.id.technology) {
            ConstantValues.CATEGORY = "Technology";
            ConstantValues.CATEGORY_URL = "http://feeds.reuters.com/reuters/technologyNews";
            fire_intent();
        } else if (v.getId() == R.id.topnews) {
            ConstantValues.CATEGORY = "Top News";
            ConstantValues.CATEGORY_URL = "http://feeds.reuters.com/reuters/topNews";
//			ConstantValues.CATEGORY_URL="http://www.tehelka.com/category/current-affairs/?feed=custom_feed";
            fire_intent();
        } else if (v.getId() == R.id.usnews) {
            ConstantValues.CATEGORY = "US News";
            ConstantValues.CATEGORY_URL = "http://feeds.reuters.com/Reuters/domesticNews";
            fire_intent();
        } else if (v.getId() == R.id.world) {
            ConstantValues.CATEGORY = "World";
            ConstantValues.CATEGORY_URL = "http://feeds.reuters.com/Reuters/worldNews";
            fire_intent();
        } else if (v.getId() == R.id.politics) {
            ConstantValues.CATEGORY = "Politics";
            ConstantValues.CATEGORY_URL = "http://feeds.reuters.com/Reuters/PoliticsNews";
            fire_intent();
        } else if (v.getId() == R.id.olddata) {
            ConstantValues.CATEGORY = "Current Month's affairs";
            ConstantValues.CATEGORY_URL = "http://currentaffairs.freejobalert.com/feed/";
            //			int connected=hasConnection();
            //			if(connected==0)
            //			{
            //				Log.e("..","connected=0 "+connected);
            //				no_network_popup();
            //			}
            //			else
            //			{
            //				loading_popup();
            //			}
            fire_intent();
        }
        referenceWrapper.addRecordStore(this, "category", ConstantValues.CATEGORY);
        referenceWrapper.addRecordStore(this, "category_url", ConstantValues.CATEGORY_URL);
    }

    private void fire_intent() {
        int connected = hasConnection();
        if (connected == 0) {
            Log.e("..", "connected=0 " + connected);
            no_network_popup();
        } else {
            setclickablefalse();
            loading_popup();
            //			Intent intent=new Intent(this,PrepareYourselfScreen.class);
            //			startActivity(intent);

        }
    }

    private void loading_popup() {
        dialog_loading = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_loading.setContentView(R.layout.custom_loading);
        text = (TextView) dialog_loading.findViewById(R.id.txt_loading);
        Typeface face = Typeface.createFromAsset(getAssets(), "adobearabic_regular.otf");
        text.setTypeface(face);

        handler.postDelayed(loading, 500);

        dialog_loading.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                handler.removeCallbacks(loading);
                dialog_loading.dismiss();
                ConstantValues.LOADING_RSS = 0;
                ConstantValues.CLICKABLE = 0;
                setclickabletrue();
            }
        });

        dialog_loading.show();
    }

    private Runnable loading = new Runnable() {

        @Override
        public void run() {
            ConstantValues.LOADING_RSS = 1;

            Intent intent = new Intent(CategoryScreen.this, PrepareYourselfScreen.class);
            startActivity(intent);
            finish();
        }
    };

    private void no_network_popup() {
        dialog_no_network = new Dialog(this);
        dialog_no_network.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_no_network.setContentView(R.layout.popup_internet_check);
        heading = (TextView) dialog_no_network.findViewById(R.id.heading);
        ok = (Button) dialog_no_network.findViewById(R.id.button_ok);
        Typeface face = Typeface.createFromAsset(getAssets(), "adobearabic_regular.otf");
        ok.setTypeface(face);
        heading.setTypeface(face);
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        dialog_no_network.show();
    }


    public int hasConnection() {
        int flag = 0;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        //Log.e("....","type of network= "+activeNetwork.getTypeName());
        if (activeNetwork != null && activeNetwork.isConnected()) {
            flag = 1;
        }

        if (flag == 0) {
            return 0;
        } else {
            return 1;
        }
    }


}
