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


/**
 * Created by rupesh.m on 12/17/2016.
 */

public class Fragment_aboutus extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextView landlineno,indiano, serviceemail, email;
    private Intent intent;
    private String tollfree, CallPhone;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_aboutus, container, false);
        initilize();
        return rootView;
    }

    private void initilize() {
       landlineno = (TextView) rootView.findViewById(R.id.landlineno);
        indiano = (TextView) rootView.findViewById(R.id.indiano);
        serviceemail = (TextView) rootView.findViewById(R.id.serviceemail);
        email = (TextView) rootView.findViewById(R.id.email);

        landlineno.setOnClickListener(this);
        indiano.setOnClickListener(this);
        serviceemail.setOnClickListener(this);
        email.setOnClickListener(this);

        String text;
        text = "<html><body  style=\"text-align:justify; font-size:10px \">";
        text += "Optimization Software Technologies (Osoftec) provides process improvement-focused business intelligence analytics and enterprise-wide operations planning solutions. Our customized software products and business-consulting services improve customer profitability. Our software solutions utilize mathematical optimization techniques and allow users to optimize and re-optimize their plans to achieve improvements continuously.</br>" +"</br>"+
                "Our solutions are designed from a user’s perspective to increase ease-of-use. We are well known for our customer support and technology transfer. Our solutions are compatible with most existing technologies. We eliminate the “black-box”. Our solutions are affordable and our pricing is flexible to accommodate the customer’s situation. Our in-house research and development team make us flexible and responsive to changing customer needs.</br>" +"</br>"+
                "Our technology solves real business problems for major industries. Keeping revenue up and costs down- we understand that that is a big promise. It is backed by real customer successes.";
        text += "</body></html>";

        ((WebView) rootView.findViewById(R.id.WebView)).loadData(text, "text/html; charset=UTF-8", "utf-8");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.landlineno:
                intent = new Intent(Intent.ACTION_DIAL);
                tollfree = landlineno.getText().toString().trim();
                //CallPhone = tollfree.replaceAll("\\D+", "");
                intent.setData(Uri.parse("tel:" + tollfree));
                try {
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                }
                break;
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
        }
    }
}

