package com.career.pathshala.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.career.pathshala.Model.OnlineQuizModel;
import com.career.pathshala.R;
import com.career.pathshala.activity.CategoryScreen;
import com.career.pathshala.activity.MockTest;
import com.career.pathshala.adapter.Online_Quiz_Adapter;
import com.career.pathshala.api_call.CallWebForService;
import com.career.pathshala.api_call.CommonFunctions;
import com.career.pathshala.api_call.ConstantValues;
import com.career.pathshala.api_call.MyServiceListener;
import com.career.pathshala.api_call.ReviewHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by om on 3/9/2017.
 */

public class Currentaffairs_Fragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CommonFunctions cmf;
    private Online_Quiz_Adapter sos_by_me_adapter;
    private ArrayList<OnlineQuizModel> arraylistme = new ArrayList<>();
    RecyclerView rv_sosbyMe;
    private Button facebook;
    private Button share;
    private Button review;
    private Button help;
    private LinearLayout prepare_yourself;
    private LinearLayout mocktest;
    private LinearLayout history;
    private TextView header;
    private TextView txt_learn;
    private TextView txt_mocktest;
    private TextView txt_history;
    private Dialog dialog_exit;
    private TextView heading;
    private Button yes;
    private Button no;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.currentaffairs_fragment, container, false);
        initilize();
        return rootView;
    }

    @SuppressLint("LongLogTag")
    private void initilize() {
        cmf = new CommonFunctions(getActivity());


        facebook = (Button) rootView.findViewById(R.id.facebook);
        share = (Button) rootView.findViewById(R.id.share);
        review = (Button) rootView.findViewById(R.id.review);
        prepare_yourself = (LinearLayout) rootView.findViewById(R.id.learn);
        mocktest = (LinearLayout) rootView.findViewById(R.id.mocktest);
        history = (LinearLayout) rootView.findViewById(R.id.history);
        header = (TextView) rootView.findViewById(R.id.header);
        txt_learn = (TextView) rootView.findViewById(R.id.txt_learn);
        txt_mocktest = (TextView) rootView.findViewById(R.id.txt_mocktest);
        txt_history = (TextView) rootView.findViewById(R.id.txt_history);

        prepare_yourself.setOnClickListener(this);
        mocktest.setOnClickListener(this);
        facebook.setOnClickListener(this);
        review.setOnClickListener(this);
        share.setOnClickListener(this);

    }


    @Override
    public void onResume() {
        super.onResume();
        // rippleBackground.startRippleAnimation();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private void SosRequestByMe() {
        String test_type = "1";
        new CallWebForService("Loading...", getContext(), cmf.urlList.Questions, cmf.Questions(test_type), new MyServiceListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("Login--->", string);
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject jsonObject2 = jsonObject.getJSONObject("CommandResult");
                    String success = jsonObject2.optString("success");
                    String Message = jsonObject2.optString("message");


                    if (success.equals("1")) {
                        arraylistme = new ArrayList<>();
                        final OnlineQuizModel tck = new OnlineQuizModel();
                        JSONArray jsonArray = jsonObject2.optJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            final JSONObject jsonObject1 = jsonArray.optJSONObject(i);

                            String id = jsonObject1.optString("id");
                            String msg = jsonObject1.optString("msg");
                            String type = jsonObject1.optString("type");
                            String image = jsonObject1.optString("image");
                            String is_deleted = jsonObject1.optString("is_deleted");
                            String is_seen = jsonObject1.optString("is_seen");
                            String created_on = jsonObject1.optString("CreatedOn");
                            String modified_on = jsonObject1.optString("modified_on");
                            tck.setId(id);
                            tck.setMsg(msg);
                            tck.setType(type);
                            tck.setImage(image);
                            tck.setIs_deleted(is_deleted);
                            tck.setIs_seen(is_seen);
                            tck.setCreated_on(created_on);
                            tck.setModified_on(modified_on);
                            arraylistme.add(tck);
                        }

                        sos_by_me_adapter = new Online_Quiz_Adapter(getActivity(), arraylistme);
                        rv_sosbyMe.setAdapter(sos_by_me_adapter);

                    } else {
                        Toast.makeText(getContext(), Message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.learn:
                ConstantValues.CLICKABLE=0;
                ConstantValues.LOADING_RSS=0;
                Intent intent=new Intent(getContext(),CategoryScreen.class);
                startActivity(intent);
                break;
            case R.id.mocktest:
                Intent intent1=new Intent(getContext(),MockTest.class);
                startActivity(intent1);
                break;
            case R.id.facebook:
                facebook();
                break;
            case R.id.review:
                review();
                break;
            case R.id.share:
                share();
                break;

        }
    }
    @SuppressLint("WrongConstant")
    private void facebook() {
        Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/pages/AppStuds-Mobile/1504858033124227?skip_nax_wizard=true&ref_type=bookmark"));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        browserIntent.setFlags(Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES);
        this.startActivity(browserIntent);
    }

    private void share() {
        Intent sendIntent=new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        if(ReviewHandler.getReviewURL().equals("http://goo.gl/VmV5WW"))
        {
            sendIntent.putExtra(Intent.EXTRA_TEXT, "I am using an awesome Current Affair app. Get your Current Affair app on Android now! link:- http://goo.gl/VmV5WW");
        }
        else if(ReviewHandler.getReviewURL().equals("http://goo.gl/V3UFSN")){
            sendIntent.putExtra(Intent.EXTRA_TEXT, "I am using an awesome Current Affair app. Get your Current Affair app on Android now! link:- http://goo.gl/V3UFSN");
        }
        else
        {
            sendIntent.putExtra(Intent.EXTRA_TEXT, "I am using an awesome Current Affair app. Get your Current Affair app on Android now! link:- https://play.google.com/store/apps/details?id=com.gk.currentaffairs");
        }
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share  To...."));
    }

    private void review() {
        ReviewHandler.startReview(getActivity());
    }

}


