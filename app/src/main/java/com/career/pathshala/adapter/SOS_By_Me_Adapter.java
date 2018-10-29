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

public class SOS_By_Me_Adapter extends RecyclerView.Adapter<SOS_By_Me_Adapter.MyViewHolder> implements View.OnClickListener {
    SOSbyMeModel soSbyMeModel;
    private LayoutInflater inflater;
    private Context context;
    private List<SOSbyMeModel> arrayList;
    private CommonFunctions cmf;

    public SOS_By_Me_Adapter(Context mcontext, ArrayList<SOSbyMeModel> data) {
        this.context = mcontext;
        this.arrayList = data;
        inflater = LayoutInflater.from(mcontext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_sos_request__by_me, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        cmf = new CommonFunctions(context);
        holder.groupmembername.setText(arrayList.get(position).getMsg());
        holder.reject.setText(arrayList.get(position).getCreated_on());
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
        public TextView reject;

        public MyViewHolder(View view) {
            super(view);
            groupmembername = (TextView) view.findViewById(R.id.groupmembername);
            reject = (TextView) view.findViewById(R.id.reject);
        }
    }
}

