package com.career.pathshala.api_call;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.career.pathshala.setting.ConnectionDetector;

import org.json.JSONObject;

import java.util.Map;


/**
 * Created by Krishna Computers on 29-06-2016.
 */
public class CallWebService {

    final MyServiceListener myListener;
    private Context mycontext;
    private String murl;
    private Map<String, String> map_vlaue;
    private ProgressDialog progressbar;
    private ConnectionDetector cdr;
    private int prgressbar = 0;


    public CallWebService(Context context, String url, final Map<String, String> map, MyServiceListener Listener) {
        super();
        this.mycontext = context;
        this.murl = url;
        this.map_vlaue = map;
        this.myListener = Listener;
        prgressbar = 1;

        if (netconnection()) {
            progressbar = new ProgressDialog(mycontext);
            progressbar.setMessage("Loading ...");
            progressbar.setCancelable(false);
            progressbar.show();
            webservices();
        }
    }


    public CallWebService(String NoProgress, Context context, String url, final Map<String, String> map, MyServiceListener Listener) {
        super();
        this.mycontext = context;
        this.murl = url;
        this.map_vlaue = map;
        this.myListener = Listener;
        prgressbar = 0;

        if (NoProgress.equals("service")) {
            webservices();
        } else {
            if (netconnection()) {
                webservices();
            }
        }
    }

    public CallWebService(Context context, String upload, String url, Map<String, String> map, MyServiceListener Listener) {
        super();
        Log.e("Api getData......" + map, "");
        this.mycontext = context;
        this.murl = url;
        this.map_vlaue = map;
        this.myListener = Listener;
        if (netconnection()) {
            uploadwebservices();
        }
    }

  /*  private boolean netconnection() {
        cdr = new ConnectionDetector(mycontext);
        boolean checkcdr = cdr.isConnectingToInternet();
        // Log.d("------->Call webservice connection--", String.valueOf(checkcdr));
        if (!checkcdr) {
            Toast.makeText(mycontext, "Please connect to internet", Toast.LENGTH_SHORT).show();
            myListener.onFailed();
            return false;
        } else {
            return true;
        }
    }*/

    private boolean netconnection() {
        ConnectivityManager cm = (ConnectivityManager) mycontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            return true;
        } else {
            Toast.makeText(mycontext, "Please connect to internet", Toast.LENGTH_SHORT).show();
            myListener.onFailed();
            return false;
        }
    }

    private void webservices() {
        try {
            final RequestQueue requestQueue = Volley.newRequestQueue(mycontext);
            try {
                JSONObject obj = new JSONObject(map_vlaue);
                Log.d("-------URL---->" + murl, " ----INPUT PARAMETER---->" + obj.toString());
            } catch (Exception ex) {
            }

            final StringRequest stringRequest = new StringRequest(Request.Method.POST, murl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            myListener.onSuccess(response);
                            if (prgressbar != 0)
                                progressbar.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("-->Response", "---->Response On failed--" + error);
                            myListener.onFailed();
                            if (prgressbar != 0)
                                progressbar.dismiss();
                            //  Toast.makeText(mycontext, "Response On failed", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // map_vlaue = new HashMap<String,String>();
                    return map_vlaue;
                }
            };


            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (Exception ex) {
            if (prgressbar != 0)
                progressbar.dismiss();
        }

    }

    private void uploadwebservices() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, murl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Api response" + response, "");
                        myListener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Api response" + error, "");
                        Toast.makeText(mycontext, "Response On failed", Toast.LENGTH_LONG).show();
                        myListener.onFailed();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // map_vlaue = new HashMap<String,String>();
                return map_vlaue;
            }
        };
        final RequestQueue requestQueue = Volley.newRequestQueue(mycontext);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
