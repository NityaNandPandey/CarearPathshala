package com.career.pathshala.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.career.pathshala.R;
import com.career.pathshala.api_call.AppUtil;
import com.career.pathshala.api_call.ConstantValues;
import com.career.pathshala.api_call.RSSReader;
import com.career.pathshala.api_call.ReferenceWrapper;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrepareYourselfScreen extends Activity {

    private ListView learningList;
    String key_items = "item";
    String key_title = "title";
    String key_description = "description";
    String key_link = "link";
    String key_date = "pubDate";
    ListView lstPost = null;
    List<HashMap<String, Object>> post_lists = new ArrayList<HashMap<String, Object>>();
    List<String> lists = new ArrayList<String>();
    List<String> lists_dates = new ArrayList<String>();
    List<String> lists_olddata = new ArrayList<String>();
    ArrayAdapter<String> adapter = null;
    ArrayAdapter<String> adapter_olddata = null;
    private ArrayList<String> links;
    private RSSReader rssfeed;
    private TextView header;
    private HashMap<String, Object> data;
    private Document xmlFeed;
    private ReferenceWrapper referenceWrapper;
    ImageView IV_backpress;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prepare_yourself_screen);
        Log.e("...", "oncreate");
        init();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CategoryScreen.class);
        startActivity(intent);
        finish();
    }


    private void init() {
        ConstantValues.COUNTER_OPEN = 0;
        ConstantValues.LOADING_RSS = 0;
        referenceWrapper = ReferenceWrapper.getReferenceWrapper(this);
        String category_url = referenceWrapper.getRecordStore(this, "category_url");
        Log.e("init p", "category_url= " + category_url);
        String category = referenceWrapper.getRecordStore(this, "category");
        ConstantValues.CATEGORY_URL = category_url;
        Log.e("init", "ConstantValues.CATEGORY_URL= " + ConstantValues.CATEGORY_URL);
        ConstantValues.CATEGORY = category;

        rssfeed = new RSSReader();


        header = (TextView) findViewById(R.id.header);
        header.setText(ConstantValues.CATEGORY);
        Typeface face = Typeface.createFromAsset(getAssets(), "adobearabic_regular.otf");
        header.setTypeface(face);

        links = new ArrayList<String>();
        learningList = (ListView) findViewById(R.id.ListView_learn);
        IV_backpress = (ImageView) findViewById(R.id.IV_backpress);
        IV_backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.finishActivityWithAnimation(PrepareYourselfScreen.this);
            }
        });

        learningList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                ConstantValues.URI = links.get(position);
                referenceWrapper.addRecordStore(PrepareYourselfScreen.this, "uri", ConstantValues.URI);


//				if (!ConstantValues.URI.startsWith("http://") && !ConstantValues.URI.startsWith("https://")){
//					ConstantValues.URI = "http://" + ConstantValues.URI;	
//				}

//				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ConstantValues.URI));
//				browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				browserIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//				startActivity(browserIntent);				

                Intent intent = new Intent(PrepareYourselfScreen.this, OpenLink.class);
                startActivity(intent);
//				finish();

            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, lists) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater = PrepareYourselfScreen.this.getLayoutInflater();
                View rowView = inflater.inflate(R.layout.custom_list, null);

//				RelativeLayout r1=((RelativeLayout)rowView.findViewById(R.id.relLayout));
//				RelativeLayout.LayoutParams layout_description = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//                        RelativeLayout.LayoutParams.MATCH_PARENT);
//				r1.setLayoutParams(layout_description);

                TextView heading = (TextView) rowView.findViewById(R.id.headings);
                Typeface face = Typeface.createFromAsset(getAssets(), "adobearabic_regular.otf");
                heading.setTypeface(face);
                data = post_lists.get(position);
                heading.setText(data.get(key_title).toString());
                return rowView;
            }
        };


        Log.e("prepare yourself", "url= " + ConstantValues.CATEGORY_URL);

        xmlFeed = rssfeed.getRSSFromServer(ConstantValues.CATEGORY_URL);

        NodeList nodes = xmlFeed.getElementsByTagName("item");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element item = (Element) nodes.item(i);
            HashMap<String, Object> feed = new HashMap<String, Object>();
            feed.put(key_title, rssfeed.getValue(item, key_title));
            feed.put(key_description, rssfeed.getValue(item, key_description));
            feed.put(key_link, rssfeed.getValue(item, key_link));
            feed.put(key_date, rssfeed.getValue(item, key_date));

            post_lists.add(feed);
            lists.add(feed.get(key_title).toString());
            links.add(feed.get(key_link).toString());
        }
        learningList.setAdapter(adapter);


    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e("...", "onstart");
        // EasyTracker.getInstance(this).activityStart(this);  //Add this method for google analytics.
    }

    @Override
    public void onStop() {
        super.onStop();
        // EasyTracker.getInstance(this).activityStop(this);  // Add this method for google analytics.
    }


}
