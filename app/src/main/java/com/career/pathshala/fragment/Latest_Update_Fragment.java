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

import com.career.pathshala.Model.LatestUpdateModel;
import com.career.pathshala.adapter.Latest_Update_Adapter;
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

public class Latest_Update_Fragment extends Fragment {
    private View rootView;
    private CommonFunctions cmf;
    private Latest_Update_Adapter mLatest_Update_Adapter;
    private ArrayList<LatestUpdateModel> arraylistme = new ArrayList<>();
    RecyclerView rv_sosbyMe;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.latest_update_fragment, container, false);
        initilize();
        return rootView;
    }

    @SuppressLint("LongLogTag")
    private void initilize() {
        cmf = new CommonFunctions(getActivity());
        rv_sosbyMe = (RecyclerView) rootView.findViewById(R.id.RV_sosrequest);
        rv_sosbyMe.setLayoutManager(new LinearLayoutManager(getActivity()));
        SosRequestByMe();


    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private void SosRequestByMe() {
        String skip = "0";
        new CallWebForService("Loading...", getContext(), cmf.urlList.News, cmf.sosbyme(skip), new MyServiceListener() {
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
                        final LatestUpdateModel tck = new LatestUpdateModel();
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

                        mLatest_Update_Adapter = new Latest_Update_Adapter(getActivity(), arraylistme);
                        rv_sosbyMe.setAdapter(mLatest_Update_Adapter);

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


