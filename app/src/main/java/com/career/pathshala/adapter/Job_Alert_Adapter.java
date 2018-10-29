package com.career.pathshala.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.career.pathshala.Model.JobAlertModel;
import com.career.pathshala.Model.SOSbyMeModel;
import com.career.pathshala.R;
import com.career.pathshala.api_call.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

public class Job_Alert_Adapter extends RecyclerView.Adapter<Job_Alert_Adapter.MyViewHolder> implements View.OnClickListener {
    JobAlertModel mJobAlertModel;
    private LayoutInflater inflater;
    private Context context;
    private List<JobAlertModel> arrayList;
    private CommonFunctions cmf;

    public Job_Alert_Adapter(Context mcontext, ArrayList<JobAlertModel> data) {
        this.context = mcontext;
        this.arrayList = data;
        inflater = LayoutInflater.from(mcontext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.job_alert_adapter, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        cmf = new CommonFunctions(context);
        holder.groupmembername.setText(arrayList.get(position).getContent());
        holder.mModifiedon.setText(arrayList.get(position).getModified_on());


       /* tck.setId(id);
        tck.setContent(content);
        tck.setUrl(url);
        tck.setImage(image);
        tck.setExamtypeid(examtypeid);
        tck.setIs_deleted(is_deleted);
        tck.setModified_on(modified_on);
        tck.setCreated_on(created_on);
        tck.setExamtype(examtype);*/
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
        public TextView mModifiedon;

        public MyViewHolder(View view) {
            super(view);
            groupmembername = (TextView) view.findViewById(R.id.groupmembername);
            mModifiedon = (TextView) view.findViewById(R.id.Modified_on);
        }
    }
}

