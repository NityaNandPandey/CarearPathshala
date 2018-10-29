package com.career.pathshala.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.career.pathshala.R;

public class Fragment_aboutus extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextView  indiano, serviceemail, email;
    private Intent intent;
    private String tollfree, CallPhone;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_aboutus, container, false);
        initilize();
        return rootView;
    }

    private void initilize() {
        indiano = (TextView) rootView.findViewById(R.id.indiano);
        serviceemail = (TextView) rootView.findViewById(R.id.serviceemail);
        email = (TextView) rootView.findViewById(R.id.email);

        indiano.setOnClickListener(this);
        serviceemail.setOnClickListener(this);
        email.setOnClickListener(this);

        String text;
        text = "<html><body  style=\"text-align:justify; font-size:10px \">";
        text += "Career Pathshala app is in Hindi and English which helps students who are preparing for SSC, BANK, IBPS, UPSC, MBA, CAT, MAT, GMAT, GRE, Civil service and other government (sarkari) competition exams. Students can take FREE TEST SERIES and get current All India Rank.</br>" + "</br>" +
                " More Features includes-\n★ Daily Current GK\n★ 50,000+ Hindi Dictionary\n★ Daily Quiz\n★ Latest News Articles\n★ Discussion Forum\n★ Test Series and Mock Test Papers\n★ Get Current Rank of every test among all users.</br>";
        text += "</body></html>";
        ((WebView) rootView.findViewById(R.id.WebView)).loadData(text, "text/html; charset=UTF-8", "utf-8");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.indiano:
                intent = new Intent(Intent.ACTION_DIAL);
                tollfree = indiano.getText().toString().trim();
                //CallPhone = tollfree.replaceAll("\\D+", "");
                intent.setData(Uri.parse("tel:" + tollfree));
                try {
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                }
                break;

            case R.id.serviceemail:
                Intent i1 = new Intent(Intent.ACTION_SEND);
                i1.setType("message/rfc822");
                i1.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@careerpathshala.com"});
                i1.putExtra(Intent.EXTRA_SUBJECT, "");
                i1.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(i1, " "));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                }
                break;

            case R.id.email:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@careerpathshala.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                i.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(i, " "));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                }
                break;
        }
    }
}

