package com.career.pathshala.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.career.pathshala.Model.ExamAlertModel;
import com.career.pathshala.Model.SOSbyMeModel;
import com.career.pathshala.R;
import com.career.pathshala.api_call.CommonFunctions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Exam_Alert_Adapter extends RecyclerView.Adapter<Exam_Alert_Adapter.MyViewHolder> implements View.OnClickListener {
    private LayoutInflater inflater;
    private Context context;
    private List<ExamAlertModel> arrayList;

    public Exam_Alert_Adapter(Context mcontext, ArrayList<ExamAlertModel> data) {
        this.context = mcontext;
        this.arrayList = data;
        inflater = LayoutInflater.from(mcontext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.exam_alert_adapter, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

      /*  tck.setId(id);
        tck.setContent(content);
        tck.setUrl(url);
        tck.setImage(image);
        tck.setExamtypeid(examtypeid);
        tck.setIs_deleted(is_deleted);
        tck.setModified_on(modified_on);
        tck.setCreated_on(created_on);
        tck.setExamtype(examtype);*/



        holder.groupmembername.setText(arrayList.get(position).getContent());
        holder.currentdate.setText(arrayList.get(position).getModified_on());
        //holder.reject.setTag(position);
        if (!arrayList.get(position).getImage().equals("")) {
            Picasso.with(context).load(arrayList.get(position).getImage()).into(holder.ImageRiView);
        } else {
            Picasso.with(context).load(R.drawable.apk_icon).into(holder.ImageRiView);
        }
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

        public TextView groupmembername, currentdate;
        ImageView ImageRiView;

        public MyViewHolder(View view) {
            super(view);
            groupmembername = (TextView) view.findViewById(R.id.groupmembername);
            currentdate = (TextView) view.findViewById(R.id.currentdate);
            ImageRiView = (ImageView) view.findViewById(R.id.ImageRiView);
        }
    }
}

