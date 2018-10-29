package com.career.pathshala.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.career.pathshala.R;


public class Fragment_contactus extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextView  indiano, serviceno, email;
    private Intent intent;
    private String tollfree, CallPhone;
    TextView UsWebsiteUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_contactus, container, false);
        initilize();
        return rootView;
    }

    private void initilize() {
        indiano = (TextView) rootView.findViewById(R.id.indiano);
        serviceno = (TextView) rootView.findViewById(R.id.serviceno);
        email = (TextView) rootView.findViewById(R.id.email);
        UsWebsiteUrl = (TextView) rootView.findViewById(R.id.UsWebsite_Url);


        indiano.setOnClickListener(this);
        serviceno.setOnClickListener(this);
        email.setOnClickListener(this);
        UsWebsiteUrl.setOnClickListener(this);


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

            case R.id.serviceno:
                Intent i1 = new Intent(Intent.ACTION_SEND);
                i1.setType("message/rfc822");
                i1.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@osoftec.com"});
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
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@osoftec.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                i.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(i, " "));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                }

                break;
            case R.id.UsWebsite_Url:
                Uri uri = Uri.parse("http://adectec.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

        }
    }

}


