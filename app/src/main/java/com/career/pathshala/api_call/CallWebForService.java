package com.career.pathshala.api_call;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public final class CallWebForService {

    final MyServiceListener myListener;
    private Context mycontext;
    private String murl;
    private Map<String, String> map_vlaue;
    private ProgressDialog progressbar;
    private static RequestQueue requestQueue;
    private static StringRequest stringRequest;
    private static JSONObject jsonBody;

    /* private static RequestQueue requestQueue;
     private static StringRequest stringRequest;
 */
    public CallWebForService(String Progress, Context context, String url, final Map<String, String> map, MyServiceListener Listener) {

        this.mycontext = context;
        this.murl = url;
        this.myListener = Listener;
        this.map_vlaue = map;
        Log.d("----murl--", url);
        Log.d("-----context-", context.toString());
        Log.d("----map--", map.toString());

        // newwebservices();
        if (!Progress.equals("")) {
            progressbar = new ProgressDialog(mycontext);
            progressbar.setMessage("Loading...");
            progressbar.setCancelable(false);
            progressbar.show();
            progresswebservices();
        } else {
            newwebservices();
        }
    }


    private void webservices() {
        try {
            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(mycontext);
            }
            stringRequest = new StringRequest(Request.Method.POST, murl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            myListener.onSuccess(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            myListener.onFailed();
                            Log.e("-----VolleyError--->", error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // map_vlaue = new HashMap<String,String>();
                    return map_vlaue;
                }
            };
            //stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        } catch (Exception ex) {
        }
    }


    private void newwebservices() {
        try {
            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(mycontext);
            }
            jsonBody = new JSONObject(map_vlaue);
            final String requestBody = jsonBody.toString();
             stringRequest = new StringRequest(Request.Method.POST, murl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    myListener.onSuccess(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    myListener.onFailed();
                    Log.e("-----VolleyError--->", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };

            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void progresswebservices() {
        try {
            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(mycontext);
            }
             jsonBody = new JSONObject(map_vlaue);
            final String requestBody = jsonBody.toString();
            stringRequest = new StringRequest(Request.Method.GET, murl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    myListener.onSuccess(response);
                    progressbar.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    myListener.onFailed();
                    progressbar.dismiss();
                    Log.e("-----VolleyError--->", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (Exception e) {
            e.printStackTrace();
            progressbar.dismiss();
        }
    }


    private void progresswebservicesget() {
        try {
            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(mycontext);
            }
            jsonBody = new JSONObject(map_vlaue);
            final String requestBody = jsonBody.toString();
            stringRequest = new StringRequest(Request.Method.GET, murl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    myListener.onSuccess(response);
                    progressbar.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    myListener.onFailed();
                    progressbar.dismiss();
                    Log.e("-----VolleyError--->", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (Exception e) {
            e.printStackTrace();
            progressbar.dismiss();
        }
    }
}
