package com.career.pathshala.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.career.pathshala.Model.SOSbyMeModel;
import com.career.pathshala.R;
import com.career.pathshala.api_call.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rupesh.m on 6/1/2017.
 */

public class Online_Quiz_Adapter extends RecyclerView.Adapter<Online_Quiz_Adapter.MyViewHolder> implements View.OnClickListener {
    SOSbyMeModel soSbyMeModel;
    private LayoutInflater inflater;
    private Context context;
    private List<SOSbyMeModel> arrayList;
    private CommonFunctions cmf;


    public Online_Quiz_Adapter(Context mcontext, ArrayList<SOSbyMeModel> data) {
        this.context = mcontext;
        this.arrayList = data;
        inflater = LayoutInflater.from(mcontext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_sos_helper_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Online_Quiz_Adapter.MyViewHolder holder, int position) {
        cmf = new CommonFunctions(context);
        //soSbyMeModel = arrayList.get(position);
        holder.groupmembername.setText(soSbyMeModel.getMsg());
        holder.TV_name.setText(soSbyMeModel.getMsg());
        // countycode = cmf.myPreference.getString(context, GlobalConstants.COUNTYCD);
        // holder.TV_mobile.setText(countycode+" "+userSOS_helpModel.getMobileno());
        holder.TV_mobile.setText(soSbyMeModel.getCreated_on());
        holder.TV_address.setText(soSbyMeModel.getType());

    }


    @Override//
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView groupmembername;
        public TextView TV_name, TV_mobile, TV_address;

        public MyViewHolder(View view) {
            super(view);
            TV_name = (TextView) view.findViewById(R.id.TV_name);
            TV_mobile = (TextView) view.findViewById(R.id.TV_mobile);
            TV_address = (TextView) view.findViewById(R.id.TV_address);

        }
    }

}

