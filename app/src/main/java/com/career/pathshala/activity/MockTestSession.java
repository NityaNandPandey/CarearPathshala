package com.career.pathshala.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.career.pathshala.R;
import com.career.pathshala.api_call.ConstantValues;
import com.career.pathshala.api_call.ReferenceWrapper;
import com.career.pathshala.database.DatabaseHandler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class MockTestSession extends Activity implements OnClickListener {

    private int counter = 1;
    private DatabaseHandler databasehandler;
    private Button previous;
    private Button next;
    private Button questionList;
    private TextView header;
    private TextView question;
    private RadioGroup question_to_attempt;
    private String question_answer;
    private String[] arr_question_answer;
    private String question1;
    private String answer1;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private Button submit;
    private Dialog dialog_question_list;
    private TextView heading;
    private String str = "";
    private int a;
    private int b;
    private int c;
    private int d;
    private String userAnswer = "";
    private int i;
    public Vector<String> vector_user_answers;
    private Dialog dialog_confirmation;
    private Button yes;
    private Button no;
    private Dialog dialog_submi_confirmation;
    private TextView txt_time;
    private boolean isPopupActive;
    TextView timeTextView;
    Timer timer;
    int timervalue = 1800;
    private ReferenceWrapper referenceWrapper;
    private int counter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_test_session);
        init();
    }

    @SuppressLint("LongLogTag")
    private void init() {
        referenceWrapper = ReferenceWrapper.getReferenceWrapper(this);

        findIds();
        onClickListeners();

        databasehandler = new DatabaseHandler();
        arr_question_answer = new String[6];

        for (int i = 0; i < 30; i++) {
            ConstantValues.AnsString[i] = "";
            ConstantValues.correctString[i] = "";
        }


        String counter_ref = referenceWrapper.getRecordStore(this, "counter");
        counter1 = Integer.parseInt(counter_ref);
        String tablename = referenceWrapper.getRecordStore(this, "tablename");
        String mocktest_name = referenceWrapper.getRecordStore(this, "mocktest_name");
        Log.e("...init(mocktestsession screen)", "counter1= " + counter1 + " tablename= " + tablename + " mocktest_name= " + mocktest_name);

        question_answer = databasehandler.retrieve_messages(this, counter1, tablename);
        split();
        fetch_user_answers();
        quiz_timer();


    }


    @Override
    public void onStop() {
        super.onStop();
        // EasyTracker.getInstance(this).activityStop(this);  // Add this method for google analytics.
    }


    private void quiz_timer() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {

                        timervalue--;
                        getCurrentTimeString(timervalue);
                        if (timervalue == 0) {
                            Toast.makeText(getApplicationContext(), "Time Up", Toast.LENGTH_LONG).show();
                            timer.cancel();
                            timer.purge();
                            timer = null;
                            save_answer();

                            databasehandler.delete_quiz_data(MockTestSession.this, "user_answers");
                            Intent intent = new Intent(MockTestSession.this, SubmitScreen.class);
                            startActivity(intent);
                            MockTestSession.this.finish();
                        }


                    }
                });

            }
        }, 0, 1000);
    }

    protected void getCurrentTimeString(int timervalue) {
        String minstring = "";
        String secstring = "";
        int mint = timervalue / 60;
        if (mint < 10) {
            minstring = "0" + mint;
        } else {
            minstring = "" + mint;
        }

        int sec = timervalue % 60;

        if (sec < 10) {
            secstring = "0" + sec;
        } else {
            secstring = "" + sec;
        }

        txt_time.setText(minstring + ":" + secstring);
    }


    public class TimerBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_USER_PRESENT)) {
                if (timer == null && !isPopupActive) {
                    quiz_timer();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        referenceWrapper.addRecordStore(this, "mocktest_name", ConstantValues.MOCKTEST_NAME);
        referenceWrapper.addRecordStore(this, "counter", "" + counter);
        referenceWrapper.addRecordStore(MockTestSession.this, "tablename", "" + ConstantValues.TABLENAME);

        String counter_ref = referenceWrapper.getRecordStore(this, "counter");
        counter1 = Integer.parseInt(counter_ref);
        String tablename = referenceWrapper.getRecordStore(this, "tablename");
        String mocktest_name = referenceWrapper.getRecordStore(this, "mocktest_name");

        Log.e("...on pause", "counter1= " + counter1 + " tablename= " + tablename + " mocktest_name= " + mocktest_name);


        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }


        unregisterReceiver(timerBroadcast);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String counter_ref = referenceWrapper.getRecordStore(this, "counter");
        counter1 = Integer.parseInt(counter_ref);
        String tablename = referenceWrapper.getRecordStore(this, "tablename");
        String mocktest_name = referenceWrapper.getRecordStore(this, "mocktest_name");

        Log.e("...on resume", "counter1= " + counter1 + " tablename= " + tablename + " mocktest_name= " + mocktest_name);


        if (timer == null) {
            quiz_timer();
        }


        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        boolean locked = km.inKeyguardRestrictedInputMode();
        if (!locked && !isPopupActive) {

            if (timer == null) {
                quiz_timer();
            }

        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(timerBroadcast, filter);

    }


    TimerBroadcastReceiver timerBroadcast;
    IntentFilter filter;
    private TextView timer1;

    @Override
    protected void onStart() {
        super.onStart();
        timerBroadcast = new TimerBroadcastReceiver();
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(timerBroadcast, filter);

        // EasyTracker.getInstance(this).activityStart(this);  //Add this method for google analytics.

    }

    @Override
    public void onBackPressed() {
        confirmation_popup();
    }


    private void fetch_user_answers() {
        Log.e("....", "fetch_user_answers");
        //		referenceWrapper.addRecordStore(this, "counter",""+counter);

        String counter_ref = referenceWrapper.getRecordStore(this, "counter");
        counter1 = Integer.parseInt(counter_ref);

        String userAnswer1 = databasehandler.retreive_userAnswer(MockTestSession.this, counter1, "user_answers");
        if (userAnswer1.equals("A")) {
            ((RadioButton) findViewById(R.id.radio1)).setButtonDrawable(R.drawable.radio_tick);
        } else if (userAnswer1.equals("B")) {
            ((RadioButton) findViewById(R.id.radio2)).setButtonDrawable(R.drawable.radio_tick);
        } else if (userAnswer1.equals("C")) {
            ((RadioButton) findViewById(R.id.radio3)).setButtonDrawable(R.drawable.radio_tick);
        } else if (userAnswer1.equals("D")) {
            ((RadioButton) findViewById(R.id.radio4)).setButtonDrawable(R.drawable.radio_tick);
        }

    }

    private void split() {

        arr_question_answer = question_answer.split(",");
        question1 = arr_question_answer[0];
        answer1 = arr_question_answer[1];
        option1 = arr_question_answer[2];
        option2 = arr_question_answer[3];
        option3 = arr_question_answer[4];
        option4 = arr_question_answer[5];

        String counter_ref = referenceWrapper.getRecordStore(this, "counter");
        counter1 = Integer.parseInt(counter_ref);
        //		ConstantValues.correctString[counter1-1]=answer1;
        //		int j=counter1-1;
        //		referenceWrapper.arr_userans[j]=answer1;
        //		Log.e("....split","j= "+j+" answer= "+referenceWrapper.getArr_ans()[j]);

        question.setText(counter1 + ". " + question1);
        ((RadioButton) findViewById(R.id.radio1)).setText(option1);
        ((RadioButton) findViewById(R.id.radio2)).setText(option2);
        ((RadioButton) findViewById(R.id.radio3)).setText(option3);
        ((RadioButton) findViewById(R.id.radio4)).setText(option4);


        ((RadioButton) findViewById(R.id.radio1)).setButtonDrawable(R.drawable.radio);
        ((RadioButton) findViewById(R.id.radio2)).setButtonDrawable(R.drawable.radio);
        ((RadioButton) findViewById(R.id.radio3)).setButtonDrawable(R.drawable.radio);
        ((RadioButton) findViewById(R.id.radio4)).setButtonDrawable(R.drawable.radio);
    }

    private void findIds() {
        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        questionList = (Button) findViewById(R.id.question_list);
        submit = (Button) findViewById(R.id.submit);
        header = (TextView) findViewById(R.id.header);


        String mocktest_name = referenceWrapper.getRecordStore(this, "mocktest_name");
        header.setText(mocktest_name);

        question = (TextView) findViewById(R.id.question);
        timer1 = (TextView) findViewById(R.id.timer);
        txt_time = (TextView) findViewById(R.id.time);

        question_to_attempt = (RadioGroup) findViewById(R.id.radiogroup);

        Typeface face = Typeface.createFromAsset(getAssets(), "adobearabic_regular.otf");
        ((RadioButton) findViewById(R.id.radio1)).setTypeface(face);
        ((RadioButton) findViewById(R.id.radio2)).setTypeface(face);
        ((RadioButton) findViewById(R.id.radio3)).setTypeface(face);
        ((RadioButton) findViewById(R.id.radio4)).setTypeface(face);
        header.setTypeface(face);
        submit.setTypeface(face);
        question.setTypeface(face);
        timer1.setTypeface(face);
        txt_time.setTypeface(face);
        previous.setTypeface(face);
        next.setTypeface(face);
        questionList.setTypeface(face);
    }

    private void onClickListeners() {
        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        questionList.setOnClickListener(this);
        question_to_attempt.setOnClickListener(this);
        submit.setOnClickListener(this);
    }


    public void onRadioButtonClicked(View v) {
        ((RadioButton) findViewById(R.id.radio1)).setButtonDrawable(R.drawable.radio);
        ((RadioButton) findViewById(R.id.radio2)).setButtonDrawable(R.drawable.radio);
        ((RadioButton) findViewById(R.id.radio3)).setButtonDrawable(R.drawable.radio);
        ((RadioButton) findViewById(R.id.radio4)).setButtonDrawable(R.drawable.radio);
        if (v.getId() == R.id.radio1) {
            i = 1;
            ((RadioButton) findViewById(R.id.radio1)).setButtonDrawable(R.drawable.radio_tick);
            ((RadioButton) findViewById(R.id.radio2)).setButtonDrawable(R.drawable.radio);
            ((RadioButton) findViewById(R.id.radio3)).setButtonDrawable(R.drawable.radio);
            ((RadioButton) findViewById(R.id.radio4)).setButtonDrawable(R.drawable.radio);
        } else if (v.getId() == R.id.radio2) {
            i = 2;
            ((RadioButton) findViewById(R.id.radio1)).setButtonDrawable(R.drawable.radio);
            ((RadioButton) findViewById(R.id.radio2)).setButtonDrawable(R.drawable.radio_tick);
            ((RadioButton) findViewById(R.id.radio3)).setButtonDrawable(R.drawable.radio);
            ((RadioButton) findViewById(R.id.radio4)).setButtonDrawable(R.drawable.radio);
        } else if (v.getId() == R.id.radio3) {
            i = 3;
            ((RadioButton) findViewById(R.id.radio1)).setButtonDrawable(R.drawable.radio);
            ((RadioButton) findViewById(R.id.radio2)).setButtonDrawable(R.drawable.radio);
            ((RadioButton) findViewById(R.id.radio3)).setButtonDrawable(R.drawable.radio_tick);
            ((RadioButton) findViewById(R.id.radio4)).setButtonDrawable(R.drawable.radio);
        } else if (v.getId() == R.id.radio4) {
            i = 4;
            ((RadioButton) findViewById(R.id.radio1)).setButtonDrawable(R.drawable.radio);
            ((RadioButton) findViewById(R.id.radio2)).setButtonDrawable(R.drawable.radio);
            ((RadioButton) findViewById(R.id.radio3)).setButtonDrawable(R.drawable.radio);
            ((RadioButton) findViewById(R.id.radio4)).setButtonDrawable(R.drawable.radio_tick);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.previous) {
            check_userAnswer();
            next.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
            if (counter > 1) {
                set_radio();
                counter = counter - 1;
                referenceWrapper.addRecordStore(this, "counter", "" + counter);
                String counter_ref = referenceWrapper.getRecordStore(this, "counter");
                counter1 = Integer.parseInt(counter_ref);
                String tablename = referenceWrapper.getRecordStore(this, "tablename");
                question_answer = databasehandler.retrieve_messages(this, counter1, tablename);
                split();
                fetch_user_answers();
            }
        } else if (v.getId() == R.id.next) {
            check_userAnswer();
            if (counter == 29) {
                submit.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
            }
            if (counter < 30) {
                if (counter != 29) {
                    next.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);
                }
                set_radio();
                counter = counter + 1;
                referenceWrapper.addRecordStore(this, "counter", "" + counter);
                String tablename = referenceWrapper.getRecordStore(this, "tablename");
                String counter_ref = referenceWrapper.getRecordStore(this, "counter");
                counter1 = Integer.parseInt(counter_ref);
                question_answer = databasehandler.retrieve_messages(this, counter1, tablename);
                split();
                fetch_user_answers();

            }
        } else if (v.getId() == R.id.question_list) {
            show_question_list();
        } else if (v.getId() == R.id.submit) {
            save_answer();
            submit_confirmation_popup();
        }
    }


    private void check_userAnswer() {
        Log.e("check_userAnswer", "counter= " + counter);
        String counter_ref = referenceWrapper.getRecordStore(this, "counter");
        counter1 = Integer.parseInt(counter_ref);
        String userAnswer2 = databasehandler.retreive_userAnswer(MockTestSession.this, counter1, "user_answers");
        if (userAnswer2.equals("A") || userAnswer2.equals("B") || userAnswer2.equals("C") || userAnswer2.equals("D")) {
        } else {
            save_answer();
        }
    }

    private void save_answer() {
        Log.e("save answer", "(counter1-1)= " + (counter1 - 1));
        if (i == 1) {
            a = 1;
            referenceWrapper.arr_userans[counter1 - 1] = "A";
        } else if (i == 2) {
            b = 1;
            referenceWrapper.arr_userans[counter1 - 1] = "B";
        } else if (i == 3) {
            c = 1;
            referenceWrapper.arr_userans[counter1 - 1] = "C";
        } else if (i == 4) {
            d = 1;
            referenceWrapper.arr_userans[counter1 - 1] = "D";
        }

        String counter_ref = referenceWrapper.getRecordStore(this, "counter");
        counter1 = Integer.parseInt(counter_ref);
        databasehandler.update_answer(this, counter1, a, b, c, d, "user_answers");
        i = a = b = c = d = 0;

    }

    private void show_question_list() {
        if (i != 0) {
            save_answer();
        } else {
            String counter_ref = referenceWrapper.getRecordStore(this, "counter");
            counter1 = Integer.parseInt(counter_ref);
            String userAnswer2 = databasehandler.retreive_userAnswer(MockTestSession.this, counter1, "user_answers");
            if (userAnswer2.equals("A")) {
                i = 1;
            } else if (userAnswer2.equals("B")) {
                i = 2;
            } else if (userAnswer2.equals("C")) {
                i = 3;
            } else if (userAnswer2.equals("D")) {
                i = 4;
            }
        }
        dialog_question_list = new Dialog(this);
        dialog_question_list.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_question_list.setContentView(R.layout.custom_question_list);

        heading = (TextView) dialog_question_list.findViewById(R.id.heading);
        Typeface face = Typeface.createFromAsset(getAssets(), "adobearabic_regular.otf");
        heading.setTypeface(face);

        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio1)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio2)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio3)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio4)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio5)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio6)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio7)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio8)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio9)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio10)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio11)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio12)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio13)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio14)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio15)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio16)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio17)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio18)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio19)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio20)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio21)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio22)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio23)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio24)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio25)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio26)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio27)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio28)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio29)).setTypeface(face);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio30)).setTypeface(face);


        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio1)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio2)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio3)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio4)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio5)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio6)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio7)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio8)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio9)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio10)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio11)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio12)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio13)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio14)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio15)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio16)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio17)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio18)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio19)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio20)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio21)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio22)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio23)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio24)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio25)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio26)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio27)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio28)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio29)).setOnClickListener(onRadioButton_questions_Clicked);
        ((RadioButton) dialog_question_list.findViewById(R.id.question_radio30)).setOnClickListener(onRadioButton_questions_Clicked);


        for (int i = 1; i <= 30; i++) {
            userAnswer = "";
            userAnswer = databasehandler.retreive_userAnswer(MockTestSession.this, i, "user_answers");
            Log.e("...", "userAnswer= " + userAnswer);
            if (userAnswer.equals("A") || userAnswer.equals("B") || userAnswer.equals("C") || userAnswer.equals("D")) {
                if (i == 1)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio1)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 2)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio2)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 3)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio3)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 4)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio4)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 5)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio5)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 6)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio6)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 7)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio7)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 8)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio8)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 9)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio9)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 10)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio10)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 11)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio11)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 12)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio12)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 13)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio13)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 14)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio14)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 15)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio15)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 16)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio16)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 17)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio17)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 18)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio18)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 19)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio19)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 20)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio20)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 21)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio21)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 22)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio22)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 23)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio23)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 24)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio24)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 25)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio25)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 26)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio26)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 27)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio27)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 28)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio28)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 29)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio29)).setButtonDrawable(R.drawable.radio_tick);
                else if (i == 30)
                    ((RadioButton) dialog_question_list.findViewById(R.id.question_radio30)).setButtonDrawable(R.drawable.radio_tick);
            }
        }

        dialog_question_list.show();


    }

    OnClickListener onRadioButton_questions_Clicked = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Log.e("radio button", "clicked");
            str = "";
            RadioButton button = (RadioButton) v;
            button.setButtonDrawable(R.drawable.radio_tick);
            String radio_txt = button.getText().toString();
            for (int i = 9; i < radio_txt.length(); i++) {
                str = str + radio_txt.charAt(i);
            }
            Log.e("...", "str= " + str);

            counter = Integer.parseInt(str);
            referenceWrapper.addRecordStore(MockTestSession.this, "counter", "" + counter);
            if (counter >= 1 && counter <= 30) {
                if (counter == 30) {
                    submit.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                }
                String tablename = referenceWrapper.getRecordStore(MockTestSession.this, "tablename");
                String counter_ref = referenceWrapper.getRecordStore(MockTestSession.this, "counter");
                counter1 = Integer.parseInt(counter_ref);
                question_answer = databasehandler.retrieve_messages(MockTestSession.this, counter1, tablename);
                split();
                fetch_user_answers();
                dialog_question_list.dismiss();
            }


            Log.e("...", "counter= " + counter);
        }
    };
    private RelativeLayout layout_ads_submit;
    private RelativeLayout layout_ads_popup;
    //private AdView adView_popup;
