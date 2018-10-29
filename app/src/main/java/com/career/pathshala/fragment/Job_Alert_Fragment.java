package com.career.pathshala.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.career.pathshala.Model.ExamAlertModel;
import com.career.pathshala.Model.JobAlertModel;
import com.career.pathshala.adapter.Job_Alert_Adapter;
import com.career.pathshala.adapter.SOS_By_Me_Adapter;
import com.career.pathshala.ClickListener.RecyclerItemClickListener;
import com.career.pathshala.Model.SOSbyMeModel;
import com.career.pathshala.R;
import com.career.pathshala.api_call.CallWebForService;
import com.career.pathshala.api_call.CommonFunctions;
import com.career.pathshala.api_call.MyServiceListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by om on 3/9/2017.
 */

public class Job_Alert_Fragment extends Fragment {
    private View rootView;
    private CommonFunctions cmf;
    private Job_Alert_Adapter jobAlertAdapter;
    private ArrayList<JobAlertModel> arraylistme = new ArrayList<>();
    RecyclerView rv_sosbyMe;
    JobAlertModel tck;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.job_alert_fragment, container, false);
        initilize();
        return rootView;
    }

    @SuppressLint("LongLogTag")
    private void initilize() {
        cmf = new CommonFunctions(getActivity());
        rv_sosbyMe = (RecyclerView) rootView.findViewById(R.id.RV_sosrequest);
        rv_sosbyMe.setLayoutManager(new LinearLayoutManager(getActivity()));
        SosRequestByMe();
        rv_sosbyMe.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rv_sosbyMe, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // rv_sosbyMeClick(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        }));


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
        String skip = "0";
        new CallWebForService("Loading...", getContext(), cmf.urlList.jobalert, cmf.sosbyme(skip), new MyServiceListener() {
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

                        JSONArray jsonArray = jsonObject2.optJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            final JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            tck = new JobAlertModel();
                            String id = jsonObject1.optString("id");
                            String content = jsonObject1.optString("content");
                            String url = jsonObject1.optString("url");
                            String image = jsonObject1.optString("image");
                            String examtypeid = jsonObject1.optString("examtypeid");
                            String is_deleted = jsonObject1.optString("is_deleted");
                            String modified_on = jsonObject1.optString("modified_on");
                            String created_on = jsonObject1.optString("CreatedOn");
                            String examtype = jsonObject1.optString("examtype");
                            tck.setId(id);
                            tck.setContent(content);
                            tck.setUrl(url);
                            tck.setImage(image);
                            tck.setExamtypeid(examtypeid);
                            tck.setIs_deleted(is_deleted);
                            tck.setModified_on(modified_on);
                            tck.setCreated_on(created_on);
                            tck.setExamtype(examtype);
                            arraylistme.add(tck);

                        }

                        jobAlertAdapter = new Job_Alert_Adapter(getActivity(), arraylistme);
                        rv_sosbyMe.setAdapter(jobAlertAdapter);

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


}


