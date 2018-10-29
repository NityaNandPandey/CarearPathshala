package com.career.pathshala.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.career.pathshala.R;
import com.career.pathshala.api_call.AppUtil;
import com.career.pathshala.api_call.ConstantValues;
import com.career.pathshala.api_call.ReferenceWrapper;
import com.career.pathshala.database.DatabaseHandler;

import java.util.ArrayList;

public class SubmitScreen extends Activity {

    private ListView List;
    private MockTestSession mocktestsession;
    private ArrayList<MessageDetails> details;
    private TextView header;
    private TextView quest;
    private TextView user;
    private TextView correct;
    private DatabaseHandler databasehandler;
    private ReferenceWrapper referenceWrapper;
    private ArrayList<String> right_wrong_list;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_screen);
        imageView = (ImageView) findViewById(R.id.IV_backpress);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.finishActivityWithAnimation(SubmitScreen.this);
            }
        });
        init();
    }

    @Override
    public void onBackPressed() {
        for (int i = 0; i < 30; i++) {
            ConstantValues.AnsString[i] = "";
            ConstantValues.correctString[i] = "";
            referenceWrapper.arr_userans[i] = "";
        }
        super.onBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
        // EasyTracker.getInstance(this).activityStart(this);  //Add this method for google analytics.
    }

    @Override
    public void onStop() {
        super.onStop();
        //  EasyTracker.getInstance(this).activityStop(this);  // Add this method for google analytics.
    }

    private void init() {
        right_wrong_list = new ArrayList<String>();

        databasehandler = new DatabaseHandler();

        referenceWrapper = ReferenceWrapper.getReferenceWrapper(this);
        String counter_ref = referenceWrapper.getRecordStore(this, "counter");
        int counter1 = Integer.parseInt(counter_ref);
        String tablename = referenceWrapper.getRecordStore(this, "tablename");
        String mocktest_name = referenceWrapper.getRecordStore(this, "mocktest_name");
        Log.e("...init(submit screen)", "counter1= " + counter1 + " tablename= " + tablename + " mocktest_name= " + mocktest_name);

        for (int i = 1; i < 31; i++) {

            String question_answer = databasehandler.retrieve_messages(this, i, tablename);
            String[] arr_question_answer = question_answer.split(",");
            String answer1 = arr_question_answer[1];
            ConstantValues.correctString[i - 1] = answer1;
            //			referenceWrapper.arr_userans[j-1]=answer1;
            Log.e("....", "i= " + i + " answer= " + referenceWrapper.getArr_ans()[i - 1]);
        }

        header = (TextView) findViewById(R.id.header);

        header.setText(mocktest_name + " Result");

        quest = (TextView) findViewById(R.id.quest);
        user = (TextView) findViewById(R.id.user);
        correct = (TextView) findViewById(R.id.correct);

        Typeface face = Typeface.createFromAsset(getAssets(), "adobearabic_regular.otf");
        header.setTypeface(face);
        quest.setTypeface(face);
        user.setTypeface(face);
        correct.setTypeface(face);

        List = (ListView) findViewById(R.id.list);
        setDataOnList();
        List.setAdapter(new CustomAdapter(details, this));

    }


    private void setDataOnList() {
        details = new ArrayList<MessageDetails>();
        MessageDetails Detail;
        for (int i = 0; i < 30; i++) {
            Detail = new MessageDetails();
            Detail.setCorrectAnswer(ConstantValues.correctString[i]);
            Detail.setUserAnswer(referenceWrapper.getArr_ans()[i]);
            //			Detail.setUserAnswer(ConstantValues.AnsString[i]);
            if (ConstantValues.correctString[i].equals(referenceWrapper.getArr_ans()[i])) {
                right_wrong_list.add("right");
            } else {
                right_wrong_list.add("wrong");
            }
            Detail.setQuestionNo(i + 1);
            details.add(Detail);
        }
    }

    public class MessageDetails {
        String UserAns;
        String CorrectAns;
        int No;

        public String getCorrectAnswer() {
            return CorrectAns;
        }

        public void setCorrectAnswer(String correctans) {
            this.CorrectAns = correctans;
        }

        public String getUserAnswer() {
            return UserAns;
        }

        public void setUserAnswer(String userans) {
            this.UserAns = userans;
        }

        public int getQuestionNo() {
            return No;
        }

        public void setQuestionNo(int i) {
            this.No = i;
        }

    }

    public class CustomAdapter extends BaseAdapter {

        private ArrayList<MessageDetails> _data;
        Context _c;
        private MessageDetails msg;

        CustomAdapter(ArrayList<MessageDetails> data, Context c) {
            _data = data;
            _c = c;
        }

        public int getCount() {
            return _data.size();
        }

        public Object getItem(int position) {
            return _data.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.custom_mock_result, null);
            }

            final TextView question_no = (TextView) v.findViewById(R.id.textView_questionno);
            final TextView correct_ans = (TextView) v.findViewById(R.id.textView_correctans);
            final TextView user_ans = (TextView) v.findViewById(R.id.textView_useranswer);
            final ImageView img = (ImageView) v.findViewById(R.id.right_wrong);

            Typeface face = Typeface.createFromAsset(getAssets(), "adobearabic_regular.otf");
            question_no.setTypeface(face);
            correct_ans.setTypeface(face);
            user_ans.setTypeface(face);

            msg = _data.get(position);

            question_no.setText("" + msg.No);
            correct_ans.setText(msg.CorrectAns);
            user_ans.setText(msg.UserAns);
            if (right_wrong_list.get(position).toString().equals("right")) {
                img.setBackgroundResource(R.drawable.right1);
            } else {
                img.setBackgroundResource(R.drawable.wrong1);
            }

            return v;
        }
    }


}
