package com.career.pathshala.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.career.pathshala.R;
import com.career.pathshala.activity.MainActivity;
import com.career.pathshala.api_call.CommonFunctions;

import butterknife.ButterKnife;


/**
 * Created by rupesh.m on 12/17/2016.
 */

public class Home_Fragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private LinearLayout Exam_Alert, Job_Alert, Laltest_Update, GK_and_Current_affairs;
    private CommonFunctions commonFunctions;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.emergency_no_fr, container, false);
        initilize();
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initilize() {
        commonFunctions = new CommonFunctions(getActivity());
        Exam_Alert = (LinearLayout) rootView.findViewById(R.id.Exam_Alert);
        Job_Alert = (LinearLayout) rootView.findViewById(R.id.Job_Alert);
        Laltest_Update = (LinearLayout) rootView.findViewById(R.id.Laltest_Update);
        GK_and_Current_affairs = (LinearLayout) rootView.findViewById(R.id.GK_and_Current_affairs);

        Exam_Alert.setOnClickListener(this);
        Job_Alert.setOnClickListener(this);
        Laltest_Update.setOnClickListener(this);
        GK_and_Current_affairs.setOnClickListener(this);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Exam_Alert:
                MainActivity.replaceFragment(new Fragment_ExamAlert());
                MainActivity.toolbar.setTitle("Exam Alert");
                break;
            case R.id.Job_Alert:
                MainActivity.replaceFragment(new Job_Alert_Fragment());
                MainActivity.toolbar.setTitle("Job Alert");
                break;
            case R.id.Laltest_Update:
                MainActivity.replaceFragment(new Latest_Update_Fragment());
                MainActivity.toolbar.setTitle("Laltest Update");
                break;
            case R.id.GK_and_Current_affairs:
                MainActivity.replaceFragment(new ExamAlert_Fragment());
                MainActivity.toolbar.setTitle("GK and Current Affairs");
                break;

        }
    }


}