//	private AdView adView_submit;

    private void set_radio() {
        ((RadioButton) findViewById(R.id.radio1)).setButtonDrawable(R.drawable.radio);
        ((RadioButton) findViewById(R.id.radio2)).setButtonDrawable(R.drawable.radio);
        ((RadioButton) findViewById(R.id.radio3)).setButtonDrawable(R.drawable.radio);
        ((RadioButton) findViewById(R.id.radio4)).setButtonDrawable(R.drawable.radio);
    }

    private void confirmation_popup() {
        dialog_confirmation = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        //		dialog_confirmation.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_confirmation.setContentView(R.layout.confirmation_popup);
        heading = (TextView) dialog_confirmation.findViewById(R.id.heading);
        yes = (Button) dialog_confirmation.findViewById(R.id.button_yes);
        no = (Button) dialog_confirmation.findViewById(R.id.button_no);
        // layout_ads_popup = (RelativeLayout) dialog_confirmation.findViewById(R.id.ads_popup);
        Typeface face = Typeface.createFromAsset(getAssets(), "adobearabic_regular.otf");
        yes.setTypeface(face);
        no.setTypeface(face);
        heading.setTypeface(face);

        //popup_adsLoad();

        yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                timer.cancel();
                timer.purge();
                timer = null;
                String tablename = referenceWrapper.getRecordStore(MockTestSession.this, "tablename");

                databasehandler.delete_quiz_data(MockTestSession.this, tablename);
                Intent intent = new Intent(MockTestSession.this, MockTest.class);
                startActivity(intent);
                MockTestSession.this.finish();
            }
        });

        no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                dialog_confirmation.dismiss();
            }
        });

        dialog_confirmation.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {

                dialog_confirmation.dismiss();

            }
        });


        dialog_confirmation.show();
    }

    private void submit_confirmation_popup() {
        dialog_submi_confirmation = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        //		dialog_submi_confirmation.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_submi_confirmation.setContentView(R.layout.confirmation_submit_popup);
        heading = (TextView) dialog_submi_confirmation.findViewById(R.id.heading);
        yes = (Button) dialog_submi_confirmation.findViewById(R.id.button_yes);
        no = (Button) dialog_submi_confirmation.findViewById(R.id.button_no);
        layout_ads_submit = (RelativeLayout) dialog_submi_confirmation.findViewById(R.id.ads_submit_popup);

        Typeface face = Typeface.createFromAsset(getAssets(), "adobearabic_regular.otf");
        yes.setTypeface(face);
        no.setTypeface(face);
        heading.setTypeface(face);

        //submit_popup_adsLoad();

        yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                timer.cancel();
                timer.purge();
                timer = null;
                databasehandler.delete_quiz_data(MockTestSession.this, "user_answers");
                Intent intent = new Intent(MockTestSession.this, SubmitScreen.class);
                startActivity(intent);
                MockTestSession.this.finish();

            }
        });

        no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog_submi_confirmation.dismiss();

            }
        });

        dialog_submi_confirmation.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {

                dialog_submi_confirmation.dismiss();

            }
        });

        dialog_submi_confirmation.show();
    }


}
